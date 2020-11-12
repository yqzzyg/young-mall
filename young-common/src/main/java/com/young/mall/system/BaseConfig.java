package com.young.mall.system;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 配置基类，该类实际持有所有的配置，子类只是提供代理访问方法
 * @Author: yqz
 * @CreateDate: 2020/11/11 16:30
 */
public abstract class BaseConfig {

    //所有的配置均保存在该 HashMap 中

    protected static Map<String, String> configs = new ConcurrentHashMap<>();

    /**
     * 添加配置到公共Map中
     * 项目启动，ConfigService已经把数据库配置读取到 configs 里面
     *
     * @param key
     * @param value
     */
    public static void addConfig(String key, String value) {
        configs.put(key, value);
    }

    /**
     * 重载配置,传入子类的prefix
     *
     * @param prefix
     */
    public static void reloadConfig(String prefix) {
        //先遍历删除该 prefix 所以配置
        for (Iterator<Map.Entry<String, String>> it = configs.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, String> item = it.next();
            if (item.getKey().startsWith(prefix)) {
                it.remove();
            }
        }
        ConfigDataService.getSystemConfigService().reloadConfig(prefix);
    }

    /**
     * 按 String 类型
     *
     * @param keyName
     * @return
     */
    protected static String getConfig(String keyName) {
        return configs.get(keyName);
    }

    /**
     * 根据 int 类型获取配置
     *
     * @return
     */
    protected static Integer getConfigInt(String keyName) {
        return Integer.valueOf(configs.get(keyName));
    }

    /**
     * 以BigDecimal类型获取配置值
     *
     * @param keyName
     * @return
     */
    protected static BigDecimal getConfigBigDec(String keyName) {
        return new BigDecimal(configs.get(keyName));
    }

    /**
     * 子类实现该方法，并告知父类配置前缀，
     * 该前缀用来索引配置组用于简化访问和按组重读配置
     *
     * @return
     */
    abstract String getPrefix();

}
