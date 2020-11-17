package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungGrouponRulesMapper;
import com.young.db.entity.YoungGrouponRules;
import com.young.db.entity.YoungGrouponRulesExample;
import com.young.mall.service.MallGroupRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 团购规则
 * @Author: yqz
 * @CreateDate: 2020/11/16 15:27
 */
@Service
public class MallGroupRuleServiceImpl implements MallGroupRuleService {

    @Autowired
    private YoungGrouponRulesMapper youngGrouponRulesMapper;

    @Override
    public YoungGrouponRules queryById(Integer id) {
        YoungGrouponRulesExample example = new YoungGrouponRulesExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return youngGrouponRulesMapper.selectOneByExample(example);
    }

    /**
     * 团购规则列表
     *
     * @param goodsSn
     * @param page
     * @param size
     * @param sort
     * @param order
     * @return
     */
    @Override
    public Optional<List<YoungGrouponRules>> list(String goodsSn,
                                                  Integer page, Integer size,
                                                  String sort, String order) {
        YoungGrouponRulesExample example = new YoungGrouponRulesExample();
        YoungGrouponRulesExample.Criteria criteria = example.createCriteria();

        if (StrUtil.isNotEmpty(goodsSn)) {
            criteria.andGoodsSnEqualTo(goodsSn);
        }
        criteria.andDeletedEqualTo(false);

        if (StrUtil.isNotEmpty(sort) && StrUtil.isNotEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, size);
        List<YoungGrouponRules> grouponRulesList = youngGrouponRulesMapper.selectByExample(example);
        return Optional.ofNullable(grouponRulesList);
    }

    @Override
    public Optional<Integer> updateById(YoungGrouponRules youngGrouponRules) {
        youngGrouponRules.setUpdateTime(LocalDateTime.now());
        int count = youngGrouponRulesMapper.updateByPrimaryKeySelective(youngGrouponRules);
        return Optional.ofNullable(count);
    }

    @Override
    public Optional<Integer> createRoles(YoungGrouponRules youngGrouponRules) {
        youngGrouponRules.setAddTime(LocalDateTime.now());
        youngGrouponRules.setUpdateTime(LocalDateTime.now());
        int count = youngGrouponRulesMapper.insertSelective(youngGrouponRules);
        return Optional.ofNullable(count);
    }

    @Override
    public Optional<Integer> delete(Integer id) {
        int count = youngGrouponRulesMapper.deleteByPrimaryKey(id);
        return Optional.ofNullable(count);
    }
}
