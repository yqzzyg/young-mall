package com.young.mall.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/2 15:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderHandleOption {
    // 取消操作
    private boolean cancel = false;
    // 删除操作
    private boolean delete = false;
    // 支付操作
    private boolean pay = false;
    // 评论操作
    private boolean comment = false;
    // 确认收货操作
    private boolean confirm = false;
    // 取消订单并退款操作
    private boolean refund = false;
    // 再次购买
    private boolean rebuy = false;
}
