package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungSeckillPromotionMapper;
import com.young.db.entity.YoungSeckillPromotion;
import com.young.db.entity.YoungSeckillPromotionExample;
import com.young.mall.service.AdminSeckillService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description: 秒杀活动管理Service实现类
 * @Author: yqz
 * @CreateDate: 2021/2/1 16:18
 */
@Service
public class AdminSeckillServiceImpl implements AdminSeckillService {

    @Resource
    private YoungSeckillPromotionMapper seckillPromotionMapper;

    @Override
    public int create(YoungSeckillPromotion flashPromotion) {

        flashPromotion.setCreateTime(LocalDateTime.now());
        return seckillPromotionMapper.insertSelective(flashPromotion);
    }

    @Override
    public int delete(Long id) {
        YoungSeckillPromotion promotion = new YoungSeckillPromotion();
        promotion.setId(id);
        promotion.setDeleted(true);
        return seckillPromotionMapper.updateByPrimaryKeySelective(promotion);
    }

    @Override
    public int update(Long id, YoungSeckillPromotion flashPromotion) {
        flashPromotion.setId(id);
        return seckillPromotionMapper.updateByPrimaryKey(flashPromotion);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        YoungSeckillPromotion promotion = new YoungSeckillPromotion();
        promotion.setId(id);
        promotion.setStatus(status);
        return seckillPromotionMapper.updateByPrimaryKeySelective(promotion);
    }

    @Override
    public YoungSeckillPromotion getItem(Long id) {
        return seckillPromotionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<YoungSeckillPromotion> list(String keyword, Integer pageSize, Integer pageNum) {

        YoungSeckillPromotionExample example = new YoungSeckillPromotionExample();

        YoungSeckillPromotionExample.Criteria criteria = example.or();

        if (!StrUtil.isEmpty(keyword)) {
            criteria.andTitleLike("%" + keyword + "%");
        }
        criteria.andDeletedEqualTo(false);
        PageHelper.startPage(pageNum, pageSize);
        List<YoungSeckillPromotion> promotionList = seckillPromotionMapper.selectByExample(example);
        return promotionList;
    }
}
