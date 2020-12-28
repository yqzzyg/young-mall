package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.young.db.entity.YoungOrderGoods;
import com.young.mall.common.ResBean;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.domain.vo.OrderCommentVo;
import com.young.mall.domain.vo.SubmitOrderVo;
import com.young.mall.service.ClientOrderService;
import com.young.mall.service.ClientUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/12/12 22:06
 */
@Api(tags = "ClientOrderController")
@RestController
@Validated
@RequestMapping("/client/order")
public class ClientOrderController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private ClientOrderService clientOrderService;

    @ApiOperation("订单列表")
    @GetMapping("/list")
    public ResBean list(@RequestParam(defaultValue = "0") Integer showType,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size) {

        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            logger.error("用户个人页面数据查询失败，未登录。");
            return ResBean.unauthorized("请登录！");
        }
        Map<String, Object> result = clientOrderService.list(userInfo.getYoungUser().getId(), showType, page, size);

        return ResBean.success(result);
    }


    @ApiOperation("订单详情")
    @GetMapping("/detail")
    public ResBean detail(@NotNull(message = "订单id不能为空") @RequestParam("orderId") Integer orderId) {
        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            logger.error("订单详情失败，未登录。");
            return ResBean.unauthorized("请登录！");
        }
        Map<String, Object> detail = clientOrderService.detail(userInfo.getYoungUser().getId(), orderId);
        return ResBean.success(detail);
    }

    @ApiOperation("物流跟踪")
    @GetMapping("/expressTrace")
    public ResBean expressTrace(@NotNull(message = "订单id不能为空") Integer orderId) {
        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            logger.error("物流跟踪失败，未登录。");
            return ResBean.unauthorized("请登录！");
        }

        Map<String, Object> expressTrace = clientOrderService.expressTrace(userInfo.getYoungUser().getId(), orderId);
        return ResBean.success(expressTrace);
    }

    @ApiOperation("查询待评价订单商品信息")
    @GetMapping("/goods")
    public ResBean goods(@NotNull(message = "订单id不能为空") Integer orderId,
                         @NotNull(message = "商品id不能为空") Integer goodsId) {

        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            logger.error("查询待评价订单商品信息失败，未登录。");
            return ResBean.unauthorized("请登录！");
        }
        Integer userId = userInfo.getYoungUser().getId();

        YoungOrderGoods orderGoods = clientOrderService.getGoodsByIds(userId, orderId, goodsId);

        return ResBean.success(orderGoods);
    }


    @ApiOperation("评价订单商品，在我的-待评价入口")
    @PostMapping("/comment")
    public ResBean comment(@RequestBody OrderCommentVo commentVo) {

        return clientOrderService.comment(commentVo);
    }

    @ApiOperation("提交订单")
    @PostMapping("/submit")
    public ResBean submit(@Valid @RequestBody SubmitOrderVo submitOrder) {

        logger.info("提交订单入参submit:{}", JSON.toJSONString(submitOrder));

        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            logger.error("提交订单失败，未登录。");
            return ResBean.unauthorized("请登录！");
        }

        ResBean submit = clientOrderService.submit(userInfo.getYoungUser().getId(), submitOrder);
        return submit;
    }

    /**
     * 付款订单的预支付会话标识
     *
     * @param map
     * @return
     */
    @PostMapping("/prepay")
    public ResBean prepay(@RequestBody Map<String, Integer> map, HttpServletRequest request) {
        logger.info("prepay入参：{}", JSONUtil.toJsonStr(map));
        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            logger.error("付款订单的预支付失败，未登录。");
            return ResBean.unauthorized("请登录！");
        }
        Integer userId = userInfo.getYoungUser().getId();

        Integer orderId = map.get("orderId");
        //整型默认为 0
        if (orderId == null || orderId == 0) {
            logger.error("用户：{},订单id不能为空", userId);
            return ResBean.failed("订单id不能为空");
        }
        return clientOrderService.prepay(userId, orderId, request);
    }
}
