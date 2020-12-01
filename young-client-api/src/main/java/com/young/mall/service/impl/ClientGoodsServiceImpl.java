package com.young.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungGoodsMapper;
import com.young.db.entity.YoungCategory;
import com.young.db.entity.YoungGoods;
import com.young.db.entity.YoungGoods.Column;
import com.young.db.entity.YoungGoodsExample;
import com.young.mall.service.ClientCategoryService;
import com.young.mall.service.ClientGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 客户端商品业务
 * @Author: yqz
 * @CreateDate: 2020/11/22 16:57
 */
@Service
public class ClientGoodsServiceImpl implements ClientGoodsService {

    @Autowired
    private YoungGoodsMapper youngGoodsMapper;

    @Autowired
    private ClientCategoryService clientCategoryService;

    Column[] columns = new Column[]{Column.id, Column.name, Column.brief, Column.picUrl, Column.isHot, Column.isNew,
            Column.counterPrice, Column.retailPrice};

    @Override
    public List<YoungGoods> queryByNew(int page, int size) {

        YoungGoodsExample example = new YoungGoodsExample();
        example.createCriteria().andIsNewEqualTo(true).andIsOnSaleEqualTo(true)
                .andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(page, size);
        List<YoungGoods> newGoodsList = youngGoodsMapper.selectByExampleSelective(example, columns);
        return newGoodsList;
    }

    @Override
    public List<YoungGoods> queryByHot(int page, int size) {

        YoungGoodsExample example = new YoungGoodsExample();
        example.createCriteria().andIsHotEqualTo(true).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("browse desc");

        PageHelper.startPage(page, size);
        List<YoungGoods> newGoodsList = youngGoodsMapper.selectByExampleSelective(example, columns);
        return newGoodsList;
    }

    @Override
    public List<YoungGoods> getGoodByCategoryId(List<Integer> cid, int page, int size) {
        YoungGoodsExample example = new YoungGoodsExample();
        example.or().andCategoryIdIn(cid).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("sort_order  asc");
        PageHelper.startPage(page, size);
        List<YoungGoods> goodsList = youngGoodsMapper.selectByExampleSelective(example, columns);
        return goodsList;
    }

    @Override
    public Integer getGoodsCountOnSale() {
        YoungGoodsExample example = new YoungGoodsExample();
        example.createCriteria().andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return (int) youngGoodsMapper.countByExample(example);
    }

    @Override
    public Map<String, Object> getCategoryById(Integer id) {
        YoungCategory category = clientCategoryService.findById(id);

        YoungCategory parent = null;
        List<YoungCategory> children = null;
        //判断是否为一级分类
        if (category.getPid() == 0) {
            parent = category;
            children = clientCategoryService.getLeveSecondByPid(category.getId());
            //如果该一级分类对应的二级分类大于 0，当前分类则取第一个，否则，当前分类还是一级分类
            category = children.size() > 0 ? children.get(0) : category;
        } else {
            //非一级分类
            //先查出一级分类
            parent = clientCategoryService.findById(category.getPid());
            //再查出二级分类
            children = clientCategoryService.getLeveSecondByPid(category.getPid());
        }
        Map<String, Object> data = new HashMap<>();

        data.put("currentCategory", category);
        //用户显示页面title
        data.put("parentCategory", parent);
        data.put("brotherCategory", children);
        return data;
    }

    @Override
    public List<YoungGoods> querySelective(Integer catId, Integer brandId,
                                           String keywords, Boolean isHot,
                                           Boolean isNew, Integer page,
                                           Integer limit, String sort,
                                           String order) {
        YoungGoodsExample example = new YoungGoodsExample();
        //sql:select id , `name` , brief , pic_url , is_hot , is_new , counter_price , retail_price from young_goods WHERE ( category_id = ? and is_on_sale = ? and deleted = ? ) or( category_id = ? and is_on_sale = ? and deleted = ? ) order by sort_order asc LIMIT ?
        //参数：100100601(Integer), true(Boolean), false(Boolean), 100100601(Integer), true(Boolean), false(Boolean), 10(Integer)
        YoungGoodsExample.Criteria criteria1 = example.or();
        YoungGoodsExample.Criteria criteria2 = example.or();

        if (!StringUtils.isEmpty(catId) && catId != 0) {
            criteria1.andCategoryIdEqualTo(catId);
            criteria2.andCategoryIdEqualTo(catId);
        }
        if (!StringUtils.isEmpty(brandId)) {
            criteria1.andBrandIdEqualTo(brandId);
            criteria2.andBrandIdEqualTo(brandId);
        }
        if (!StringUtils.isEmpty(isNew)) {
            criteria1.andIsNewEqualTo(isNew);
            criteria2.andIsNewEqualTo(isNew);
        }
        if (!StringUtils.isEmpty(isHot)) {
            criteria1.andIsHotEqualTo(isHot);
            criteria2.andIsHotEqualTo(isHot);
        }
        if (!StringUtils.isEmpty(keywords)) {
            criteria1.andKeywordsLike("%" + keywords + "%");
            criteria2.andNameLike("%" + keywords + "%");
        }
        criteria1.andIsOnSaleEqualTo(true);
        criteria2.andIsOnSaleEqualTo(true);
        criteria1.andDeletedEqualTo(false);
        criteria2.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return youngGoodsMapper.selectByExampleSelective(example, columns);
    }

    @Override
    public List<Integer> getCatIds(Integer brandId, String keywords, Boolean isHot, Boolean isNew) {
        YoungGoodsExample example = new YoungGoodsExample();
        YoungGoodsExample.Criteria criteria1 = example.or();
        YoungGoodsExample.Criteria criteria2 = example.or();

        if (!StringUtils.isEmpty(brandId)) {
            criteria1.andBrandIdEqualTo(brandId);
            criteria2.andBrandIdEqualTo(brandId);
        }
        if (!StringUtils.isEmpty(isNew)) {
            criteria1.andIsNewEqualTo(isNew);
            criteria2.andIsNewEqualTo(isNew);
        }
        if (!StringUtils.isEmpty(isHot)) {
            criteria1.andIsHotEqualTo(isHot);
            criteria2.andIsHotEqualTo(isHot);
        }
        if (!StringUtils.isEmpty(keywords)) {
            criteria1.andKeywordsLike("%" + keywords + "%");
            criteria2.andNameLike("%" + keywords + "%");
        }
        criteria1.andIsOnSaleEqualTo(true);
        criteria2.andIsOnSaleEqualTo(true);
        criteria1.andDeletedEqualTo(false);
        criteria2.andDeletedEqualTo(false);

        List<YoungGoods> goodsList = youngGoodsMapper.selectByExampleSelective(example, Column.categoryId);
        List<Integer> cats = new ArrayList<Integer>();
        for (YoungGoods goods : goodsList) {
            cats.add(goods.getCategoryId());
        }
        return cats;
    }
}
