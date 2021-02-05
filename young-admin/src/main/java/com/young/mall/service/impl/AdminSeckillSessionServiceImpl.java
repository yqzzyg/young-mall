package com.young.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungSeckillPromotionSessionMapper;
import com.young.db.entity.YoungSeckillPromotion;
import com.young.db.entity.YoungSeckillPromotionSession;
import com.young.db.entity.YoungSeckillPromotionSessionExample;
import com.young.db.pojo.SeckillPromotionSessionDetail;
import com.young.mall.common.CommonPage;
import com.young.mall.service.AdminSeckillProductRelationService;
import com.young.mall.service.AdminSeckillSessionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private AdminSeckillProductRelationService relationService;

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

        List<SeckillPromotionSessionDetail> result = new ArrayList<>();

        YoungSeckillPromotionSessionExample example = new YoungSeckillPromotionSessionExample();
        example.createCriteria().andStatusEqualTo(1).andDeletedEqualTo(false);

        PageHelper.startPage(page,size);
        List<YoungSeckillPromotionSession> list = promotionSessionMapper.selectByExample(example);

        CommonPage<YoungSeckillPromotionSession> restPage = CommonPage.restPage(list);
        for (YoungSeckillPromotionSession promotionSession : list) {
            SeckillPromotionSessionDetail detail = new SeckillPromotionSessionDetail();
            BeanUtil.copyProperties(promotionSession, detail);

            long count = relationService.getCount(promotionId, promotionSession.getId());
            detail.setProductCount(count);
            result.add(detail);
        }
        return result;
    }
}
