package com.young.mall.controller;

import com.google.common.base.Supplier;
import com.young.db.entity.YoungAd;
import com.young.db.entity.YoungCategory;
import com.young.mall.common.ResBean;
import com.young.mall.service.MallCategoryService;
import com.young.mall.service.MallCouponService;
import com.young.mall.service.WxAdService;
import io.swagger.annotations.Api;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/21 20:24
 */
@Api(tags = "WxHomeController", description = "微信首页")
@RestController
@RequestMapping("/wx/home")
public class WxHomeController {

    @Autowired
    private WxAdService wxAdService;

    @Autowired
    private MallCategoryService mallCategoryService;

    @Autowired
    private MallCouponService mallCouponService;

    @GetMapping("/index")
    public ResBean index(Integer userId) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Callable<List<YoungAd>> bannerListCallable = () -> wxAdService.queryIndex();
        Callable<List<YoungCategory>> channelListCallable = () -> mallCategoryService.queryLevelFirst().get();
        FutureTask<List<YoungAd>> bannerTask = new FutureTask<>(bannerListCallable);
        FutureTask<List<YoungCategory>> channelTask = new FutureTask<>(channelListCallable);
        executorService.submit(bannerTask);
        executorService.submit(channelTask);

        Map<String, Object> entity = new HashMap<>();
        try {
            entity.put("banner", bannerTask.get());
            entity.put("channel", channelTask.get());
            //缓存数据
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        return ResBean.success(entity);
    }

}
