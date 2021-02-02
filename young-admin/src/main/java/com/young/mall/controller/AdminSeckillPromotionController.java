package com.young.mall.controller;

import com.young.db.entity.YoungSeckillPromotion;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.service.AdminSeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 秒杀活动管理
 * @Author: yqz
 * @CreateDate: 2021/2/1 16:16
 */
@Controller
@Api(tags = "秒杀活动管理AdminSeckillPromotion")
@RestController
@RequestMapping("/admin/seckill")
public class AdminSeckillPromotionController {

    @Resource
    private AdminSeckillService promotionService;

    @ApiOperation("添加活动")
    @PostMapping(value = "/create")
    public ResBean create(@RequestBody YoungSeckillPromotion flashPromotion) {
        int count = promotionService.create(flashPromotion);
        if (count > 0) {
            return ResBean.success(count);
        }
        return ResBean.failed();
    }

    @ApiOperation("删除活动信息")
    @PostMapping(value = "/delete/{id}")
    public ResBean delete(@PathVariable Long id) {
        int count = promotionService.delete(id);
        if (count > 0) {
            return ResBean.success(count);
        }
        return ResBean.failed();
    }

    @ApiOperation("编辑活动信息")
    @PostMapping(value = "/update/{id}")
    public ResBean update(@PathVariable Long id, @RequestBody YoungSeckillPromotion flashPromotion) {
        int count = promotionService.update(id, flashPromotion);
        if (count > 0) {
            return ResBean.success(count);
        }
        return ResBean.failed();
    }

    @ApiOperation("修改上下线状态")
    @PostMapping(value = "/update/status/{id}")
    public ResBean update(@PathVariable Long id, Integer status) {
        int count = promotionService.updateStatus(id, status);
        if (count > 0) {
            return ResBean.success(count);
        }
        return ResBean.failed();
    }

    @ApiOperation("获取活动详情")
    @GetMapping(value = "/{id}")
    public ResBean<YoungSeckillPromotion> getItem(@PathVariable Long id) {
        YoungSeckillPromotion flashPromotion = promotionService.getItem(id);
        return ResBean.success(flashPromotion);
    }

    @ApiOperation("根据活动名称分页查询")
    @GetMapping(value = "/list")
    public ResBean<CommonPage<YoungSeckillPromotion>> getItem(@RequestParam(value = "keyword", required = false) String keyword,
                                                              @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<YoungSeckillPromotion> flashPromotionList = promotionService.list(keyword, pageSize, pageNum);

        return ResBean.success(CommonPage.restPage(flashPromotionList));
    }
}
