package com.young.mall.service.impl;

import com.young.db.dao.YoungGrouponMapper;
import com.young.db.entity.YoungGroupon;
import com.young.db.entity.YoungGrouponExample;
import com.young.mall.service.ClientGrouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public List<YoungGroupon> queryMyGroupon(Integer userId) {
        YoungGrouponExample example = new YoungGrouponExample();
        example.or().andUserIdEqualTo(userId).andCreatorUserIdEqualTo(userId)
                .andGrouponIdEqualTo(0).andDeletedEqualTo(false)
                .andPayedEqualTo(true);
        example.orderBy("add_time desc");

        return youngGrouponMapper.selectByExample(example);
    }

    @Override
    public List<YoungGroupon> queryMyJoinGroupon(Integer userId) {
        YoungGrouponExample example = new YoungGrouponExample();
        example.or().andUserIdEqualTo(userId).andGrouponIdNotEqualTo(0)
                .andDeletedEqualTo(false).andPayedEqualTo(true);
        example.orderBy("add_time desc");

        return youngGrouponMapper.selectByExample(example);
    }

    @Override
    public Integer countGroupon(Integer grouponId) {
        YoungGrouponExample example = new YoungGrouponExample();
        example.or().andGrouponIdEqualTo(grouponId)
                .andDeletedEqualTo(false)
                .andPayedEqualTo(true);
        long count = youngGrouponMapper.countByExample(example);
        return (int) count;
    }

    @Override
    public YoungGroupon queryById(Integer id) {
        YoungGrouponExample example = new YoungGrouponExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false).andPayedEqualTo(true);
        return youngGrouponMapper.selectOneByExample(example);
    }

    @Override
    public Integer createGroupon(YoungGroupon groupon) {
        groupon.setAddTime(LocalDateTime.now());
        groupon.setUpdateTime(LocalDateTime.now());
        return youngGrouponMapper.insertSelective(groupon);
    }
}
