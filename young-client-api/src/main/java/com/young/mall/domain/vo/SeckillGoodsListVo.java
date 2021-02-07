package com.young.mall.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 获取秒杀列表入参
 * @Author: yqz
 * @CreateDate: 2021/2/7 18:03
 */
@Data
public class SeckillGoodsListVo implements Serializable {

    @ApiModelProperty("商品id集合")
    private List<Integer> goodsIds;
    @ApiModelProperty("分类")
    private Integer categoryId;

    @ApiModelProperty("品牌")
    private Integer brandId;
    @ApiModelProperty("关键词")
    private String keyword;
    @ApiModelProperty("是否新品")
    private Boolean isNew = false;
    @ApiModelProperty("是否热门")
    private Boolean isHot = false;

    @ApiModelProperty("分页页数")
    private Integer page = 1;
    @ApiModelProperty("每页数据量")
    private Integer size = 5;
    @ApiModelProperty("排序依据")
    private String sort = "sort_order";

    @ApiModelProperty("排序方式")
    private String order = "asc";
}
