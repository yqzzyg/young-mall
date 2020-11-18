package com.young.mall.controller;

import com.young.mall.common.ResBean;
import com.young.mall.dto.StatVo;
import com.young.mall.service.AdminStatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description: 用户统计
 * @Author: yqz
 * @CreateDate: 2020/11/18 21:50
 */
@Api(tags = "AdminStatController", description = "用户统计")
@RestController
@RequestMapping("/admin/stat")
public class AdminStatController extends BaseController {

    @Autowired
    private AdminStatService adminStatService;

    @ApiOperation("用户统计")
    @GetMapping("/user")
    public ResBean statUser() {

        Optional<List<Map>> optional = adminStatService.statUser();
        if (!optional.isPresent()) {
            return ResBean.failed("查询失败");
        }
        String[] columns = new String[]{"day", "users"};
        StatVo statVo = new StatVo();
        statVo.setColumns(columns);
        statVo.setRows(optional.get());
        return ResBean.success(statVo);
    }

    @ApiOperation("订单统计")
    @GetMapping("/order")
    public ResBean statOrder() {
        Optional<List<Map>> optional = adminStatService.statOrder();
        if (!optional.isPresent()) {
            return ResBean.failed("查询失败");
        }
        String[] columns = new String[]{"day", "orders", "customers", "amount", "pcr"};
        StatVo statVo = new StatVo();
        statVo.setColumns(columns);
        statVo.setRows(optional.get());
        return ResBean.success(statVo);
    }

    @ApiOperation("商品统计")
    @GetMapping("/goods")
    public ResBean statGoods() {
        Optional<List<Map>> optional = adminStatService.statGoods();
        if (!optional.isPresent()) {
            return ResBean.failed("查询失败");
        }
        String[] columns = new String[]{"day", "orders", "products", "amount"};
        StatVo statVo = new StatVo();
        statVo.setColumns(columns);
        statVo.setRows(optional.get());
        return ResBean.success(statVo);
    }

}
