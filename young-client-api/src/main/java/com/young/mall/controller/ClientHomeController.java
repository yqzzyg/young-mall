package com.young.mall.controller;

import com.young.db.entity.*;
import com.young.mall.common.ResBean;
import com.young.mall.service.*;
import com.young.mall.system.SystemConfig;
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

    @Autowired
    private ClientArticleService clientArticleService;

    @Autowired
    private ClientGoodsService clientGoodsService;

    @Autowired
    private ClientBrandService clientBrandService;

    @Autowired
    private ClientGrouponRulesService grouponRulesService;

    @Autowired
    private ClientTopicService topicService;

    @GetMapping("/index")
    public ResBean index(Integer userId) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //轮播图
        Callable<List<YoungAd>> bannerListCallable = () -> clientAdService.queryIndex();
        //首页九宫格
        Callable<List<YoungCategory>> channelListCallable = () -> clientCategoryService.queryLevelFirst();
        //优惠券
        Callable<List<YoungCoupon>> couponListCallable;
        if (userId == null) {
            couponListCallable = () -> clientCouponService.queryList(0, 3);
        } else {
            couponListCallable = () -> clientCouponService.queryAvailableList(userId, 0, 3);
        }
        Callable<List<YoungArticle>> articleListCallable = () -> clientArticleService.queryList(0, 5, "add_time", "desc");
        //首页新品首发模块
        Callable<List<YoungGoods>> newGoodsListCallable = () -> clientGoodsService.queryByNew(0, SystemConfig.getNewLimit());
        //首页人气推荐
        Callable<List<YoungGoods>> hotGoodsListCallable = () -> clientGoodsService.queryByHot(0, SystemConfig.getHotLimit());
        //首页品牌厂商直供
        Callable<List<YoungBrand>> brandListCallable = () -> clientBrandService.queryBrand(0, SystemConfig.getBrandLimit());
        //团购专区
        Callable<List<Map<String, Object>>> grouponListCallable = () -> grouponRulesService.queryList(0, 6);
        //活动专场
        Callable<List<YoungTopic>> topicListCallable = () -> topicService.queryList(0, SystemConfig.getTopicLimit());

        FutureTask<List<YoungAd>> bannerTask = new FutureTask<>(bannerListCallable);
        FutureTask<List<YoungCategory>> channelTask = new FutureTask<>(channelListCallable);
        FutureTask<List<YoungCoupon>> couponListTask = new FutureTask<>(couponListCallable);
        FutureTask<List<YoungArticle>> articleTask = new FutureTask<>(articleListCallable);
        FutureTask<List<YoungGoods>> newGoodsListTask = new FutureTask<>(newGoodsListCallable);
        FutureTask<List<YoungGoods>> hotGoodsListTask = new FutureTask<>(hotGoodsListCallable);
        FutureTask<List<YoungBrand>> brandListTask = new FutureTask<>(brandListCallable);

        FutureTask<List<Map<String, Object>>> grouponListTask = new FutureTask<>(grouponListCallable);
        FutureTask<List<YoungTopic>> topicListTask = new FutureTask<>(topicListCallable);

        executorService.submit(bannerTask);
        executorService.submit(channelTask);
        executorService.submit(couponListTask);
        executorService.submit(articleTask);
        executorService.submit(newGoodsListTask);
        executorService.submit(hotGoodsListTask);
        executorService.submit(brandListTask);
        executorService.submit(grouponListTask);
        executorService.submit(topicListTask);


        Map<String, Object> entity = new HashMap<>();
        try {
            entity.put("banner", bannerTask.get());
            entity.put("channel", channelTask.get());
            entity.put("couponList", couponListTask.get());
            entity.put("articles", articleTask.get());
            entity.put("newGoodsList", newGoodsListTask.get());
            entity.put("hotGoodsList", hotGoodsListTask.get());
            entity.put("brandList", brandListTask.get());
            entity.put("grouponList", grouponListTask.get());
            entity.put("topicList", topicListTask.get());

            //缓存数据
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        return ResBean.success(entity);
    }

}
