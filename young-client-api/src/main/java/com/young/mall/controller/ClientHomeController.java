package com.young.mall.controller;

import com.young.db.entity.YoungAd;
import com.young.db.entity.YoungCategory;
import com.young.db.entity.YoungCoupon;
import com.young.mall.common.ResBean;
import com.young.mall.service.ClientCategoryService;
import com.young.mall.service.MallCategoryService;
import com.young.mall.service.ClientAdService;
import com.young.mall.service.ClientCouponService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/21 20:24
 */
@Api(tags = "WxHomeController", description = "微信首页")
@RestController
@RequestMapping("/wx/home")
public class ClientHomeController {

    @Autowired
    private ClientAdService clientAdService;

    @Autowired
    private ClientCategoryService clientCategoryService;

    @Autowired
    private ClientCouponService clientCouponService;

    @GetMapping("/index")
    public ResBean index(Integer userId) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Callable<List<YoungAd>> bannerListCallable = () -> clientAdService.queryIndex();
        Callable<List<YoungCategory>> channelListCallable = () -> clientCategoryService.queryLevelFirst();

        Callable<List<YoungCoupon>> couponListCallable;
        if (userId == null) {
            couponListCallable = () -> clientCouponService.queryList(0, 3);
        } else {
            couponListCallable = () -> clientCouponService.queryAvailableList(userId, 0, 3);
        }


        FutureTask<List<YoungAd>> bannerTask = new FutureTask<>(bannerListCallable);
        FutureTask<List<YoungCategory>> channelTask = new FutureTask<>(channelListCallable);
        FutureTask<List<YoungCoupon>> couponListTask = new FutureTask<>(couponListCallable);


        executorService.submit(bannerTask);
        executorService.submit(channelTask);
        executorService.submit(couponListTask);

        Map<String, Object> entity = new HashMap<>();
        try {
            entity.put("banner", bannerTask.get());
            entity.put("channel", channelTask.get());
            entity.put("couponList", couponListTask.get());
            //缓存数据
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        return ResBean.success(entity);
    }

}
