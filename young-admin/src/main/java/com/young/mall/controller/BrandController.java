package com.young.mall.controller;

import com.young.db.entity.YoungBrand;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 品牌制造商
 * @Author: yqz
 * @CreateDate: 2020/11/1 14:41
 */
@Api(tags = "BrandController", description = "品牌")
@RestController
@RequestMapping("/admin/brand")
public class BrandController extends BaseController {

    @Autowired
    private BrandService brandService;

    @ApiOperation("查询品牌list")
    @PreAuthorize("@pms.hasPermission('admin:brand:list')")
    @GetMapping("/list")
    public ResBean queryBrandList(String id, String name,
                                  @RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer size,
                                  @RequestParam(defaultValue = "add_time") String sort,
                                  @RequestParam(defaultValue = "desc") String order) {
        logger.error("id:{},name:{},page:{},size:{},sort:{},order:{}", id, name, page, size, sort, order);

        Optional<List<YoungBrand>> listOptional = brandService.queryBrandList(id, name, page, size, sort, order);
        if (!listOptional.isPresent()) {
            return ResBean.failed("查询品牌失败");
        }

        CommonPage<YoungBrand> restPage = CommonPage.restPage(listOptional.get());
        return ResBean.success(restPage);
    }
}
