package com.young.mall.config.wx;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/3 16:25
 */
@Configuration
public class WxConfig {
    @Autowired
    private WxProperties properties;

    @Bean
    public WxMaConfig wxMaConfig() {
        WxMaInMemoryConfig config = new WxMaInMemoryConfig();
        config.setAppid(properties.getAppId());
        config.setSecret(properties.getAppSecret());
        return config;
    }

    @Bean
    public WxMaService wxMaService(WxMaConfig maConfig) {
        WxMaServiceImpl wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(maConfig);
        return wxMaService;
    }

    @Bean
    public WxPayConfig wxPayConfig() {
        WxPayConfig config = new WxPayConfig();
        config.setAppId(properties.getAppId());
        config.setMchId(properties.getMchId());
        config.setMchKey(properties.getMchKey());
        config.setNotifyUrl(properties.getNotifyUrl());
        config.setKeyPath(properties.getKeyPath());
        config.setTradeType("JSAPI");
        config.setSignType("MD5");

        return config;
    }

    @Bean
    public WxPayService wxPayService(WxPayConfig payConfig) {
        WxPayServiceImpl service = new WxPayServiceImpl();
        service.setConfig(payConfig);
        return service;
    }
}
