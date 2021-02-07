package com.young.db.pojo;

import com.young.db.entity.YoungSeckillPromotionSession;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2021/2/7 13:41
 */
@Data
public class SeckillPromotionRelationProduct extends YoungSeckillPromotionSession {

    @ApiModelProperty("一对多商品id")
    private List<Integer> goodsIds;
}
