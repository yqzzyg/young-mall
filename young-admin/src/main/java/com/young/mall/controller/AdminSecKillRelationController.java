package com.young.mall.controller;

import com.young.db.entity.YoungSeckillPromotionProductRelation;
import com.young.db.pojo.SeckillPromotionProduct;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.service.AdminSeckillProductRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 秒杀和商品关系管理Controller
 * @Author: yqz
 * @CreateDate: 2021/2/1 15:14
 */
@Api(tags = "秒杀和商品关系管理Controller")
@RestController
@RequestMapping("/admin/SeckillProductRelation")
public class AdminSecKillRelationController {

    @Resource
    private AdminSeckillProductRelationService relationService;

    @ApiOperation("批量选择商品添加关联")
    @PostMapping(value = "/create")
    public ResBean create(@RequestBody List<YoungSeckillPromotionProductRelation> relationList) {
        int count = relationService.create(relationList);
        if (count > 0) {
            return ResBean.success(count);
        }
        return ResBean.failed();
    }

    @ApiOperation("删除关联")
    @PostMapping(value = "/delete/{id}")
    public ResBean delete(@PathVariable Long id) {
        int count = relationService.delete(id);
        if (count > 0) {
            return ResBean.success(count);
        }
        return ResBean.failed();
    }


    @ApiOperation("分页查询不同场次关联及商品信息")
    @GetMapping("/list")
    public ResBean list(@RequestParam(value = "flashPromotionId") Long flashPromotionId,
                        @RequestParam(value = "flashPromotionSessionId") Long flashPromotionSessionId,
                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        List<SeckillPromotionProduct> promotionProducts = relationService.list(flashPromotionId, flashPromotionSessionId, pageSize, pageNum);

        return ResBean.success(CommonPage.restPage(promotionProducts));

    }
}
