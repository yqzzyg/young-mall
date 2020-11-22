package com.young.mall.service;

import com.young.db.entity.YoungBrand;

import java.util.List;

/**
 * @Description: 品牌
 * @Author: yqz
 * @CreateDate: 2020/11/22 17:29
 */
public interface ClientBrandService {

    /**
     * 品牌
     *
     * @param page
     * @param size
     * @return
     */
    List<YoungBrand> queryBrand(int page, int size);
}
