package com.young.mall.service;

import com.young.db.entity.YoungSeckillPromotionSession;
import com.young.db.pojo.SeckillPromotionSessionDetail;

import java.util.List;

/**
 * @Description: 限时购场次管理Service
 * @Author: yqz
 * @CreateDate: 2021/2/1 21:42
 */
public interface AdminSeckillSessionService {

    /**
     * 添加场次
     *
     * @param promotionSession
     * @return
     */
    int create(YoungSeckillPromotionSession promotionSession);

    /**
     * 修改场次
     *
     * @param id
     * @param promotionSession
     * @return
     */
    int update(Long id, YoungSeckillPromotionSession promotionSession);


    /**
     * 根据启用状态获取场次列表
     *
     * @return
     */
    List<YoungSeckillPromotionSession> list();

    /**
     * 获取全部可选场次及其数量
     *
     * @param promotionId
     * @return
     */
    List<SeckillPromotionSessionDetail> selectList(Long promotionId);

}
