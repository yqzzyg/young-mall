package com.young.db.pojo;

import com.young.db.entity.YoungSeckillPromotionSession;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 包含商品数量的场次信息
 * @Author: yqz
 * @CreateDate: 2021/2/1 21:39
 */
public class SeckillPromotionSessionDetail extends YoungSeckillPromotionSession {
    @Setter
    @Getter
    @ApiModelProperty("商品数量")
    private Long productCount;
}
