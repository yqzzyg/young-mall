package com.young.mall.config.express;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 读取快递物流配置
 * @Author: yqz
 * @CreateDate: 2020/12/21 15:31
 */
@Data
@ConfigurationProperties(prefix = "young.express")
public class ExpressProperties {
    private boolean enable;
    private String appId;
    private String appKey;
    private List<Map<String, String>> vendors = new ArrayList<>();
}
