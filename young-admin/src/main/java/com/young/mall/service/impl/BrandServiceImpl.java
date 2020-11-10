package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungBrandMapper;
import com.young.db.entity.YoungBrand;
import com.young.db.entity.YoungBrandExample;
import com.young.db.mapper.BrandMapper;
import com.young.db.pojo.BrandPojo;
import com.young.mall.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/1 14:46
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private YoungBrandMapper youngBrandMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public Optional<List<YoungBrand>> queryBrandList(String id, String name,
                                                     Integer page, Integer size,
                                                     String sort, String order) {
        YoungBrandExample example = new YoungBrandExample();
        YoungBrandExample.Criteria criteria = example.createCriteria();


        if (StrUtil.isNotEmpty(id)) {
            criteria.andIdEqualTo(Integer.valueOf(id));
        }
        if (StrUtil.isNotEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (StrUtil.isNotEmpty(sort) && StrUtil.isNotEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        List<YoungBrand> brandList = youngBrandMapper.selectByExample(example);

        return Optional.ofNullable(brandList);
    }

    @Override
    public Optional<List<YoungBrand>> all() {

        YoungBrandExample example = new YoungBrandExample();
        example.createCriteria().andDeletedEqualTo(false);
        List<YoungBrand> brandList = youngBrandMapper.selectByExample(example);
        return Optional.ofNullable(brandList);
    }

    @Override
    public Optional<List<BrandPojo>> listBrand() {
        List<BrandPojo> listBrand = brandMapper.listBrand();

        return Optional.ofNullable(listBrand);
    }
}
