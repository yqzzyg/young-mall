package com.young.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungSeckillPromotionProductRelationMapper;
import com.young.db.entity.YoungSeckillPromotionProductRelation;
import com.young.db.entity.YoungSeckillPromotionProductRelationExample;
import com.young.db.mapper.SeckillPromotionProductRelationMapper;
import com.young.db.pojo.SeckillPromotionProduct;
import com.young.mall.service.AdminSeckillProductRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 秒杀商品关联管理Service实现类
 * @Author: yqz
 * @CreateDate: 2021/2/1 15:23
 */
@Service
public class AdminSeckillProductRelationServiceImpl implements AdminSeckillProductRelationService {

    @Resource
    private SeckillPromotionProductRelationMapper relationMapper;

    @Resource
    private YoungSeckillPromotionProductRelationMapper youngSeckillPromotionProductRelationMapper;

    @Override
    public int create(List<YoungSeckillPromotionProductRelation> relationList) {

        for (YoungSeckillPromotionProductRelation relation : relationList) {
            youngSeckillPromotionProductRelationMapper.insertSelective(relation);
        }
        return relationList.size();
    }

    @Override
    public int delete(Long id) {
        return youngSeckillPromotionProductRelationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Long id, YoungSeckillPromotionProductRelation relation) {
        relation.setId(id);

        return youngSeckillPromotionProductRelationMapper.updateByPrimaryKey(relation);
    }

    @Override
    public YoungSeckillPromotionProductRelation getItem(Long id) {
        return youngSeckillPromotionProductRelationMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SeckillPromotionProduct> list(Long flashPromotionId, Long flashPromotionSessionId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return relationMapper.getSeckillGoodsList(flashPromotionId, flashPromotionSessionId);
    }


    @Override
    public long getCount(Long promotionId, Long promotionSessionId) {

        YoungSeckillPromotionProductRelationExample example = new YoungSeckillPromotionProductRelationExample();
        example.createCriteria()
                .andFlashPromotionIdEqualTo(promotionId)
                .andFlashPromotionSessionIdEqualTo(promotionSessionId);
        return youngSeckillPromotionProductRelationMapper.countByExample(example);
    }
}
