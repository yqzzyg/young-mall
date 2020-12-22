package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.young.db.entity.YoungUser;
import com.young.mall.common.ResBean;
import com.young.mall.domain.ClientLoginDto;
import com.young.mall.domain.ClientUserDto;
import com.young.mall.domain.WxLoginInfo;
import com.young.mall.domain.enums.WxResponseCode;
import com.young.mall.domain.vo.ResetVo;
import com.young.mall.notify.NotifyService;
import com.young.mall.notify.NotifyType;
import com.young.mall.service.ClientAuthService;
import com.young.mall.service.ClientUserService;
import com.young.mall.service.RedisService;
import com.young.mall.utils.CharUtil;
import com.young.mall.utils.RegexUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 用户端登录、注册相关
 * @Author: yqz
 * @CreateDate: 2020/11/23 15:40
 */
@Api(tags = "ClientAuthController")
@RestController
@RequestMapping("/client/auth")
public class ClientAuthController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ClientAuthService clientAuthService;

    @Resource
    private NotifyService notifyService;

    @Resource
    private RedisService redisService;

    @Resource
    private ClientUserService clientUserService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @ApiOperation("注册")
    @PostMapping("register")
    public ResBean<Map<String, Object>> register(@Valid @RequestBody ClientUserDto clientUserDto, HttpServletRequest request) {
        logger.info("客户端注册入参：{}", clientUserDto);

        return clientAuthService.register(clientUserDto, request);
    }


    @ApiOperation("请求验证码")
    @PostMapping("/regCaptcha")
    public ResBean registerCaptcha(@RequestBody Map<String, String> body) {

        String mobile = body.get("mobile");
        if (StrUtil.isBlank(mobile)) {
            return ResBean.validateFailed();
        }
        if (!RegexUtil.isMobileExact(mobile)) {
            return ResBean.validateFailed();
        }
        if (!notifyService.isSmsEnable()) {
            logger.info("请求验证码出错:{}", WxResponseCode.AUTH_CAPTCHA_UNSUPPORT.getMsg());
        }
        String code = CharUtil.getRandomNum(6);

        Map<String, Object> map = new HashMap<>(2);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        map.put("params", jsonObject);

        Object captcha = redisService.get(mobile);
        //如果缓存中没有验证码，则调用发送接口
        if (BeanUtil.isEmpty(captcha)) {
            notifyService.notifySmsTemplate(mobile, NotifyType.CAPTCHA, map);
            redisService.set(mobile, code, 60);
        } else {
            logger.info("请求验证码出错:{}", WxResponseCode.AUTH_CAPTCHA_FREQUENCY.getMsg());
            return ResBean.failed(WxResponseCode.AUTH_CAPTCHA_FREQUENCY.getMsg());
        }

        return ResBean.success("成功");
    }


    @ApiOperation("账户密码方式登录")
    @PostMapping("/login")
    public ResBean<Map<String, Object>> login(@Valid @RequestBody ClientLoginDto clientLoginDto, HttpServletRequest request) {
        logger.info("客户端登录入参：{}", clientLoginDto);

        return clientAuthService.login(clientLoginDto, request);
    }

    @ApiOperation("通过微信登录")
    @PostMapping("/loginByWeixin")
    public ResBean<Map<String, Object>> loginByWeixin(@Valid @RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        ResBean<Map<String, Object>> resBean = new ResBean<>();
        try {
            resBean = clientAuthService.loginByWeixin(wxLoginInfo, request);
        } catch (WxErrorException e) {
            logger.info("微信登录失败：{}", e.getError());
            e.printStackTrace();
        }
        return resBean;
    }


    @ApiOperation("账号密码重置")
    @PostMapping("/reset")
    public ResBean reset(@Valid @RequestBody ResetVo reset) {

        // 判断验证码是否正确
        Object cacheCode = redisService.get(reset.getMobile());

        if (BeanUtil.isEmpty(cacheCode) || !reset.getCode().equals(cacheCode)) {
            logger.info("账号密码重置出错:{}", WxResponseCode.AUTH_CAPTCHA_UNMATCH.getMsg());
            return ResBean.failed(WxResponseCode.AUTH_CAPTCHA_UNMATCH);
        }

        List<YoungUser> userList = clientUserService.getUserByMobile(reset.getMobile());

        if (userList.size() > 1) {
            logger.info("账号密码重置出错,账户不唯一,查询手机号:{}", reset.getMobile());
            return ResBean.failed("账号密码重置出错,账户不唯一");
        } else if (userList.size() == 0) {
            logger.info("账号密码重置出错,账户不存在,查询手机号:{},{}", reset.getMobile(), WxResponseCode.AUTH_MOBILE_UNREGISTERED.getMsg());
            return ResBean.failed(WxResponseCode.AUTH_MOBILE_UNREGISTERED);
        }

        YoungUser user = userList.get(0);
        String encode = passwordEncoder.encode(reset.getPassword());
        user.setPassword(encode);
        if (clientUserService.updateById(user) == 0) {
            logger.info("账号密码重置更新用户信息出错:{}", user);
            return ResBean.failed("更新数据失败");
        }
        return ResBean.success("更新成功");
    }

    @ApiOperation("绑定手机号码")
    @PostMapping("/bindPhone")
    public ResBean bindPhone(@RequestBody String body) {


        return null;

    }
}
