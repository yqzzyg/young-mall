package com.young.mall.service;

import com.young.db.entity.YoungAddress;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 地址相关service
 * @Author: yqz
 * @CreateDate: 2020/10/30 21:15
 */
public interface AddressService {

    /**
     * 查询地址list
     * @param userId 用户ID
     * @param name 用户姓名
     * @param page 起始页
     * @param size 分页大小
     * @param sort 排序
     * @param order
     * @return
     */
    Optional<List<YoungAddress>> queryAddressList(Integer userId, String name,
                                                  Integer page, Integer size,
                                                  String sort, String order);
}
