package com.young.mall.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungSeckillPromotionMapper;
import com.young.db.dao.YoungSeckillPromotionProductRelationMapper;
import com.young.db.dao.YoungSeckillPromotionSessionMapper;
import com.young.db.entity.*;
import com.young.db.mapper.SeckillPromotionProductRelationMapper;
import com.young.db.pojo.SeckillPromotionProduct;
import com.young.db.pojo.SeckillPromotionRelationProduct;
import com.young.mall.service.ClientSecondsToKillService;
import com.young.mall.service.MallGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description: 秒杀活动Service实现类
 * @Author: yqz
 * @CreateDate: 2021/2/3 12:07
 */
@Service
public class ClientSecondsToKillServiceImpl implements ClientSecondsToKillService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private YoungSeckillPromotionMapper seckillPromotionMapper;

    @Resource
    private YoungSeckillPromotionSessionMapper promotionSessionMapper;

    @Resource
    private YoungSeckillPromotionProductRelationMapper productRelationMapper;

    @Resource
    private SeckillPromotionProductRelationMapper relationMapper;

    @Autowired
    private MallGoodsService mallGoodsService;

    @Override
    public Map<String, Object> seckillPromotionCategory(Integer id) {

        YoungSeckillPromotionExample example = new YoungSeckillPromotionExample();
        YoungSeckillPromotionExample.Criteria or = example.or();
        or.andStatusEqualTo(1).andDeletedEqualTo(false);
        if (!ObjectUtil.isEmpty(id) && id != 0) {
            or.andIdEqualTo(Long.valueOf(id));
        }
        List<YoungSeckillPromotion> promotionList = seckillPromotionMapper.selectByExample(example);

        Map<String, Object> data = new HashMap<>(5);
        //用户显示页面title
        data.put("brotherCategory", promotionList);
        return data;
    }

    @Override
    public List<SeckillPromotionRelationProduct> list(Integer promotionId, Integer page, Integer size) {

        PageHelper.startPage(page, size);
        List<SeckillPromotionRelationProduct> relationProductList = relationMapper.getSessionListByPromotionId(Long.valueOf(promotionId));
        return relationProductList;
    }

    @Override
    public List<SeckillPromotionProduct> listByDate(Long promotionId, Long promotionSessionId, Integer pageSize, Integer pageNum) {

        PageHelper.startPage(pageNum, pageSize);
        List<SeckillPromotionProduct> seckillGoodsList = relationMapper.getSeckillGoodsList(promotionId, promotionSessionId);
        return seckillGoodsList;
    }

}
