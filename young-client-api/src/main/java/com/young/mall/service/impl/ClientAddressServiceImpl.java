package com.young.mall.service.impl;

import com.young.db.dao.YoungAddressMapper;
import com.young.db.entity.YoungAddress;
import com.young.db.entity.YoungAddressExample;
import com.young.mall.service.ClientAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 收货地址service
 * @Author: yqz
 * @CreateDate: 2020/12/16 14:51
 */
@Service
public class ClientAddressServiceImpl implements ClientAddressService {


    @Autowired
    private YoungAddressMapper youngAddressMapper;

    @Override
    public YoungAddress findDefault(Integer userId) {
        YoungAddressExample example = new YoungAddressExample();
        example.or().andUserIdEqualTo(userId)
                .andIsDefaultEqualTo(true)
                .andDeletedEqualTo(false);
        return youngAddressMapper.selectOneByExample(example);
    }

    @Override
    public YoungAddress findById(Integer id) {
        return youngAddressMapper.selectByPrimaryKey(id);
    }
}
