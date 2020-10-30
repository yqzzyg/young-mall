package com.young.mall.controller;

import com.young.db.entity.YoungUser;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.service.UserService;
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
 * @Description: 操作用户，用户端，非后台用户
 * @Author: yqz
 * @CreateDate: 2020/10/30 14:28
 */
@Api(tags = "UserController", description = "操作用户，用户端，非后台用户")
@RestController
@RequestMapping("/admin/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @ApiOperation("获取用户端用户信息")
    @GetMapping("/list")
    public ResBean userList(String username, String mobile,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer size,
                            @RequestParam(defaultValue = "add_time") String sort,
                            @RequestParam(defaultValue = "desc") String order) {
        Optional<List<YoungUser>> listOptional = userService.queryUserList(username, mobile, page, size, sort, order);

        List<YoungUser> userList = listOptional.get();

        CommonPage<YoungUser> restPage = CommonPage.restPage(userList);
        return ResBean.success(restPage);
    }
}
