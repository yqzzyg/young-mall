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
@RequestMapping("/admin/seckillProductRelation")
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

    @ApiOperation("修改关联相关信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResBean update(@PathVariable Long id, @RequestBody YoungSeckillPromotionProductRelation relation) {
        int count = relationService.update(id, relation);
        if (count > 0) {
            return ResBean.success(count);
        }
        return ResBean.failed();
    }

    @ApiOperation("获取管理商品促销信息")
    @GetMapping(value = "/{id}")
    public ResBean<YoungSeckillPromotionProductRelation> getItem(@PathVariable Long id) {
        YoungSeckillPromotionProductRelation relation = relationService.getItem(id);
        return ResBean.success(relation);
    }

    @ApiOperation("分页查询不同场次关联及商品信息")
    @GetMapping("/list")
    public ResBean list(@RequestParam(value = "flashPromotionId") Long flashPromotionId,
                        @RequestParam(value = "flashPromotionSessionId") Long flashPromotionSessionId,
                        @RequestParam(value = "size", defaultValue = "5") Integer size,
                        @RequestParam(value = "page", defaultValue = "1") Integer page) {

        List<SeckillPromotionProduct> promotionProducts = relationService.list(flashPromotionId, flashPromotionSessionId, size, page);

        return ResBean.success(CommonPage.restPage(promotionProducts));

    }
}
