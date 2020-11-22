package com.young.mall.service;

import com.young.db.entity.YoungCategory;

import java.util.List;

/**
 * @Description: 客户端商品分类查询
 * @Author: yqz
 * @CreateDate: 2020/11/22 11:36
 */
public interface ClientCategoryService {

    /**
     * 查询一级分类
     *
     * @return
     */
    List<YoungCategory> queryLevelFirst();

}
