package com.young.db.mapper;

import com.young.db.entity.YoungOrder;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * @Description: 订单操作
 * @Author: yqz
 * @CreateDate: 2020/11/2 16:26
 */
public interface OrderMapper {
    int updateWithOptimisticLocker(@Param("lastUpdateTime") LocalDateTime lastUpdateTime,
                              @Param("order") YoungOrder order);
}
