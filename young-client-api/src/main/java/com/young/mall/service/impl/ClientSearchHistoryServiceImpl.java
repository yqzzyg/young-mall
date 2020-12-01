package com.young.mall.service.impl;

import com.young.db.dao.YoungSearchHistoryMapper;
import com.young.db.entity.YoungSearchHistory;
import com.young.mall.service.ClientSearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Description: 搜索历史
 * @Author: yqz
 * @CreateDate: 2020/12/1 21:00
 */
@Service
public class ClientSearchHistoryServiceImpl implements ClientSearchHistoryService {

    @Autowired
    private YoungSearchHistoryMapper searchHistoryMapper;

    @Override
    public Integer save(YoungSearchHistory history) {

        history.setAddTime(LocalDateTime.now());
        history.setUpdateTime(LocalDateTime.now());
        int count = searchHistoryMapper.insertSelective(history);
        return count;
    }
}
