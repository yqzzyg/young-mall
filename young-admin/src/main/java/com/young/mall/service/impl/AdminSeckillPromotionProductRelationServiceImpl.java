package com.young.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.young.db.mapper.SeckillPromotionProductRelationMapper;
import com.young.db.pojo.SeckillPromotionProduct;
import com.young.mall.service.AdminSeckillPromotionProductRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 限时购商品关联管理Service实现类
 * @Author: yqz
 * @CreateDate: 2021/2/1 15:23
 */
@Service
public class AdminSeckillPromotionProductRelationServiceImpl implements AdminSeckillPromotionProductRelationService {

    @Resource
    private SeckillPromotionProductRelationMapper relationMapper;


    @Override
    public List<SeckillPromotionProduct> list(Long flashPromotionId, Long flashPromotionSessionId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return relationMapper.getSeckillGoodsList(flashPromotionId, flashPromotionSessionId);
    }
}
