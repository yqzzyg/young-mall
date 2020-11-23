package com.young.mall.service;

import java.util.Map;

/**
 * @Description: 客户端首页数据
 * @Author: yqz
 * @CreateDate: 2020/11/23 14:36
 */
public interface WxHomeService {

    /**
     * 查询客户端主页数据
     *
     * @param userId 用户 id
     * @return
     */
    Map<String, Object> getIndexData(Integer userId);
}
