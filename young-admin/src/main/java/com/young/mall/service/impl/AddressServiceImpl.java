package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungAddressMapper;
import com.young.db.entity.YoungAddress;
import com.young.db.entity.YoungAddressExample;
import com.young.mall.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description: AddressService实现类
 * @Author: yqz
 * @CreateDate: 2020/10/30 21:17
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private YoungAddressMapper addressMapper;

    @Override
    public Optional<List<YoungAddress>> queryAddressList(Integer userId, String name,
                                                         Integer page, Integer size,
                                                         String sort, String order) {
        YoungAddressExample example = new YoungAddressExample();
        YoungAddressExample.Criteria criteria = example.createCriteria();

        if ( userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (StrUtil.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);
        if (StrUtil.isNotBlank(sort) && StrUtil.isNotBlank(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page,size);
        List<YoungAddress> addressList = addressMapper.selectByExample(example);

        return Optional.ofNullable(addressList);
    }
}
