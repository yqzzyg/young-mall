package com.young.mall.system;

import com.young.db.entity.YoungSystem;
import com.young.mall.exception.Asserts;
import com.young.mall.service.MallSystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 该类用于自动初始化数据库配置到BaseConfig中，以便BaseConfig的子类调用
 * @Author: yqz
 * @CreateDate: 2020/11/11 16:39
 */
@Service
public class ConfigDataService {
    private static ConfigDataService systemConfigService;

    @Autowired
    private MallSystemConfigService youngSystemConfigService;

    // 不允许实例化
    private ConfigDataService() {

    }

    public static ConfigDataService getSystemConfigService() {
        return systemConfigService;
    }

    @PostConstruct
    public void inist() {
        systemConfigService = this;
        systemConfigService.inistConfigs();
    }

    /**
     * 根据 prefix 重置 prefix 下所有的设置
     *
     * @param prefix
     */
    public void reloadConfig(String prefix) {
        Optional<List<YoungSystem>> optional = youngSystemConfigService.queryAll();
        if (!optional.isPresent()) {
            Asserts.fail("读取所有配置失败");
        }
        List<YoungSystem> systemList = optional.get();
        for (YoungSystem youngSystem : systemList) {
            //判断是否符合条件
            if (youngSystem.getKeyName().startsWith(prefix)) {
                BaseConfig.addConfig(youngSystem.getKeyName(), youngSystem.getKeyValue());
            }
        }
    }

    /**
     * 读取所有配置
     */
    private void inistConfigs() {
        Optional<List<YoungSystem>> optional = youngSystemConfigService.queryAll();
        if (!optional.isPresent()) {
            Asserts.fail("读取所有配置失败");
        }
        List<YoungSystem> systemList = optional.get();
        for (YoungSystem youngSystem : systemList) {
            BaseConfig.addConfig(youngSystem.getKeyName(), youngSystem.getKeyValue());
        }
    }
}
