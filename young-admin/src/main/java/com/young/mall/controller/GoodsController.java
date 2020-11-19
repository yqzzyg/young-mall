package com.young.mall.controller;

import cn.hutool.json.JSONUtil;
import com.young.db.entity.YoungGoods;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.dto.GoodsArguments;
import com.young.mall.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description: 商品管理
 * @Author: yqz
 * @CreateDate: 2020/11/9 20:40
 */
@Api(tags = "GoodsController", description = "商品管理")
@RestController
@RequestMapping("/admin/goods")
public class GoodsController extends BaseController {

    @Autowired
    private GoodsService goodsService;


    @ApiOperation("分页查询商品")
    @PreAuthorize("@pms.hasPermission('admin:goods:list')")
    @GetMapping("/list")
    public ResBean list(String goodsSn, String name,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size,
                        @RequestParam(defaultValue = "add_time") String sort,
                        @RequestParam(defaultValue = "desc") String order) {
        logger.info("goodsSn:{},name:{},page:{},size:{},sort:{},order:{}", goodsSn, name, page, size, sort, order);

        Optional<List<YoungGoods>> youngGoods = goodsService.querySelective(goodsSn, name, page, size, sort, order);
        if (!youngGoods.isPresent()) {
            return ResBean.failed("分页查询商品失败");
        }

        CommonPage<YoungGoods> restPage = CommonPage.restPage(youngGoods.get());
        return ResBean.success(restPage);
    }

    @ApiOperation("商品分类和品牌")
    @PreAuthorize("@pms.hasPermission('admin:goods:catAndBrand')")
    @GetMapping("/catAndBrand")
    public ResBean catAndBrand() {
        Optional<Map> map = goodsService.catAndBrand();

        return ResBean.success(map.get());
    }

    @ApiOperation("商品详情")
    @PreAuthorize("@pms.hasPermission('admin:goods:detail')")
    @GetMapping("/detail")
    public ResBean detail(@NotNull @RequestParam("id") Integer id) {
        Optional<Map> detail = goodsService.detail(id);
        if (!detail.isPresent()) {
            return ResBean.failed("查询商品详情失败");
        }
        return ResBean.success(detail.get());
    }

    @ApiOperation("更新商品")
    @PreAuthorize("@pms.hasPermission('admin:goods:update')")
    @PostMapping("/update")
    public ResBean update(@RequestBody YoungGoods goods) {

        return null;
    }

    @ApiOperation("删除商品")
    @PreAuthorize("@pms.hasPermission('admin:goods:delete')")
    @PostMapping("/delete")
    public ResBean delete(@RequestBody YoungGoods youngGoods) {
        Optional<Integer> optional = goodsService.delete(youngGoods);
        if (!optional.isPresent()) {
            return ResBean.failed("删除失败");
        }
        return ResBean.success(optional.get());
    }

    @ApiOperation("创建商品")
    @PreAuthorize("@pms.hasPermission('admin:goods:create')")
    @PostMapping("/create")
    public ResBean create(@RequestBody GoodsArguments goodsArguments) {
        goodsService.create(goodsArguments);
        logger.info("创建商品入参：{}", JSONUtil.toJsonStr(goodsArguments));
        return ResBean.success("创建商品成功");
    }
}
