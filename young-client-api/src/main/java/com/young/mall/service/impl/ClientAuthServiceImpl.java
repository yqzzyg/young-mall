package com.young.mall.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.bean.BeanUtil;
import com.young.db.dao.YoungUserMapper;
import com.young.db.entity.YoungUser;
import com.young.mall.common.ResBean;
import com.young.mall.constant.CommonConstants;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.domain.ClientUserDto;
import com.young.mall.domain.UserInfo;
import com.young.mall.domain.enums.WxResponseCode;
import com.young.mall.exception.Asserts;
import com.young.mall.service.ClientAuthService;
import com.young.mall.service.ClientCouponAssignService;
import com.young.mall.service.ClientUserService;
import com.young.mall.utils.CaptchaCodeManager;
import com.young.mall.utils.IpUtil;
import com.young.mall.utils.JwtTokenUtil;
import com.young.mall.utils.RegexUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
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
    private YoungUserMapper youngUserMapper;
    @Autowired
    private WxMaService wxService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientCouponAssignService clientCouponAssignService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Transactional
    @Override
    public ResBean<Map<Object, Object>> register(ClientUserDto registerDto, HttpServletRequest request) {
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
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("tokenExpire", new Date());
        result.put("userInfo", userInfo);
        return ResBean.success(result);
    }

    public String checkParams(ClientUserDto registerDto) {
        // 判断验证码是否正确
        String cacheCode = CaptchaCodeManager.getCachedCaptcha(registerDto.getMobile());
        if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(registerDto.getCode())) {
            logger.error("请求账号注册出错:{}", WxResponseCode.AUTH_CAPTCHA_UNMATCH);
            Asserts.fail(WxResponseCode.AUTH_CAPTCHA_UNMATCH);
        }
        if (!RegexUtil.isMobileExact(registerDto.getMobile())) {
            logger.info("{}，注册失败：{}", registerDto, WxResponseCode.AUTH_INVALID_MOBILE);
            Asserts.fail(WxResponseCode.AUTH_INVALID_MOBILE);
        }
        List<YoungUser> userList =clientUserService.getUserByName(registerDto.getUsername());
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
