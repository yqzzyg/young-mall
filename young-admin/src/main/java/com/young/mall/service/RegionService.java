package com.young.mall.service;

import afu.org.checkerframework.checker.oigj.qual.O;
import com.young.db.entity.YoungRegion;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 行政区划
 * @Author: yqz
 * @CreateDate: 2020/11/1 4:17
 */
public interface RegionService {
    /**
     * 查询行政区划list
     * @param name
     * @param code
     * @param page 分页起始页
     * @param size 分页大小
     * @param sort 排序依据
     * @param order 排序方式
     * @return
     */
    Optional<List<YoungRegion>> queryRegionList(String name, Integer code,
                                                Integer page, Integer size,
                                                String sort, String order);
}
