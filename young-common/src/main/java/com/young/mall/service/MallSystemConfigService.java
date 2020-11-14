package com.young.mall.service;

import com.young.db.entity.YoungSystem;

import java.util.List;
import java.util.Optional;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/11 16:44
 */
public interface MallSystemConfigService {

    /**
     * 查询所有系统配置
     * @return
     */
    Optional<List<YoungSystem>> queryAll();
}
