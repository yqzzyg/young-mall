package com.young.db.mapper;

import com.young.db.pojo.BrandPojo;

import java.util.List;

/**
 * @Description: 自自定义sql查询品牌
 * @Author: yqz
 * @CreateDate: 2020/11/10 14:51
 */
public interface BrandMapper {

    /**
     * 查询品牌
     * @return
     */
    List<BrandPojo> listBrand();
}
