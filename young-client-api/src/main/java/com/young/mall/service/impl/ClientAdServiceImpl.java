package com.young.mall.service.impl;

import com.young.db.dao.YoungAdMapper;
import com.young.db.entity.YoungAd;
import com.young.db.entity.YoungAdExample;
import com.young.mall.service.ClientAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 广告相关业务
 * @Author: yqz
 * @CreateDate: 2020/11/21 20:35
 */
@Service
public class ClientAdServiceImpl implements ClientAdService {

    @Autowired
    private YoungAdMapper youngAdMapper;

    @Override
    public List<YoungAd> queryIndex() {

        YoungAdExample example = new YoungAdExample();
        example.createCriteria().andPositionEqualTo((byte) 1).andDeletedEqualTo(false);
        List<YoungAd> adList = youngAdMapper.selectByExample(example);
        return adList;
    }
}
