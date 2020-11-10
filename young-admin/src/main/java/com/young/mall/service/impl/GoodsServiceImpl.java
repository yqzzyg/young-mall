package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungGoodsMapper;
import com.young.db.entity.YoungBrand;
import com.young.db.entity.YoungCategory;
import com.young.db.entity.YoungGoods;
import com.young.db.entity.YoungGoodsExample;
import com.young.db.pojo.BrandPojo;
import com.young.db.pojo.CatAndBrand;
import com.young.mall.dto.CatAndBrandVo;
import com.young.mall.service.BrandService;
import com.young.mall.service.GoodsService;
import com.young.mall.service.YoungCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
