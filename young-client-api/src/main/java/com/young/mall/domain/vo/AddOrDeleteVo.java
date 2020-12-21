package com.young.mall.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description: 添加或者删除商品收藏
 * @Author: yqz
 * @CreateDate: 2020/12/14 14:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddOrDeleteVo implements Serializable {

    @NotNull(message = "收藏类型不能为空")
    private Byte type;
    @NotNull(message = "商品或者专题id不能为空")
    private Integer valueId;
}
