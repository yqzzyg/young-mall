package com.young.mall.controller;

import cn.hutool.json.JSONUtil;
import com.young.db.entity.YoungAddress;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.service.AddressService;
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
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/10/30 20:57
 */
@Api(tags = "AdminAddressController", description = "地址管理")
@RestController
@RequestMapping("/admin/address")
public class AdminAddressController extends BaseController {

    @Autowired
    private AddressService addressService;

    @ApiOperation("分页查询收获地址")
    @PreAuthorize("@pms.hasPermission('admin:address:list')")
    @GetMapping("/list")
    public ResBean queryAddress(Integer userId, String name,
                                @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer size,
                                @RequestParam(defaultValue = "add_time") String sort,
                                @RequestParam(defaultValue = "desc") String order) {
        logger.error("userId:{},name:{},page:{},size:{},sort:{},order:{}", userId, name, page, size, sort, order);
        Optional<List<YoungAddress>> listOptional = addressService.queryAddressList(userId, name, page, size, sort, order);

        logger.error("service查询数据库用户收获地址出参：{}", JSONUtil.toJsonStr(listOptional.get()));
        if (!listOptional.isPresent()) {
            return ResBean.failed("查询失败");
        }
        CommonPage<YoungAddress> restPage = CommonPage.restPage(listOptional.get());

        return ResBean.success(restPage);

    }
}
