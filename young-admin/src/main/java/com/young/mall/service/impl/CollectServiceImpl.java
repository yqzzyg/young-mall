package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungCollectMapper;
import com.young.db.entity.YoungCollect;
import com.young.db.entity.YoungCollectExample;
import com.young.mall.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 收藏列表
 * @Author: yqz
 * @CreateDate: 2020/10/30 22:25
 */
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private YoungCollectMapper collectMapper;

    @Override
    public Optional<List<YoungCollect>> queryCollectList(String userId, String valueId,
                                                         Integer page, Integer size,
                                                         String sort, String order) {

        YoungCollectExample example = new YoungCollectExample();

        YoungCollectExample.Criteria criteria = example.createCriteria();
        if (StrUtil.isNotBlank(userId)) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (StrUtil.isNotBlank(valueId)) {
            criteria.andValueIdEqualTo(Integer.valueOf(valueId));
        }

        criteria.andDeletedEqualTo(false);

        if (StrUtil.isNotBlank(sort) && StrUtil.isNotBlank(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        List<YoungCollect> collectList = collectMapper.selectByExample(example);

        return Optional.ofNullable(collectList);
    }
}
