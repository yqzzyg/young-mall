package com.young.mall.controller;

import com.young.db.entity.YoungOrder;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.service.AuthService;
import com.young.mall.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description: 订单controller
 * @Author: yqz
 * @CreateDate: 2020/11/2 10:21
 */
@Api(tags = "OrderController", description = "订单管理")
@RestController
@RequestMapping("/admin/order")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;


    @ApiOperation("订单列表")
    @GetMapping("/list")
    public ResBean list(Integer userId, String orderSn,
                        @RequestParam(required = false) List<Short> orderStatusArray,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size,
                        @RequestParam(defaultValue = "add_time") String sort,
                        @RequestParam(defaultValue = "desc") String order) {
        logger.info("userId:{},orderSn:{},orderStatusArray:{},page:{},size:{},sort:{},order:{}", userId, orderSn, orderStatusArray, page, size, sort, order);

        Optional<List<YoungOrder>> list = orderService.list(userId, orderSn, orderStatusArray, page, size, sort, order);
        if (!list.isPresent()) {
            return ResBean.failed("查询失败");
        }
        CommonPage<YoungOrder> restPage = CommonPage.restPage(list.get());
        return ResBean.success(restPage);
    }


    @ApiOperation("订单详情")
    @GetMapping("/detail")
    public ResBean detail(@NotNull @RequestParam("id") Integer id) {
        logger.info("订单详情接口入参：{}", id);

        Optional<Map<String, Object>> detail = orderService.detail(id);
        if (!detail.isPresent()) {
            return ResBean.failed("订单详情查询失败");
        }

        return ResBean.success(detail.get());
    }
}
