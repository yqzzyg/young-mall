package com.young.db.pojo;

import com.young.db.entity.YoungGoods;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: YoungGoods对象增强，增加一个库存字段
 * @Author: yqz
 * @CreateDate: 2021/2/2 17:46
 */
@Data
public class YoungGoodsPlus extends YoungGoods {

    @ApiModelProperty("库存")
    private Integer stock;
}
