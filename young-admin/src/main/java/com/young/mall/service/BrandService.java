package com.young.mall.service;

import com.young.db.entity.YoungBrand;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 品牌
 * @Author: yqz
 * @CreateDate: 2020/11/1 14:43
 */
public interface BrandService {

    /**
     * 品牌
     *
     * @param id    品牌商ID
     * @param name  品牌名称
     * @param page  分页
     * @param size  分页大小
     * @param sort  排序依据
     * @param order 排序放hi
     * @return
     */
    Optional<List<YoungBrand>> queryBrandList(String id, String name,
                                              Integer page, Integer size,
                                              String sort, String order);


    /**
     * 查询所有品牌
     *
     * @return
     */
    Optional<List<YoungBrand>> all();
}
