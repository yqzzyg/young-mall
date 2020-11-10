package com.young.mall.service;

import com.young.db.entity.YoungGoodsSpecification;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 商品规格业务
 * @Author: yqz
 * @CreateDate: 2020/11/10 15:44
 */
public interface YoungGoodsSpecificationService {

    /**
     * 查询商品货品
     * @param id
     * @return
     */
    Optional<List<YoungGoodsSpecification>> queryByGid(Integer id);


    /**
     * 删除
     * @param gid
     * @return
     */
    Optional<Integer> deleteByGid(Integer gid);
}
