package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.young.db.entity.YoungAdmin;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.domain.dto.AdminUserDto;
import com.young.mall.domain.enums.AdminResponseCode;
import com.young.mall.exception.Asserts;
import com.young.mall.service.AdminUserService;
import com.young.mall.util.RegexUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 管理员管理
 * @Author: yqz
 * @CreateDate: 2020/11/17 15:07
 */
@Api(tags = "AdminUserController", description = "管理员管理")
@RestController
@RequestMapping("/admin/admin")
public class AdminUserController extends BaseController {

    @Autowired
    private AdminUserService adminUserService;

    @ApiOperation("分页查询")
    @PreAuthorize("@pms.hasPermission('admin:user:list')")
    @GetMapping("/list")
    public ResBean list(String username,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size,
                        @RequestParam(defaultValue = "add_time") String sort,
                        @RequestParam(defaultValue = "desc") String order) {

        Optional<List<YoungAdmin>> optional = adminUserService.list(username, page, size, sort, order);
        if (!optional.isPresent()) {
            return ResBean.failed("查询失败");
        }
        List<YoungAdmin> youngAdminList = optional.get();

        List<AdminUserDto> adminUserList = new ArrayList<>();

        for (YoungAdmin youngAdmin : youngAdminList) {
            AdminUserDto adminUserDto = new AdminUserDto();
            BeanUtil.copyProperties(youngAdmin, adminUserDto);
            adminUserList.add(adminUserDto);
        }
        CommonPage<AdminUserDto> restPage = CommonPage.restPage(adminUserList);
        return ResBean.success(restPage);
    }

    @ApiOperation("创建管理员用户")
    @PreAuthorize("@pms.hasPermission('admin:user:create')")
    @PostMapping("/create")
    public ResBean create(@RequestBody YoungAdmin youngAdmin) {
        validate(youngAdmin);
        String username = youngAdmin.getUsername();
        List<YoungAdmin> adminList = adminUserService.findAdminByName(username).get();
        if (adminList.size() > 0) {
            Asserts.fail(AdminResponseCode.ADMIN_NAME_EXIST);
        }
        String password = youngAdmin.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode(password);
        youngAdmin.setPassword(encodePassword);
        Optional<Integer> optional = adminUserService.create(youngAdmin);
        if (!optional.isPresent() || optional.get() != 1) {
            return ResBean.failed("新增失败");
        }
        return ResBean.failed("新增成功");
    }

    private void validate(YoungAdmin admin) {
        String name = admin.getUsername();
        if (StrUtil.isEmpty(name)) {
            Asserts.fail("用户名不能为空");
        }
        if (!RegexUtil.isUsername(name)) {
            logger.error("校验错误：{}", AdminResponseCode.ADMIN_INVALID_NAME.desc());
            Asserts.fail(AdminResponseCode.ADMIN_INVALID_NAME);

        }
        String password = admin.getPassword();
        if (StringUtils.isEmpty(password) || password.length() < 6) {
            logger.error("校验错误：{}", AdminResponseCode.ADMIN_INVALID_PASSWORD.desc());
            Asserts.fail(AdminResponseCode.ADMIN_INVALID_PASSWORD);
        }
    }

    @ApiOperation("管理员详情")
    @PreAuthorize("@pms.hasPermission('admin:user:read')")
    @GetMapping("/read")
    public ResBean read(@NotNull Integer id) {
        Optional<YoungAdmin> optional = adminUserService.findById(id);
        if (!optional.isPresent()) {
            return ResBean.failed("查询失败");
        }
        return ResBean.success(optional.get());
    }

    @ApiOperation("更新")
    @PreAuthorize("@pms.hasPermission('admin:user:update')")
    @PostMapping("/update")
    public ResBean update(@RequestBody YoungAdmin youngAdmin) {
        validate(youngAdmin);

        Integer id = youngAdmin.getId();
        if (id == null) {
            return ResBean.failed("参数错误");
        }
        String password = youngAdmin.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        youngAdmin.setPassword(encodedPassword);

        Optional<Integer> update = adminUserService.updateById(youngAdmin);
        if (!update.isPresent() || update.get() != 1) {
            return ResBean.failed("更新失败");
        }
        return ResBean.success("更新成功");
    }

    @ApiOperation("删除后台用户")
    @PreAuthorize("@pms.hasPermission('admin:user:delete')")
    @PostMapping("/delete")
    public ResBean delete(@RequestBody YoungAdmin youngAdmin) {
        Integer adminId = youngAdmin.getId();
        if (adminId == null) {
            return ResBean.failed("参数错误");
        }
        Optional<Integer> optional = adminUserService.delete(adminId);
        if (!optional.isPresent() || optional.get() != 1) {
            return ResBean.failed("删除失败");
        }
        return ResBean.success("删除成功");
    }

}
