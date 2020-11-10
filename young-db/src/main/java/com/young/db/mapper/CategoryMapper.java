package com.young.db.mapper;

import com.young.db.pojo.CatAndBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description: 用于自定义分类查询
 * @Author: yqz
 * @CreateDate: 2020/11/10 9:24
 */
@Mapper
public interface CategoryMapper {

    /**
     * @return list
     */
    List<CatAndBrand> selectCatAndBrand();
}
