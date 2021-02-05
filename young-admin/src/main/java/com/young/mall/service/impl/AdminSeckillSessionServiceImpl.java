package com.young.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungSeckillPromotionSessionMapper;
import com.young.db.entity.YoungSeckillPromotionSession;
import com.young.db.mapper.SeckillPromotionProductRelationMapper;
import com.young.db.pojo.SeckillPromotionSessionDetail;
import com.young.mall.service.AdminSeckillSessionService;
import org.springframework.stereotype.Service;

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

    @Resource
    private YoungSeckillPromotionSessionMapper promotionSessionMapper;


    @Resource
    private SeckillPromotionProductRelationMapper relationMapper;


    @Override
    public int create(YoungSeckillPromotionSession promotionSession) {

        promotionSession.setCreateTime(LocalDateTime.now());

        return promotionSessionMapper.insertSelective(promotionSession);
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
