package com.young.mall.service;

import com.young.db.pojo.SeckillPromotionSessionDetail;

import java.util.List;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2021/2/1 21:42
 */
public interface AdminSeckillSessionService {

    /**
     * 获取全部可选场次及其数量
     *
     * @param promotionId
     * @return
     */
    List<SeckillPromotionSessionDetail> selectList(Long promotionId);

}
