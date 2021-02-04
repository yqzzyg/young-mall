package com.young.mall.controller;

import com.young.db.pojo.SeckillPromotionProduct;
import com.young.db.pojo.SeckillPromotionSessionDetail;
import com.young.mall.common.ResBean;
import com.young.mall.service.ClientSecondsToKillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 秒杀抢购（仿京东秒杀页面）
 * 首页---> 进入秒杀列表页---->顶部展示不同类别秒杀table--->不同类别下展示该类别下的不同时间段的秒杀
 * @Author: yqz
 * @CreateDate: 2021/2/1 12:21
 */
@Api(tags = "秒杀抢购ClientSecondsToKillController")
@RestController
@RequestMapping("/client/seckill")
public class ClientSecondsToKillController {


    @Resource
    private ClientSecondsToKillService killService;


    @ApiOperation("秒杀商品类别")
    @GetMapping("/category")
    public ResBean<Map<String, Object>> seckillPromotionCategory(Integer id) {

        Map<String, Object> category = killService.seckillPromotionCategory();
        return ResBean.success(category);
    }


    /**
     * 根据category查询秒杀时间段列表
     *
     * @param promotionId 秒杀活动id
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("根据category查询秒杀时间段列表")
    @GetMapping("/list")
    public ResBean list(
            @RequestParam(value = "promotionId") Integer promotionId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        List<SeckillPromotionSessionDetail> list = killService.list(promotionId, page, size);
        Map<String, Object> data = new HashMap<>(2);

        data.put("goodsList", list);
        return ResBean.success(data);
    }

    /**
     * 分页查询不同场次关联及商品信息
     *
     * @param promotionId        秒杀活动id
     * @param promotionSessionId 秒杀场次id
     * @param pageSize
     * @param pageNum
     * @return
     */
    @ApiOperation("分页查询不同场次关联及商品信息")
    @GetMapping("/listByDate")
    public ResBean<List<SeckillPromotionProduct>> listByDate(@RequestParam(value = "promotionId") Long promotionId,
                                                             @RequestParam(value = "promotionSessionId") Long promotionSessionId,
                                                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        List<SeckillPromotionProduct> listByDate = killService.listByDate(promotionId, promotionSessionId, pageSize, pageNum);
        return ResBean.success(listByDate);
    }

    /**
     * @param id 商品id
     * @return
     */
    @ApiOperation("秒杀商品详情")
    @GetMapping("/details")
    public ResBean details(@RequestParam("id") Integer id) {

        return ResBean.success(null);
    }

    @ApiOperation("获取系统时间")
    @GetMapping("/now")
    public ResBean now() {
        return ResBean.success(System.currentTimeMillis());
    }
}
