package com.young.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungGoodsMapper;
import com.young.db.entity.*;
import com.young.db.pojo.BrandPojo;
import com.young.db.pojo.CatAndBrand;
import com.young.mall.common.ResBean;
import com.young.mall.exception.Asserts;
import com.young.mall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/10/29 15:47
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private YoungGoodsMapper goodsMapper;

    @Autowired
    private YoungCategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private YoungGoodsService youngGoodsService;
    @Autowired
    private YoungGoodsSpecificationService youngGoodsSpecificationService;
    @Autowired
    private GoodsAttributeService goodsAttributeService;
    @Autowired
    private YoungCategoryService youngCategoryService;

    @Autowired
    private YoungGoodsProductService youngGoodsProductService;

    @Override
    public Optional<Integer> count() {

        YoungGoodsExample example = new YoungGoodsExample();

        example.createCriteria().andDeletedEqualTo(false);

        long count = goodsMapper.countByExample(example);
        return Optional.ofNullable((int) count);
    }

    @Override
    public Optional<List<YoungGoods>> querySelective(String goodsSn, String name,
                                                     Integer page, Integer size,
                                                     String sort, String order) {

        YoungGoodsExample example = new YoungGoodsExample();
        YoungGoodsExample.Criteria criteria = example.createCriteria();
        if (StrUtil.isNotEmpty(goodsSn)) {
            criteria.andGoodsSnLike("%" + goodsSn + "%");
        }
        if (StrUtil.isNotEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);
        if (StrUtil.isNotEmpty(sort) && StrUtil.isNotEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        List<YoungGoods> goodsList = goodsMapper.selectByExample(example);
        return Optional.ofNullable(goodsList);
    }

    @Override
    public Optional<Map> catAndBrand() {

        List<CatAndBrand> categoryList = categoryService.selectCatAndBrand().get();
        /*// 管理员设置“所属分类”
        List<YoungCategory> youngCategories = categoryService.queryLevelFirst().get();
        List<CatAndBrandVo> categoryList = new ArrayList<>(youngCategories.size());
        for (YoungCategory category : youngCategories) {
            CatAndBrandVo vo = new CatAndBrandVo();
            vo.setValue(category.getId());
            vo.setLabel(category.getName());
            List<YoungCategory> youngCategoryList = categoryService.queryByPid(category.getId()).get();
            List<CatAndBrandVo> children = new ArrayList<>();
            for (YoungCategory youngCategory : youngCategoryList) {
                CatAndBrandVo brandVo = new CatAndBrandVo();
                brandVo.setValue(youngCategory.getId());
                brandVo.setLabel(youngCategory.getName());
                children.add(brandVo);
            }
            vo.setChildren(children);
            categoryList.add(vo);
        }

        // 管理员设置“所属品牌商”
        List<YoungBrand> list = brandService.all().get();
        List<Map<String, Object>> brandList = new ArrayList<>(list.size());

        for (YoungBrand youngBrand : list) {
            Map<String, Object> map = new HashMap<>(3);
            map.put("value", youngBrand.getId());
            map.put("label", youngBrand.getName());
            brandList.add(map);
        }*/
        List<BrandPojo> brandList = brandService.listBrand().get();

        Map<String, Object> data = new HashMap<>();
        data.put("categoryList", categoryList);
        data.put("brandList", brandList);
        return Optional.ofNullable(data);
    }

    @Override
    public Optional<Map> detail(Integer id) {

        YoungGoods goods = youngGoodsService.findById(id).get();

        List<YoungGoodsProduct> youngGoodsProducts = youngGoodsProductService.queryByGoodsId(id).get();
        List<YoungGoodsSpecification> specificationList = youngGoodsSpecificationService.queryByGid(id).get();
        List<YoungGoodsAttribute> attributeList = goodsAttributeService.queryByGoodsId(id).get();

        Integer categoryId = goods.getCategoryId();
        YoungCategory youngCategory = youngCategoryService.findById(categoryId).get();
        Integer[] categoryIds = {};
        if (!BeanUtil.isEmpty(youngCategory)) {
            Integer pid = youngCategory.getPid();
            categoryIds = new Integer[]{pid, categoryId};
        }
        Map<String, Object> map = new HashMap<>();
        map.put("goods", goods);
        map.put("specifications", specificationList);
        map.put("products", youngGoodsProducts);
        map.put("attributes", attributeList);
        map.put("categoryIds", categoryIds);
        return Optional.ofNullable(map);
    }

    /**
     * 编辑商品
     * <p>
     * TODO 目前商品修改的逻辑是
     * 1. 更新Dts_goods表
     * 2. 逻辑删除Dts_goods_specification、Dts_goods_attribute、Dts_goods_product
     * 3. 添加Dts_goods_specification、Dts_goods_attribute、Dts_goods_product
     * <p>
     * 这里商品三个表的数据采用删除再添加的策略是因为 商品编辑页面，支持管理员添加删除商品规格、添加删除商品属性，因此这里仅仅更新是不可能的，
     * 只能删除三个表旧的数据，然后添加新的数据。 但是这里又会引入新的问题，就是存在订单商品货品ID指向了失效的商品货品表。
     * 因此这里会拒绝管理员编辑商品，如果订单或购物车中存在商品。 所以这里可能需要重新设计。
     */
    @Override
    public Optional<Integer> update(YoungGoods youngGoods) {

        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<Integer> delete(YoungGoods youngGoods) {
        Integer id = youngGoods.getId();
        if (id == null) {
            Asserts.fail("参数错误");
        }
        int delGoodsCount = goodsMapper.logicalDeleteByPrimaryKey(id);
        Integer delSpecCount = youngGoodsSpecificationService.deleteByGid(id).get();
        Integer delAttrCount = goodsAttributeService.delete(id).get();
        Integer delGoodsProductCount = youngGoodsProductService.deleteByGid(id).get();


        return Optional.ofNullable(delGoodsCount + delSpecCount + delAttrCount + delGoodsProductCount);
    }
}
