package com.young.mall.service;

import com.young.db.entity.YoungCategory;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 商品分类
 * @Author: yqz
 * @CreateDate: 2020/11/1 14:58
 */
public interface CategoryService {


    /**
     * 商品分类
     * @param id 类目ID
     * @param name 类目名称
     * @param page 分页
     * @param size 分页大小
     * @param sort 排序依据
     * @param order 排序方式
     * @return
     */
    Optional<List<YoungCategory>> queryCateGoryList(String id, String name,
                                                    Integer page, Integer size,
                                                    String sort, String order);

    Optional<List<YoungCategory>> queryLevelFirst();


    /**
     * 删除分类，逻辑删除、修改状态
     * @param id 主键id
     * @return
     */
    Optional<Integer> delete(Integer id);
}
