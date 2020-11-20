package com.young.mall.controller;

import com.young.db.entity.YoungRole;
import com.young.db.pojo.RolePermissionPojo;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.service.AdminPermissionsService;
import com.young.mall.service.AdminRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    private AdminPermissionsService adminPermissionsService;

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

    @ApiOperation("角色查询")
    @PreAuthorize("@pms.hasPermission('admin:role:list')")
    @GetMapping("/list")
    public ResBean list(String name,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer szie,
                        @RequestParam(defaultValue = "add_time") String sort,
                        @RequestParam(defaultValue = "desc") String order) {
        Optional<List<YoungRole>> optional = adminRoleService.list(name, page, szie, sort, order);
        if (!optional.isPresent()) {
            return ResBean.failed("查询失败");
        }
        CommonPage<YoungRole> restPage = CommonPage.restPage(optional.get());
        return ResBean.success(restPage);
    }

    @ApiOperation("添加角色")
    @PreAuthorize("@pms.hasPermission('admin:role:create')")
    @PostMapping("/create")
    public ResBean create(@Valid @RequestBody YoungRole youngRole) {

        if (adminRoleService.checkExist(youngRole.getName())) {
            return ResBean.failed("该角色名称已存在");
        }
        Optional<Integer> optional = adminRoleService.create(youngRole);
        if (!optional.isPresent() || optional.get() != 1) {
            return ResBean.failed("添加失败");
        }
        return ResBean.success("添加成功");
    }

    @ApiOperation("更新角色")
    @PreAuthorize("@pms.hasPermission('admin:role:update')")
    @PostMapping("/update")
    public ResBean update(@Valid @RequestBody YoungRole youngRole) {

        Optional<Integer> update = adminRoleService.updateById(youngRole);
        if (!update.isPresent() || update.get() != 1) {
            return ResBean.failed("更新失败");
        }
        return ResBean.success("更新成功");
    }

    @ApiOperation("删除角色")
    @PreAuthorize("@pms.hasPermission('admin:role:delete')")
    @PostMapping("/delete")
    public ResBean delete(@RequestBody YoungRole youngRole) {

        Integer id = youngRole.getId();
        if (id == null) {
            return ResBean.validateFailed("参数不能为空");
        }
        Optional<Integer> optional = adminRoleService.deleteById(id);
        if (!optional.isPresent() || optional.get() != 1) {
            return ResBean.failed("删除失败");
        }
        return ResBean.success("删除成功");
    }

    @ApiOperation("管理员的权限情况")
    @GetMapping("/permissions")
    public ResBean getPermissions(Integer roleId) {
        List<String> permissionsList = adminPermissionsService.getPermissionsList(roleId);
        List<RolePermissionPojo> listRolePermission = adminPermissionsService.listRolePermission();
        //list转Set
        Set<String> permissions = new HashSet<>(permissionsList);
        Map<String, Object> data = new HashMap<>(2);
        data.put("systemPermissions", listRolePermission);
        data.put("assignedPermissions", permissions);
        return ResBean.success(data);
    }
}
