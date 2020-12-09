package com.young.mall.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/12/9 16:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartCheckDto implements Serializable {

    @NotNull(message = "入驻品牌商编码不能为空")
    @ApiModelProperty("入驻品牌商编码")
    List<Integer> productIds;

    @NotNull(message = "购物车中商品是否选择状态不能为空")
    @ApiModelProperty("购物车中商品是否选择状态")
    Integer isChecked;
}
