package com.young.mall.controller;

import com.young.mall.common.ResBean;
import com.young.mall.service.AdminSystemService;
import com.young.mall.system.SystemConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * @Description: 配置管理
 * @Author: yqz
 * @CreateDate: 2020/11/20 16:36
 */
@Api(tags = "AdminConfigController", description = "配置管理")
@RestController
@RequestMapping("/admin/config")
public class AdminConfigController extends BaseController {


    @Autowired
    private AdminSystemService adminSystemService;

    @ApiOperation("商场配置详情")
    @PreAuthorize("@pms.hasPermission('admin:config:mall:list')")
    @GetMapping("/mall")
    public ResBean listMall() {
        Optional<Map<String, String>> optional = adminSystemService.listMall();
        if (!optional.isPresent()) {
            return ResBean.failed("查询失败");
        }
        return ResBean.success(optional.get());
    }

    @ApiOperation("商场配置详情更新")
    @PreAuthorize("@pms.hasPermission('admin:config:mall:updateConfigs')")
    @PostMapping("/mall")
    public ResBean update(@RequestBody Map<String, Object> map) {
        adminSystemService.updateConfig(map);
        return ResBean.success("成功");
    }

    @ApiOperation("运费配置详情")
    @PreAuthorize("@pms.hasPermission('admin:config:express:list')")
    @GetMapping("/express")
    public ResBean listExpress() {
        Optional<Map<String, String>> list = adminSystemService.listExpress();
        if (!list.isPresent()) {
            return ResBean.failed("查询失败");
        }
        return ResBean.success(list.get());
    }

    @ApiOperation("运费配置编辑")
    @PreAuthorize("@pms.hasPermission('admin:config:express:updateConfigs')")
    @PostMapping("/express")
    public ResBean updateExpress(@RequestBody Map<String, Object> map) {
        adminSystemService.updateConfig(map);
        SystemConfig.reloadConfig(SystemConfig.PRE_FIX);
        return ResBean.success("更新成功");
    }


    @ApiOperation("订单配置详情")
    @PreAuthorize("@pms.hasPermission('admin:config:order:list')")
    @GetMapping("/order")
    public ResBean lisOrder() {
        Optional<Map<String, String>> list = adminSystemService.listOrder();
        if (!list.isPresent()) {
            return ResBean.failed("查询失败");
        }
        return ResBean.success(list.get());
    }

    @ApiOperation("订单配置编辑")
    @PreAuthorize("@pms.hasPermission('admin:config:order:updateConfigs')")
    @PostMapping("/order")
    public ResBean updateOrder(@RequestBody Map<String, Object> map) {
        adminSystemService.updateConfig(map);
        return ResBean.success("更新成功");
    }

    @ApiOperation("小程序配置详情")
    @PreAuthorize("@pms.hasPermission('admin:config:wx:list')")
    @GetMapping("/wx")
    public ResBean listWx() {
        Optional<Map<String, String>> list = adminSystemService.listWx();
        if (!list.isPresent()) {
            return ResBean.failed("查询失败");
        }
        return ResBean.success(list.get());
    }

    @ApiOperation("小程序配置编辑")
    @PreAuthorize("@pms.hasPermission('admin:config:wx:updateConfigs')")
    @PostMapping("/wx")
    public ResBean updateWx(@RequestBody Map<String, Object> map) {
        adminSystemService.updateConfig(map);
        SystemConfig.reloadConfig(SystemConfig.PRE_FIX);
        return ResBean.success("更新成功");
    }
}
