package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungSeckillPromotionMapper;
import com.young.db.entity.YoungSeckillPromotion;
import com.young.db.entity.YoungSeckillPromotionExample;
import com.young.mall.service.AdminSeckillPromotionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 秒杀活动管理Service实现类
 * @Author: yqz
 * @CreateDate: 2021/2/1 16:18
 */
@Service
public class AdminSeckillPromotionServiceImpl implements AdminSeckillPromotionService {

    @Resource
    private YoungSeckillPromotionMapper seckillPromotionMapper;

    @Override
    public List<YoungSeckillPromotion> list(String keyword, Integer pageSize, Integer pageNum) {

        YoungSeckillPromotionExample example = new YoungSeckillPromotionExample();

        if (!StrUtil.isEmpty(keyword)) {
            example.createCriteria().andTitleLike("%" + keyword + "%");
        }
        PageHelper.startPage(pageNum, pageSize);
        List<YoungSeckillPromotion> promotionList = seckillPromotionMapper.selectByExample(example);
        return promotionList;
    }
}
