package com.young.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungCategoryMapper;
import com.young.db.entity.YoungCategory;
import com.young.db.entity.YoungCategoryExample;
import com.young.db.mapper.CategoryMapper;
import com.young.db.pojo.CategoryAndGoodsPojo;
import com.young.mall.service.ClientCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 客户端商品分类查询
 * @Author: yqz
 * @CreateDate: 2020/11/22 11:37
 */
@Service
public class ClientCategoryServiceImpl implements ClientCategoryService {

    @Autowired
    private YoungCategoryMapper youngCategoryMapper;

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<YoungCategory> queryLevelFirst() {
        YoungCategoryExample example = new YoungCategoryExample();

        example.createCriteria().andLevelEqualTo("L1")
                .andDeletedEqualTo(false);
        List<YoungCategory> categoryList = youngCategoryMapper.selectByExample(example);
        return categoryList;
    }

    @Override
    public List<CategoryAndGoodsPojo> getCategoryAndGoodsPojo(int page,int size) {

        PageHelper.startPage(page,size);
        List<CategoryAndGoodsPojo> categoryAndGoodsPojoList = categoryMapper.getCategoryAndGoods();
        return categoryAndGoodsPojoList;
    }
}
