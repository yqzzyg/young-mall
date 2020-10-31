package com.young.mall.service;

import com.young.db.entity.YoungFootprint;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 用户足迹Service
 * @Author: yqz
 * @CreateDate: 2020/10/31 16:13
 */
public interface FootprintService {

    /**
     * 查询用户足迹
     * @param userId 用户ID
     * @param goodsId 商品ID
     * @param page 分页起始页
     * @param size 分页大小
     * @param sort 排序依据字段
     * @param order 排序方式
     * @return
     */
    Optional<List<YoungFootprint>> queryFootPrint(String userId, String goodsId,
                                                  Integer page, Integer size,
                                                  String sort, String order);
}
