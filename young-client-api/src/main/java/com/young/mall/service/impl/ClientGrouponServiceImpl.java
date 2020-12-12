package com.young.mall.service.impl;

import com.young.db.dao.YoungGrouponMapper;
import com.young.db.entity.YoungGroupon;
import com.young.db.entity.YoungGrouponExample;
import com.young.mall.service.ClientGrouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/12/12 22:37
 */
@Service
public class ClientGrouponServiceImpl implements ClientGrouponService {

    @Autowired
    private YoungGrouponMapper youngGrouponMapper;

    @Override
    public YoungGroupon queryByOrderId(Integer orderId) {
        YoungGrouponExample example = new YoungGrouponExample();
        example.or().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
        return youngGrouponMapper.selectOneByExample(example);
    }
}
