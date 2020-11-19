package com.young.mall.controller;

import com.github.pagehelper.PageInfo;
import com.young.db.entity.YoungFootprint;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.service.FootprintService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description: 用户足迹
 * @Author: yqz
 * @CreateDate: 2020/10/31 16:09
 */
@Api(tags = "FootprintController", description = "用户足迹")
@RestController
@RequestMapping("/admin/footprint")
public class FootprintController extends BaseController {


    @Autowired
    private FootprintService footprintService;

    @ApiOperation("用户足迹")
    @GetMapping("/list")
    @PreAuthorize("@pms.hasPermission('admin:footprint:list')")
    public ResBean list(String userId, String goodsId,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size,
                        @RequestParam(defaultValue = "add_time") String sort,
                        @RequestParam(defaultValue = "desc") String order) {
        logger.info("userId:{},goodsId:{},page:{},size:{},sort:{},order:{}", userId, goodsId, page, size, sort, order);

        Optional<List<YoungFootprint>> optionalList = footprintService.queryFootPrint(userId, goodsId, page, size, sort, order);
        if (!optionalList.isPresent()) {
            return ResBean.failed("查询失败");
        }
        CommonPage<YoungFootprint> restPage = CommonPage.restPage(optionalList.get());
        return ResBean.success(restPage);
    }
}
