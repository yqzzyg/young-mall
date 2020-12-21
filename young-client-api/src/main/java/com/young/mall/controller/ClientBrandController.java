package com.young.mall.controller;

import com.young.db.entity.YoungBrand;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.service.ClientBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 品牌接口
 * @Author: yqz
 * @CreateDate: 2020/12/21 10:07
 */
@Api(tags = "ClientBrandController")
@RestController
@RequestMapping("/client/brand")
@Validated
public class ClientBrandController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientBrandService clientBrandService;


    @ApiOperation("品牌列表")
    @GetMapping("/list")
    public ResBean list(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size) {

        List<YoungBrand> brandList = clientBrandService.queryBrand(page, size);

        CommonPage<YoungBrand> restPage = CommonPage.restPage(brandList);
        return ResBean.success(restPage);
    }

    @ApiOperation("品牌详情")
    @GetMapping("/detail")
    public ResBean detail(@NotNull(message = "品牌id不能为空") @RequestParam("id") Integer id) {
        YoungBrand brand = clientBrandService.findById(id);

        Map<String, Object> data = new HashMap<>(8);
        data.put("brand", brand);

        return ResBean.success(data);
    }
}
