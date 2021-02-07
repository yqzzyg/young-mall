package com.young.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungSeckillPromotionProductRelationMapper;
import com.young.db.dao.YoungSeckillPromotionSessionMapper;
import com.young.db.entity.YoungSeckillPromotionProductRelation;
import com.young.db.entity.YoungSeckillPromotionProductRelationExample;
import com.young.db.entity.YoungSeckillPromotionSession;
import com.young.db.mapper.SeckillPromotionProductRelationMapper;
import com.young.db.pojo.SeckillPromotionSessionDetail;
import com.young.mall.exception.Asserts;
import com.young.mall.service.AdminSeckillSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description: 限时购场次管理Service实现类
 * @Author: yqz
 * @CreateDate: 2021/2/1 21:45
 */
@Service
public class AdminSeckillSessionServiceImpl implements AdminSeckillSessionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private YoungSeckillPromotionSessionMapper promotionSessionMapper;

    @Resource
    private YoungSeckillPromotionProductRelationMapper productRelationMapper;

    @Resource
    private SeckillPromotionProductRelationMapper relationMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int create(Long flashPromotionId, YoungSeckillPromotionSession promotionSession) {

        promotionSession.setCreateTime(LocalDateTime.now());
        //在该秒杀分类下添加秒杀场次
        int count = promotionSessionMapper.insertSelective(promotionSession);

        YoungSeckillPromotionProductRelation relation = new YoungSeckillPromotionProductRelation();
        relation.setFlashPromotionId(flashPromotionId);
        relation.setFlashPromotionSessionId(promotionSession.getId());
        //在秒杀分类--场次关系表中添加关联
        productRelationMapper.insertSelective(relation);
        return count;
    }

    @Override
    public int update(Long id, YoungSeckillPromotionSession promotionSession) {
        promotionSession.setId(id);
        return promotionSessionMapper.updateByPrimaryKey(promotionSession);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        YoungSeckillPromotionSession promotionSession = new YoungSeckillPromotionSession();
        promotionSession.setId(id);
        promotionSession.setStatus(status);
        return promotionSessionMapper.updateByPrimaryKeySelective(promotionSession);
    }

    @Override
    public int delete(Long id) {
        YoungSeckillPromotionSession promotionSession = new YoungSeckillPromotionSession();
        promotionSession.setId(id);
        promotionSession.setDeleted(true);

        return promotionSessionMapper.updateByPrimaryKeySelective(promotionSession);
    }

    @Override
    public YoungSeckillPromotionSession getItem(Long id) {
        return promotionSessionMapper.selectByPrimaryKey(id);
    }


    @Override
    public List<SeckillPromotionSessionDetail> selectList(Long promotionId, Integer size,
                                                          Integer page) {

        PageHelper.startPage(page, size);
        List<SeckillPromotionSessionDetail> sessionDetailList = relationMapper.getSessionListWithCount(promotionId);
        return sessionDetailList;
    }
}
