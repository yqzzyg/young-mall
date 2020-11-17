package com.young.mall.controller;

import com.young.db.entity.YoungRole;
import com.young.mall.common.ResBean;
import com.young.mall.service.AdminRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @Description: 角色管理
 * @Author: yqz
 * @CreateDate: 2020/11/17 15:35
 */
@Api(tags = "AdminRoleController", description = "角色管理")
@RestController
@RequestMapping("/admin/role")
public class AdminRoleController extends BaseController {

    @Autowired
    private AdminRoleService adminRoleService;

    @ApiOperation("查询所有角色")
    @GetMapping("/options")
    public ResBean options() {

        Optional<List<YoungRole>> optional = adminRoleService.listAll();
        if (!optional.isPresent()) {
            return ResBean.failed("查询失败");
        }
        List<YoungRole> roleList = optional.get();
        List<Map<String, Object>> options = new ArrayList<>(roleList.size());
        for (YoungRole role : roleList) {
            Map<String, Object> option = new HashMap<>(2);
            option.put("value", role.getId());
            option.put("label", role.getName());
            options.add(option);
        }
        return ResBean.success(options);
    }
}
