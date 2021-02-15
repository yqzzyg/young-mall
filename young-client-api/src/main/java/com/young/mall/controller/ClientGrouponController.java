package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import com.young.db.entity.*;
import com.young.mall.common.ResBean;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.service.*;
import com.young.mall.util.OrderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/12/17 10:00
 */
@Api(tags = "ClientGrouponController")
@RestController
@RequestMapping("/client/groupon")
public class ClientGrouponController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private ClientGrouponService clientGrouponService;

    @Autowired
    private ClientOrderService clientOrderService;

    @Autowired
    private ClientOrderGoodsService clientOrderGoodsService;

    @Autowired
    private ClientGrouponRulesService clientGrouponRulesService;

    /**
     * 用户开团或入团情况
     *
     * @param showType 显示类型，如果是0，则是当前用户开的团购；否则，则是当前用户参加的团购
     * @return
     */
    @ApiOperation("用户开团或入团情况")
    @GetMapping("/my")
    public ResBean my(@RequestParam(name = "showType", defaultValue = "0") Integer showType) {

        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            return ResBean.unauthorized("请登录！");
        }
        Integer userId = userInfo.getYoungUser().getId();

        List<YoungGroupon> myGroupon;
        if (showType == 0) {
            myGroupon = clientGrouponService.queryMyGroupon(userId);
        } else {
            myGroupon = clientGrouponService.queryMyJoinGroupon(userId);
        }

        List<Map<String, Object>> grouponVoList = new ArrayList<>(myGroupon.size());

        //写在100个循环内等于你有100个引用对应了100个对象,
        //所以100个对象在一段时间内都是会占用内存，直到内存不足GC主动回收
        YoungOrder order;
        YoungGrouponRules rules;
        YoungUser creator;
        for (YoungGroupon groupon : myGroupon) {

            order = clientOrderService.findById(groupon.getOrderId());
            rules = clientGrouponRulesService.queryById(groupon.getRulesId());
            creator = clientUserService.findById(groupon.getCreatorUserId());

            Map<String, Object> grouponVo = new HashMap<>();
            // 填充团购信息
            grouponVo.put("id", groupon.getId());
            grouponVo.put("groupon", groupon);
            grouponVo.put("rules", rules);
            grouponVo.put("creator", creator.getNickname());

            int linkGrouponId;
            // 这是一个团购发起记录
            if (groupon.getGrouponId() == 0) {
                linkGrouponId = groupon.getId();
                grouponVo.put("isCreator", userId.equals(creator.getId()));
            } else {
                linkGrouponId = groupon.getGrouponId();
                grouponVo.put("isCreator", false);
            }

            Integer joinerCount = clientGrouponService.countGroupon(linkGrouponId);
            grouponVo.put("joinerCount", joinerCount + 1);
            // 填充订单信息
            grouponVo.put("orderId", order.getId());
            grouponVo.put("orderSn", order.getOrderSn());
            grouponVo.put("actualPrice", order.getActualPrice());
            grouponVo.put("orderStatusText", OrderUtil.orderStatusText(order));
            grouponVo.put("handleOption", OrderUtil.build(order));

            List<YoungOrderGoods> orderGoodsList = clientOrderGoodsService.queryByOid(order.getId());
            List<Map<String, Object>> orderGoodsVoList = new ArrayList<>(orderGoodsList.size());
            for (YoungOrderGoods orderGoods : orderGoodsList) {
                Map<String, Object> orderGoodsVo = new HashMap<>(16);
                orderGoodsVo.put("id", orderGoods.getId());
                orderGoodsVo.put("goodsName", orderGoods.getGoodsName());
                orderGoodsVo.put("number", orderGoods.getNumber());
                orderGoodsVo.put("picUrl", orderGoods.getPicUrl());
                orderGoodsVoList.add(orderGoodsVo);
            }
            grouponVo.put("goodsList", orderGoodsVoList);
            grouponVoList.add(grouponVo);
        }

        Map<String, Object> result = new HashMap<>(4);
        result.put("count", grouponVoList.size());
        result.put("data", grouponVoList);

        return ResBean.success(result);
    }
}
