package com.young.mall.controller;

import cn.hutool.json.JSONUtil;
import com.young.db.entity.YoungGoods;
import com.young.db.entity.YoungGoodsProduct;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.dto.GoodsArguments;
import com.young.mall.exception.Asserts;
import com.young.mall.service.GoodsService;
import com.young.mall.service.YoungBrandService;
import com.young.mall.service.YoungCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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

    @Autowired
    private YoungBrandService youngBrandService;

    @Autowired
    private YoungCategoryService youngCategoryService;

    @ApiOperation("分页查询商品")
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
    @GetMapping("/catAndBrand")
    public ResBean catAndBrand() {
        Optional<Map> map = goodsService.catAndBrand();

        return ResBean.success(map.get());
    }

    @ApiOperation("商品详情")
    @GetMapping("/detail")
    public ResBean detail(@NotNull @RequestParam("id") Integer id) {
        Optional<Map> detail = goodsService.detail(id);
        if (!detail.isPresent()) {
            return ResBean.failed("查询商品详情失败");
        }
        return ResBean.success(detail.get());
    }

    @ApiOperation("更新商品")
    @PostMapping("/update")
    public ResBean update(@RequestBody YoungGoods goods) {

        return null;
    }

    @ApiOperation("删除商品")
    @PostMapping("/delete")
    public ResBean delete(@Valid @RequestBody YoungGoods youngGoods) {
        Optional<Integer> optional = goodsService.delete(youngGoods);
        if (!optional.isPresent()) {
            return ResBean.failed("删除失败");
        }
        return ResBean.success(optional.get());
    }

    @ApiOperation("创建商品")
    @PostMapping("/create")
    public ResBean create(@Valid @RequestBody GoodsArguments goodsArguments) {

        //校验
        validate(goodsArguments);
        logger.info("创建商品入参：{}", JSONUtil.toJsonStr(goodsArguments));
        return ResBean.success(goodsArguments);
    }


    private void validate(GoodsArguments goodsArguments) {
        YoungGoods goods = goodsArguments.getGoods();

        // 分类可以不设置，如果设置则需要验证分类存在
        Integer categoryId = goods.getCategoryId();
        if (categoryId != null && categoryId != 0) {
            if (!youngCategoryService.findById(categoryId).isPresent()) {
                Asserts.fail("商品分类不存在");
            }
        }

        // 品牌商可以不设置，如果设置则需要验证品牌商存在
        Integer brandId = goods.getBrandId();
        if (brandId != null && brandId != 0) {
            if (!youngBrandService.findById(brandId).isPresent()) {
                Asserts.fail("品牌商不存在");
            }
        }
        List<YoungGoodsProduct> products = goodsArguments.getProducts();
        for (YoungGoodsProduct product : products) {
            Integer number = product.getNumber();
            if (number == null || number < 0) {
                Asserts.fail("商品库存数量不能为空");
            }

            BigDecimal price = product.getPrice();
            if (price == null) {
                Asserts.fail("商品价格不能为空");
            }

            String[] productSpecifications = product.getSpecifications();
            if (productSpecifications.length == 0) {
                Asserts.fail("商品规格值不能为空");
            }
        }
    }
}
