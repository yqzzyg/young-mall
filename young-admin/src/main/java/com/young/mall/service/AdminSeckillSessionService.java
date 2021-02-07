package com.young.mall.service;

import com.young.db.entity.YoungSeckillPromotionSession;
import com.young.db.pojo.SeckillPromotionSessionDetail;
import org.springframework.transaction.annotation.Transactional;

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
     * @param flashPromotionId
     * @param promotionSession
     * @return
     */
    int create(Long flashPromotionId, YoungSeckillPromotionSession promotionSession);

    /**
     * 修改场次
     *
     * @param id
     * @param promotionSession
     * @return
     */
    int update(Long id, YoungSeckillPromotionSession promotionSession);

    /**
     * 修改场次启用状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status);

    /**
     * 删除场次
     *
     * @param id
     * @param flashPromotionId 活动id
     * @return
     */
    int delete(Long id,Long flashPromotionId);

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    YoungSeckillPromotionSession getItem(Long id);

    /**
     * 获取全部可选场次及其数量
     *
     * @param promotionId
     * @return
     */
    List<SeckillPromotionSessionDetail> selectList(Long promotionId,
                                                   Integer size,
                                                   Integer page);

}
