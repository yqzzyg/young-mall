package com.young.mall.service.impl;

import com.young.db.dao.YoungBrandMapper;
import com.young.db.entity.YoungBrand;
import com.young.mall.service.YoungBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Description: 品牌商业务
 * @Author: yqz
 * @CreateDate: 2020/11/10 22:42
 */
@Service
public class YoungBrandServiceImpl implements YoungBrandService {

    @Autowired
    private YoungBrandMapper youngBrandMapper;

    @Override
    public Optional<YoungBrand> findById(Integer brandId) {

        YoungBrand youngBrand = youngBrandMapper.selectByPrimaryKey(brandId);

        return Optional.ofNullable(youngBrand);
    }
}
