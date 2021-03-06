package com.young.db.pojo;

import com.young.db.entity.YoungSeckillPromotionSession;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 包含商品数量的场次信息
 * @Author: yqz
 * @CreateDate: 2021/2/1 21:39
 */
@Data
public class SeckillPromotionSessionDetail extends YoungSeckillPromotionSession {

    @ApiModelProperty("商品数量")
    private Integer productCount;

    @ApiModelProperty("开启状态")
    private Integer status;
}
