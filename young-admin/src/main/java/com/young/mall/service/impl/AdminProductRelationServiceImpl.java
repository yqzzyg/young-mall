package com.young.mall.service.impl;

import com.young.db.dao.YoungSeckillPromotionProductRelationMapper;
import com.young.db.entity.YoungSeckillPromotionProductRelationExample;
import com.young.mall.service.AdminProductRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: 秒杀商品关联管理Service实现类
 * @Author: yqz
 * @CreateDate: 2021/2/1 22:19
 */
@Service
public class AdminProductRelationServiceImpl implements AdminProductRelationService {

    @Resource
    private YoungSeckillPromotionProductRelationMapper relationMapper;


    @Override
    public long getCount(Long promotionId, Long promotionSessionId) {

        YoungSeckillPromotionProductRelationExample example = new YoungSeckillPromotionProductRelationExample();
        example.createCriteria()
                .andFlashPromotionIdEqualTo(promotionId)
                .andFlashPromotionSessionIdEqualTo(promotionSessionId);
        return relationMapper.countByExample(example);
    }
}
