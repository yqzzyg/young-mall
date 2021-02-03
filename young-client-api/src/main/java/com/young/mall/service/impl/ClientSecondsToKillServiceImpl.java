package com.young.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungSeckillPromotionMapper;
import com.young.db.dao.YoungSeckillPromotionProductRelationMapper;
import com.young.db.dao.YoungSeckillPromotionSessionMapper;
import com.young.db.entity.*;
import com.young.db.mapper.SeckillPromotionProductRelationMapper;
import com.young.db.pojo.SeckillPromotionProduct;
import com.young.db.pojo.SeckillPromotionSessionDetail;
import com.young.mall.service.ClientSecondsToKillService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 秒杀活动Service实现类
 * @Author: yqz
 * @CreateDate: 2021/2/3 12:07
 */
@Service
public class ClientSecondsToKillServiceImpl implements ClientSecondsToKillService {

    @Resource
    private YoungSeckillPromotionMapper seckillPromotionMapper;

    @Resource
    private YoungSeckillPromotionSessionMapper promotionSessionMapper;

    @Resource
    private YoungSeckillPromotionProductRelationMapper productRelationMapper;

    @Resource
    private SeckillPromotionProductRelationMapper relationMapper;

    @Override
    public Map<String, Object> seckillPromotionCategory(Integer id) {

        YoungSeckillPromotionExample example = new YoungSeckillPromotionExample();

        if (!ObjectUtil.isEmpty(id)) {
            example.or().andIdEqualTo(Long.valueOf(id));
        }

        List<YoungSeckillPromotion> promotionList = seckillPromotionMapper.selectByExample(example);

        Map<String, Object> data = new HashMap<>(5);
        data.put("currentCategory", promotionList);
        //用户显示页面title
        data.put("parentCategory", promotionList);
        data.put("brotherCategory", promotionList);
        return data;
    }

    @Override
    public Object list(Integer promotionId, Integer page, Integer size) {

        List<SeckillPromotionSessionDetail> resultList = new ArrayList<>();

        YoungSeckillPromotionSessionExample example = new YoungSeckillPromotionSessionExample();
        example.createCriteria().andStatusEqualTo(1).andDeletedEqualTo(false);
        //查询所有秒杀活动
        List<YoungSeckillPromotionSession> list = promotionSessionMapper.selectByExample(example);


        //查询每个秒杀活动内的多个时间段
        for (YoungSeckillPromotionSession session : list) {
            SeckillPromotionSessionDetail detail = new SeckillPromotionSessionDetail();


            YoungSeckillPromotionProductRelationExample relationExample = new YoungSeckillPromotionProductRelationExample();
            relationExample.or()
                    .andFlashPromotionIdEqualTo(Long.valueOf(promotionId))
                    .andFlashPromotionSessionIdEqualTo(session.getId());
            //该活动对应的商品数量
            long relationCount = productRelationMapper.countByExample(relationExample);
            BeanUtil.copyProperties(session, detail);
            detail.setProductCount(relationCount);
            resultList.add(detail);
        }
        return resultList;
    }

    @Override
    public Object listByDate(Long promotionId, Long promotionSessionId, Integer pageSize, Integer pageNum) {

        PageHelper.startPage(pageNum, pageSize);
        List<SeckillPromotionProduct> seckillGoodsList = relationMapper.getSeckillGoodsList(promotionId, promotionSessionId);
        return seckillGoodsList;
    }
}
