package com.young.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungCategoryMapper;
import com.young.db.entity.YoungCategory;
import com.young.db.entity.YoungCategory.Column;
import com.young.db.entity.YoungCategoryExample;
import com.young.db.entity.YoungGoods;
import com.young.db.mapper.CategoryMapper;
import com.young.db.pojo.CategoryAndGoodsPojo;
import com.young.mall.service.ClientCategoryService;
import com.young.mall.service.ClientGoodsService;
import com.young.mall.system.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private ClientGoodsService goodsService;

    private Column[] result = {Column.id, Column.name,
            Column.iconUrl};

    @Override
    public List<YoungCategory> queryLevelFirst() {
        YoungCategoryExample example = new YoungCategoryExample();

        example.createCriteria().andLevelEqualTo("L1")
                .andDeletedEqualTo(false);
        List<YoungCategory> categoryList = youngCategoryMapper.selectByExample(example);
        return categoryList;
    }

    @Override
    public List<CategoryAndGoodsPojo> getCategoryAndGoodsPojo(int page, int size) {

        PageHelper.startPage(page, size);
        List<CategoryAndGoodsPojo> categoryAndGoodsPojoList = categoryMapper.getCategoryAndGoods();
        return categoryAndGoodsPojoList;
    }

    @Override
    public List<Map<String, Object>> getCategoryList(int page, int size) {
        List<Map<String, Object>> categoryList = new ArrayList<>();
        List<YoungCategory> leveFirst = getLeveFirst(page, size);
        for (YoungCategory category : leveFirst) {
            //根据一级分类的 id 查询该 id 对应的二级分类，二级分类的pid为一级分类的id
            List<YoungCategory> categoryLeveSecond = getLeveSecondByPid(category.getId());
            //循环取出二级分类对应主键id
            List<Integer> catLeveSecondIds = categoryLeveSecond.stream().map(youngCategory -> youngCategory.getId())
                    .collect(Collectors.toList());
            //根据商品二级 id 查询该id对应的商品，young_goods表
            List<YoungGoods> goodsList = goodsService.getGoodByCategoryId(catLeveSecondIds, 0, SystemConfig.getCatlogMoreLimit());

            Map<String, Object> categoryAndGoods = new HashMap<>();
            categoryAndGoods.put("id", category.getId());
            categoryAndGoods.put("name", category.getName());
            categoryAndGoods.put("goodsList", goodsList);
            categoryList.add(categoryAndGoods);
        }
        return categoryList;
    }

    @Override
    public List<YoungCategory> getLeveFirst(int page, int size) {
        YoungCategoryExample example = new YoungCategoryExample();
        example.createCriteria().andLevelEqualTo("L1").andNameNotEqualTo("推荐")
                .andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        List<YoungCategory> categoryList = youngCategoryMapper.selectByExampleSelective(example, result);
        return categoryList;
    }

    @Override
    public List<YoungCategory> getLeveSecondByPid(Integer pid) {
        YoungCategoryExample example = new YoungCategoryExample();
        example.createCriteria().andPidEqualTo(pid).andDeletedEqualTo(false);
        List<YoungCategory> categoryList = youngCategoryMapper.selectByExampleSelective(example, result);
        return categoryList;
    }
}
