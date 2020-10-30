package com.young.mall.service;

import com.young.db.entity.YoungCollect;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 用户收藏service
 * @Author: yqz
 * @CreateDate: 2020/10/30 22:20
 */
public interface CollectService {

    /**
     * 查询所有收藏
     * @param userId 用户Id
     * @param valueId  如果type=0，则是商品ID；如果type=1，则是专题ID
     * @param page 起始页
     * @param size 分页大小
     * @param sort 排序依据字段
     * @param order 排序方式
     * @return
     */
    Optional<List<YoungCollect>> queryCollectList(String userId, String valueId,
                                                  Integer page, Integer size,
                                                  String sort, String order);
}
