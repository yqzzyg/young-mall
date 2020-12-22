package com.young.mall.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.young.db.entity.YoungUser;
import com.young.mall.common.ResBean;
import com.young.mall.constant.CommonConstants;
import com.young.mall.domain.*;
import com.young.mall.domain.enums.WxResponseCode;
import com.young.mall.enums.UserTypeEnum;
import com.young.mall.exception.Asserts;
import com.young.mall.service.ClientAuthService;
import com.young.mall.service.ClientCouponAssignService;
import com.young.mall.service.ClientUserService;
import com.young.mall.service.RedisService;
import com.young.mall.utils.CaptchaCodeManager;
import com.young.mall.utils.IpUtil;
import com.young.mall.utils.JwtTokenUtil;
import com.young.mall.utils.RegexUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 登录注册相关业务
 * @Author: yqz
 * @CreateDate: 2020/11/24 10:23
 */
@Service
public class ClientAuthServiceImpl implements ClientAuthService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxMaService wxService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientCouponAssignService clientCouponAssignService;

    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisService redisService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Transactional
    @Override
    public ResBean<Map<String, Object>> register(ClientUserDto registerDto, HttpServletRequest request) {
        //校验参数
        String openId = checkParams(registerDto);

        String passwd = passwordEncoder.encode(registerDto.getPassword());
        YoungUser user = new YoungUser();

        BeanUtil.copyProperties(registerDto, user);
        user.setPassword(passwd);
        user.setWeixinOpenid(openId);
        user.setAvatar(CommonConstants.DEFAULT_AVATAR);
        user.setNickname(registerDto.getUsername());
        user.setGender((byte) 0);
        user.setUserLevel((byte) 0);
        user.setStatus((byte) 0);
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(IpUtil.client(request));
        //用户信息存入数据库
        Integer count = clientUserService.addUser(user);

        // 给新用户发送注册优惠券
        clientCouponAssignService.assignForRegister(user.getId());

        // userInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(registerDto.getUsername());
        userInfo.setAvatarUrl(user.getAvatar());
        // 4 生成自定义token
        ClientUserDetails userDetails = new ClientUserDetails(user);
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, Object> result = new HashMap<>(4);
        result.put("token", token);
        result.put("tokenHead", tokenHead);
        result.put("userInfo", userInfo);
        return ResBean.success(result);
    }

    @Override
    public ResBean<Map<String, Object>> login(ClientLoginDto clientLoginDto, HttpServletRequest request) {

        logger.debug("进入AuthServiceImpl login方法,入参：username：{}，password：{}", clientLoginDto.getUsername(), clientLoginDto.getPassword());
        // 1 创建UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(clientLoginDto.getUsername(), clientLoginDto.getPassword());
        // 2 认证。调用UserDetailsServiceImpl.loadUserByUsername
        Authentication authenticate = null;
        try {
            authenticate = this.authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                Asserts.fail("密码错误");
            } else {
                Asserts.fail("用户名错误");
            }
        }
        // 3 保存认证信息
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        // 4 生成自定义token
        ClientUserDetails userDetails = (ClientUserDetails) authenticate.getPrincipal();
        YoungUser youngUser = userDetails.getYoungUser();

        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, Object> result = new HashMap<>(4);

        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(youngUser.getUsername());
        userInfo.setAvatarUrl(youngUser.getAvatar());
        result.put("token", token);
        result.put("tokenHead", tokenHead);
        result.put("userInfo", userInfo);
        return ResBean.success(result);
    }

    @Override
    public ResBean<Map<String, Object>> loginByWeixin(WxLoginInfo wxLoginInfo, HttpServletRequest request) throws WxErrorException {

        String code = wxLoginInfo.getCode();
        UserInfo userInfo = wxLoginInfo.getUserInfo();
        Integer shareUserId = wxLoginInfo.getShareUserId();

        WxMaJscode2SessionResult result = wxService.getUserService().getSessionInfo(code);
        String sessionKey = result.getSessionKey();
        String openid = result.getOpenid();
        if (StrUtil.isEmpty(sessionKey) || StrUtil.isEmpty(openid)) {
            return ResBean.failed("调用微信失败");
        }
        YoungUser user = clientUserService.getOneUserByOpenId(openid);
        if (BeanUtil.isEmpty(user)) {
            user = new YoungUser();
            user.setUsername(openid);
            user.setPassword(openid);
            user.setWeixinOpenid(openid);
            user.setAvatar(userInfo.getAvatarUrl());
            user.setNickname(userInfo.getNickName());
            user.setGender(userInfo.getGender());
            user.setUserLevel((byte) 0);
            user.setStatus((byte) 0);
            user.setLastLoginTime(LocalDateTime.now());
            user.setLastLoginIp(IpUtil.client(request));
            user.setShareUserId(shareUserId);

            Integer count = clientUserService.addUser(user);
            // 给新用户发送注册优惠券
            clientCouponAssignService.assignForRegister(user.getId());

        } else {
            user.setLastLoginTime(LocalDateTime.now());
            user.setLastLoginIp(IpUtil.client(request));
            if (clientUserService.updateById(user) == 0) {
                return ResBean.failed("更新数据失败");
            }
        }

        userInfo.setUserId(user.getId());
        if (!StringUtils.isEmpty(user.getMobile())) {// 手机号存在则设置
            userInfo.setPhone(user.getMobile());
        }
        String registerDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                .format(user.getAddTime() != null ? user.getAddTime() : LocalDateTime.now());
        userInfo.setRegisterDate(registerDate);
        userInfo.setStatus(user.getStatus());
        // 用户层级
        userInfo.setUserLevel(user.getUserLevel());
        // 用户层级描述
        userInfo.setUserLevelDesc(UserTypeEnum.getInstance(user.getUserLevel()).getDesc());

        // 4 生成自定义token
        ClientUserDetails userDetails = new ClientUserDetails(user);
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, Object> responseData = new HashMap<>(4);
        responseData.put("token", token);
        responseData.put("tokenHead", tokenHead);
        responseData.put("userInfo", userInfo);
        return ResBean.success(responseData);
    }

    public String checkParams(ClientUserDto registerDto) {
        // 判断验证码是否正确

        Object cacheCode = redisService.get(registerDto.getMobile());
        if (BeanUtil.isEmpty(cacheCode) || !cacheCode.equals(registerDto.getCode())) {
            logger.error("请求账号注册出错:{}", WxResponseCode.AUTH_CAPTCHA_UNMATCH);
            Asserts.fail(WxResponseCode.AUTH_CAPTCHA_UNMATCH);
        }
        if (!RegexUtil.isMobileExact(registerDto.getMobile())) {
            logger.info("{}，注册失败：{}", registerDto, WxResponseCode.AUTH_INVALID_MOBILE);
            Asserts.fail(WxResponseCode.AUTH_INVALID_MOBILE);
        }
        List<YoungUser> userList = clientUserService.getUserByName(registerDto.getUsername());
        if (userList.size() > 0) {
            logger.info("{}，注册失败：{}", registerDto, WxResponseCode.AUTH_NAME_REGISTERED);
            Asserts.fail(WxResponseCode.AUTH_NAME_REGISTERED);
        }
        userList = clientUserService.getUserByMobile(registerDto.getMobile());
        if (userList.size() > 0) {
            logger.info("{}，注册失败：{}", registerDto, WxResponseCode.AUTH_MOBILE_REGISTERED);
            Asserts.fail(WxResponseCode.AUTH_MOBILE_REGISTERED);
        }
        String openId = new String();
        try {
            WxMaJscode2SessionResult wxUserInfo = wxService.getUserService().getSessionInfo(registerDto.getWxCode());
            openId = wxUserInfo.getOpenid();
        } catch (WxErrorException e) {
            logger.info("请求微信平台报错：{}", e.getError());
            e.printStackTrace();
            Asserts.fail(WxResponseCode.AUTH_OPENID_UNACCESS);
        }
        userList = clientUserService.getUserByOpenId(openId);
        if (userList.size() > 0) {
            logger.info("{}，注册失败：{}", registerDto, WxResponseCode.AUTH_OPENID_BINDED);
            Asserts.fail(WxResponseCode.AUTH_OPENID_BINDED);
        }
        return openId;
    }
}
