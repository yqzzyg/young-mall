package com.young.mall.service;

import com.young.db.entity.YoungOrder;
import com.young.mall.common.ResBean;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description: 订单service
 * @Author: yqz
 * @CreateDate: 2020/10/29 15:52
 */
public interface OrderService {

    /**
     * 订单数量
     * @param userId 用户id
     * @return
     */
    Optional<Integer> count(Integer userId);

    /**
     * 总订单数
     *
     * @return
     */
    Optional<Integer> count();

    /**
     * 订单list查询
     * @param userId 用户ID
     * @param orderSn 订单编号
     * @param orderStatusArray 订单状态
     * @param page 分页
     * @param size 分页大小
     * @param sort 排序依据
     * @param order 排序方式
     * @return
     */
    Optional<List<YoungOrder>> list(Integer userId, String orderSn,
                                    List<Short> orderStatusArray,
                                    Integer page, Integer size,
                                    String sort, String order);

    /**
     * 订单详情
     * @param id 顶顶那id
     * @return
     */
    Optional<Map<String,Object>> detail(Integer id);

    /**
     * 退款操作
     * @param map 入参
     * @return
     */
    ResBean refund(Map<String,Object> map);
}
