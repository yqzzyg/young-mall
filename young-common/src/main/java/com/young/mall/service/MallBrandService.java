package com.young.mall.service;

import com.young.db.entity.YoungBrand;

import java.util.Optional;

/**
 * @Description: 品牌商业务
 * @Author: yqz
 * @CreateDate: 2020/11/10 22:39
 */
public interface MallBrandService {

    /**
     * 根据brandId查询品牌商
     *
     * @param brandId
     * @return
     */
    Optional<YoungBrand> findById(Integer brandId);

}
