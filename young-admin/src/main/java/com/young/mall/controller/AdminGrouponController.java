package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.young.db.entity.YoungGoods;
import com.young.db.entity.YoungGrouponRules;
import com.young.db.pojo.GroupOnListPojo;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.exception.Asserts;
import com.young.mall.service.MallGoodsService;
import com.young.mall.service.MallGroupRuleService;
import com.young.mall.service.MallGrouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Description: 团购业务
 * @Author: yqz
 * @CreateDate: 2020/11/16 13:45
 */
@Api(tags = "AdminGrouponController", description = "团购业务")
@RestController
@RequestMapping("/admin/groupon")
public class AdminGrouponController extends BaseController {

    @Autowired
    private MallGoodsService mallGoodsService;

    @Autowired
    private MallGrouponService mallGrouponService;

    @Autowired
    private MallGroupRuleService mallGroupRuleService;

    @ApiOperation("分页查询团购列表")
    @GetMapping("/list")
    public ResBean list(String goodsSn, @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size,
                        @RequestParam(defaultValue = "add_time") String sort,
                        @RequestParam(defaultValue = "desc") String order) {

        Optional<List<YoungGrouponRules>> optional = mallGroupRuleService.list(goodsSn, page, size, sort, order);
        if (!optional.isPresent()) {
            return ResBean.failed("查询失败");
        }
        CommonPage<YoungGrouponRules> restPage = CommonPage.restPage(optional.get());
        return ResBean.success(restPage);
    }

    @ApiOperation("更新团购")
    @PostMapping("/update")
    public ResBean update(@RequestBody YoungGrouponRules youngGrouponRules) {
        validate(youngGrouponRules);

        String goodsSn = youngGrouponRules.getGoodsSn();
        Optional<YoungGoods> youngGoodsOpt = mallGoodsService.findByGoodsSn(goodsSn);
        if (!youngGoodsOpt.isPresent()) {
            return ResBean.failed("该商品编号无对应商品");
        }
        youngGrouponRules.setGoodsName(youngGoodsOpt.get().getName());
        youngGrouponRules.setPicUrl(youngGoodsOpt.get().getPicUrl());

        Optional<Integer> optional = mallGroupRuleService.updateById(youngGrouponRules);
        if (!optional.isPresent() || optional.get() != 1) {
            return ResBean.failed("更新失败");
        }
        return ResBean.success("更新成功");
    }

    @ApiOperation("新增团购")
    @PostMapping("/create")
    public ResBean create(@RequestBody YoungGrouponRules youngGrouponRules) {
        validate(youngGrouponRules);

        String goodsSn = youngGrouponRules.getGoodsSn();
        YoungGoods youngGoods = mallGoodsService.findByGoodsSn(goodsSn).get();
        if (BeanUtil.isEmpty(youngGoods)) {
            return ResBean.failed("参数错误");
        }
        youngGrouponRules.setGoodsName(youngGoods.getName());
        youngGrouponRules.setPicUrl(youngGoods.getPicUrl());
        Optional<Integer> optional = mallGroupRuleService.createRoles(youngGrouponRules);
        if (!optional.isPresent()) {
            return ResBean.failed("新增失败");
        }
        return ResBean.success("新增成功");
    }

    @ApiOperation("删除团购")
    @PostMapping("/delete")
    public ResBean delete(@RequestBody YoungGrouponRules youngGrouponRules) {
        Integer id = youngGrouponRules.getId();
        if (id == null) {
            return ResBean.failed("参数错误");
        }
        Optional<Integer> optional = mallGroupRuleService.delete(id);
        if (!optional.isPresent()) {
            return ResBean.failed("删除失败");
        }
        return ResBean.success("删除成功");
    }

    @ApiOperation("团购活动")
    @GetMapping("/listRecord")
    public ResBean listGroupOn(String goodsSn,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10") Integer size,
                               @RequestParam(defaultValue = "add_time") String sort,
                               @RequestParam(defaultValue = "desc") String order) {

        Optional<List<GroupOnListPojo>> optional = mallGrouponService.list(goodsSn, page, size, sort, order);
        if (!optional.isPresent()) {
            return ResBean.failed("查询失败");
        }
        CommonPage<GroupOnListPojo> restPage = CommonPage.restPage(optional.get());
        return ResBean.success(restPage);
    }

    private void validate(YoungGrouponRules grouponRules) {
        String goodsSn = grouponRules.getGoodsSn();
        if (StrUtil.isEmpty(goodsSn)) {
            Asserts.fail("参数错误");
        }
        BigDecimal discount = grouponRules.getDiscount();
        if (discount == null) {
            Asserts.fail("参数错误");
        }
        Integer discountMember = grouponRules.getDiscountMember();
        if (discountMember == null) {
            Asserts.fail("参数错误");
        }
        LocalDateTime expireTime = grouponRules.getExpireTime();
        if (expireTime == null) {
            Asserts.fail("参数错误");
        }
    }

}
