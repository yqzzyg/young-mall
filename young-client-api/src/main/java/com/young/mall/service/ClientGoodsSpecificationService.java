package com.young.mall.service;

import com.young.db.entity.YoungGoodsSpecification;
import com.young.mall.domain.ClientGoodsSpecificationVO;

import java.util.List;

/**
 * @Description: 商品规格
 * @Author: yqz
 * @CreateDate: 2020/12/2 21:29
 */
public interface ClientGoodsSpecificationService {

    /**
     * 根据商品id查询定制商品属性
     *
     * @param id
     * @return
     */
    List<ClientGoodsSpecificationVO> getSpecificationVoList(Integer id);

    /**
     * 根据商品id查询商品属性列表
     *
     * @param id
     * @return
     */
    List<YoungGoodsSpecification> queryByGid(Integer id);
}
