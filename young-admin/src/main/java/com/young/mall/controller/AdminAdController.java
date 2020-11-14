package com.young.mall.controller;

import com.young.db.entity.YoungAd;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.service.AdvertisingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/14 18:36
 */
@Api(tags = "AdminAdController", description = "广告业务")
@RestController
@RequestMapping("/admin/ad")
public class AdminAdController extends BaseController {

    @Autowired
    private AdvertisingService advertisingService;

    @ApiOperation("分页展示广告")
    @GetMapping("/list")
    public ResBean list(String name, String content,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size,
                        @RequestParam(defaultValue = "add_time") String sort,
                        @RequestParam(defaultValue = "desc") String order) {
        logger.info("name:{},content:{},page:{},size:{},sort:{},order:{}", name, content, page, size, sort, order);

        Optional<List<YoungAd>> optional = advertisingService.list(name, content, page, size, sort, order);
        if (!optional.isPresent()) {
            return ResBean.failed("查询失败");
        }
        CommonPage<YoungAd> restPage = CommonPage.restPage(optional.get());
        return ResBean.success(restPage);
    }

    @ApiOperation("创建广告")
    @PostMapping("/create")
    public ResBean create(@Valid @RequestBody YoungAd youngAd) {
        logger.info("创建广告入参：{}", youngAd);
        Optional<Integer> optional = advertisingService.create(youngAd);
        if (!optional.isPresent()) {
            return ResBean.failed("创建失败");
        }
        return ResBean.success("创建成功");
    }

    @ApiOperation("广告详情")
    @GetMapping("/read")
    public ResBean read(@NotNull Integer id) {
        logger.info("广告详情入参：{}", id);
        Optional<YoungAd> optional = advertisingService.findById(id);
        if (!optional.isPresent()) {
            return ResBean.failed("读取失败");
        }
        return ResBean.success(optional.get());
    }

    @ApiOperation("更新广告")
    @PostMapping("/update")
    public ResBean update(@Valid @RequestBody YoungAd youngAd) {
        logger.info("更新广告入参:{}", youngAd);
        Optional<Integer> optional = advertisingService.update(youngAd);
        if (!optional.isPresent()) {
            return ResBean.failed("更新失败");
        }
        return ResBean.success("更新成功");
    }

    @ApiOperation("删除广告")
    @PostMapping("/delete")
    public ResBean delete(@RequestBody YoungAd youngAd) {
        logger.info("删除广告入参：{}", youngAd);
        Integer id = youngAd.getId();
        if (id == null) {
            return ResBean.validateFailed("参数错误");
        }
        Optional<Integer> optional = advertisingService.delete(id);
        if (!optional.isPresent() || optional.get() != 1) {
            return ResBean.failed("删除失败");
        }
        return ResBean.success(optional.get());
    }
}
