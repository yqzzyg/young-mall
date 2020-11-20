package com.young.mall.service;

import com.young.db.entity.YoungSystem;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description: 系统配置
 * @Author: yqz
 * @CreateDate: 2020/11/20 17:00
 */
public interface AdminSystemService {

    /**
     * 商场配置详情
     *
     * @return
     */
    Optional<Map<String, String>> listMall();

    /**
     * 商场配置详情更新
     *
     * @param map
     * @return
     */
    void updateConfig(Map<String, Object> map);

    /**
     * 运费配置详情
     *
     * @return
     */
    Optional<Map<String, String>> listExpress();


    /**
     * 订单配置详情
     *
     * @return
     */
    Optional<Map<String, String>> listOrder();

    /**
     * 小程序配置详情
     * @return
     */
    Optional<Map<String, String>> listWx();

}
