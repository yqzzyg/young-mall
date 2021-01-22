package com.young.mall.service.impl;

import com.young.db.dao.YoungUserLogMapper;
import com.young.db.entity.YoungUserLog;
import com.young.mall.domain.MallLog;
import com.young.mall.service.MallUserLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Description: 用户日志数据库记录
 * @Author: yqz
 * @CreateDate: 2021/1/22 14:45
 */
@Service
public class MallUserLogServiceImpl implements MallUserLogService {

    @Resource
    private YoungUserLogMapper userLogMapper;

    @Override
    public Integer insertLog(YoungUserLog userLog) {

        userLog.setAddTime(LocalDateTime.now());
        userLog.setUpdateTime(LocalDateTime.now());
        userLog.setDeleted(false);
        return userLogMapper.insertSelective(userLog);
    }
}
