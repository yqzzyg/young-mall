package com.young.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungCategoryMapper;
import com.young.db.entity.YoungCategory;
import com.young.db.entity.YoungCategoryExample;
import com.young.mall.common.ResBean;
import com.young.mall.exception.Asserts;
import com.young.mall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/1 15:00
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private YoungCategoryMapper categoryMapper;

    @Override
    public Optional<List<YoungCategory>> queryCateGoryList(String id, String name,
                                                           Integer page, Integer size,
                                                           String sort, String order) {

        YoungCategoryExample example = new YoungCategoryExample();
        YoungCategoryExample.Criteria criteria = example.createCriteria();

        if (StrUtil.isNotEmpty(id)) {
            criteria.andIdEqualTo(Integer.valueOf(id));
        }
        if (StrUtil.isNotEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);
        if (StrUtil.isNotEmpty(sort) && StrUtil.isNotEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        List<YoungCategory> categoryList = categoryMapper.selectByExample(example);
        return Optional.ofNullable(categoryList);
    }

    @Override
    public Optional<List<YoungCategory>> queryLevelFirst() {
        YoungCategoryExample example = new YoungCategoryExample();

        example.createCriteria().andLevelEqualTo("L1")
                .andDeletedEqualTo(false);
        List<YoungCategory> categoryList = categoryMapper.selectByExample(example);
        return Optional.ofNullable(categoryList);
    }

    @Override
    public Optional<Integer> delete(Integer id) {

        YoungCategory category = new YoungCategory();
        category.setId(id);
        category.setDeleted(true);
        int count = categoryMapper.updateByPrimaryKeySelective(category);
        return Optional.ofNullable(count);
    }

    @Override
    public Optional<Integer> creat(YoungCategory category) {

        ResBean validate = validate(category);

        if (!BeanUtil.isEmpty(validate)) {
            Asserts.fail(validate.getMsg());
        }
        category.setAddTime(new Date());
        category.setUpdateTime(new Date());
        int count = categoryMapper.insertSelective(category);
        return Optional.ofNullable(count);
    }

    private ResBean validate(YoungCategory category) {
        String name = category.getName();
        if (StringUtils.isEmpty(name)) {
            return ResBean.validateFailed("类目名称不能为空");
        }

        String level = category.getLevel();
        if (StringUtils.isEmpty(level)) {
            return ResBean.validateFailed("级别不能为空");

        }
        if (!level.equals("L1") && !level.equals("L2")) {
            return ResBean.validateFailed("级别目前仅支持两级");
        }

        Integer pid = category.getPid();
        if (level.equals("L2") && (pid == null)) {
            return ResBean.validateFailed("选择二级类目时，父类目不能为空");
        }

        return null;
    }

}
