package com.young.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungCollectMapper;
import com.young.db.entity.YoungCollect;
import com.young.db.entity.YoungCollectExample;
import com.young.mall.service.ClientCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description: 用户收藏
 * @Author: yqz
 * @CreateDate: 2020/12/3 17:41
 */
@Service
public class ClientCollectServiceImpl implements ClientCollectService {

    @Autowired
    private YoungCollectMapper youngCollectMapper;

    @Override
    public int count(Integer uid, Integer gid) {
        YoungCollectExample example = new YoungCollectExample();
        example.or().andUserIdEqualTo(uid).andValueIdEqualTo(gid).andDeletedEqualTo(false);
        return (int) youngCollectMapper.countByExample(example);
    }

    @Override
    public List<YoungCollect> queryByType(Integer userId, Byte type, Integer page, Integer size) {

        YoungCollectExample example = new YoungCollectExample();
        example.or().andUserIdEqualTo(userId).andTypeEqualTo(type).andDeletedEqualTo(false);
        example.setOrderByClause(YoungCollect.Column.addTime.desc());
        PageHelper.startPage(page, size);
        return youngCollectMapper.selectByExample(example);
    }

    @Override
    public int countByType(Integer userId, Byte type) {
        YoungCollectExample example = new YoungCollectExample();
        example.or().andUserIdEqualTo(userId).andTypeEqualTo(type).andDeletedEqualTo(false);
        return (int) youngCollectMapper.countByExample(example);
    }

    @Override
    public YoungCollect queryByTypeAndValue(Integer userId, Byte type, Integer valueId) {

        YoungCollectExample example = new YoungCollectExample();
        example.or().andUserIdEqualTo(userId)
                .andValueIdEqualTo(valueId)
                .andTypeEqualTo(type)
                .andDeletedEqualTo(false);
        return youngCollectMapper.selectOneByExample(example);
    }

    @Override
    public Integer deleteById(Integer id) {
        return youngCollectMapper.logicalDeleteByPrimaryKey(id);
    }

    @Override
    public Integer add(YoungCollect collect) {
        collect.setAddTime(LocalDateTime.now());
        collect.setUpdateTime(LocalDateTime.now());
        return youngCollectMapper.insertSelective(collect);
    }
}
