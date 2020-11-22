package com.young.db.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 首页展示商品分类和商品
 * @Author: yqz
 * @CreateDate: 2020/11/22 23:01
 */
@Data
public class CategoryAndGoodsPojo implements Serializable {

    private Integer id;

    @ApiModelProperty(value = "分类名称")
    private String name;
    private List<CategoryAndGoodsChildrenPojo> goodsList;
}
