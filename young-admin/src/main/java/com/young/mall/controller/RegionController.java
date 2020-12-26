package com.young.mall.controller;

import com.young.db.entity.YoungRegion;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 行政区划
 * @Author: yqz
 * @CreateDate: 2020/11/1 14:16
 */
@Api(tags = "RegionController", description = "行政区划")
@RestController
@RequestMapping("/admin/region")
public class RegionController extends BaseController {

    @Autowired
    private RegionService regionService;

    @ApiOperation("查询行政区划list")
    @GetMapping("/list")
    public ResBean queryRegionList(String name, Integer code, @RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer size,
                                   @RequestParam(defaultValue = "id") String sort,
                                   @RequestParam(defaultValue = "desc") String order) {

        logger.error("name:{},code:{},page:{},size:{},sort:{},order:{}", name, code, page, size, sort, order);

        Optional<List<YoungRegion>> optionalList = regionService.queryRegionList(name, code, page, size, sort, order);


        if (!optionalList.isPresent()) {
            return ResBean.failed("行政区划列表查询失败");
        }

        CommonPage<YoungRegion> restPage = CommonPage.restPage(optionalList.get());
        return ResBean.success(restPage);
    }

}
