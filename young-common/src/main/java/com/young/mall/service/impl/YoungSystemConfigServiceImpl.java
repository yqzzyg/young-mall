package com.young.mall.service.impl;

import com.young.db.dao.YoungSystemMapper;
import com.young.db.entity.YoungSystem;
import com.young.db.entity.YoungSystemExample;
import com.young.mall.service.YoungSystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 查询系统配置
 * @Author: yqz
 * @CreateDate: 2020/11/11 16:45
 */
@Service
public class YoungSystemConfigServiceImpl implements YoungSystemConfigService {

    @Autowired
    private YoungSystemMapper youngSystemMapper;

    @Override
    public Optional<List<YoungSystem>> queryAll() {

        YoungSystemExample example = new YoungSystemExample();
        example.createCriteria().andDeletedEqualTo(false);
        List<YoungSystem> systemList = youngSystemMapper.selectByExample(example);

        return Optional.ofNullable(systemList);
    }
}
