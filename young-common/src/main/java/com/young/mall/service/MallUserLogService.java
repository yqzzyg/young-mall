package com.young.mall.service;

import com.young.db.entity.YoungUserLog;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2021/1/22 14:44
 */
public interface MallUserLogService {

    /**
     * 记录日志
     *
     * @param userLog
     * @return
     */
    Integer insertLog(YoungUserLog userLog);
}
