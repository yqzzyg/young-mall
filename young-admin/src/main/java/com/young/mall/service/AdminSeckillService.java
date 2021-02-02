package com.young.mall.service;

import com.young.db.entity.YoungSeckillPromotion;

import java.util.List;

/**
 * @Description: 秒杀活动管理Service
 * @Author: yqz
 * @CreateDate: 2021/2/1 16:17
 */
public interface AdminSeckillService {


    /**
     * 添加活动
     *
     * @param flashPromotion
     * @return
     */
    int create(YoungSeckillPromotion flashPromotion);

    /**
     * 删除单个活动
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 修改指定活动
     *
     * @param id
     * @param flashPromotion
     * @return
     */
    int update(Long id, YoungSeckillPromotion flashPromotion);

    /**
     * 修改上下线状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status);

    /**
     * 获取详细信息
     *
     * @param id
     * @return
     */
    YoungSeckillPromotion getItem(Long id);

    /**
     * 分页查询活动
     *
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    List<YoungSeckillPromotion> list(String keyword, Integer pageSize, Integer pageNum);
}
