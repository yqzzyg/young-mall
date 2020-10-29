package com.young.mall.controller;

import com.young.mall.common.ResBean;
import com.young.mall.service.GoodsProductService;
import com.young.mall.service.GoodsService;
import com.young.mall.service.OrderService;
import com.young.mall.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 后台主页数据 Controller
 * @Author: yqz
 * @CreateDate: 2020/10/29 15:37
 */
@Api(tags = "AdminAuthController", description = "后台用户管理")
@RestController
@RequestMapping("/admin")
public class AdminDashbordController extends BaseController {


    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsProductService productService;
    @Autowired
    private OrderService orderService;

    @ApiOperation("获取主页数据")
    @GetMapping("/dashboard")
    public ResBean info() {

        Integer userTotal = userService.count().get();
        Integer goodsTotal = goodsService.count().get();
        Integer productTotal = productService.count().get();
        Integer orderTotal = orderService.count().get();

        Map<String, Integer> map = new HashMap<>();
        map.put("userTotal", userTotal);
        map.put("goodsTotal", goodsTotal);
        map.put("productTotal", productTotal);
        map.put("orderTotal", orderTotal);
        return ResBean.success(map);
    }
}
