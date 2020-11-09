package com.young.mall.controller;

import cn.hutool.json.JSONUtil;
import com.young.db.entity.YoungIssue;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.service.IssueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 通用问题
 * @Author: yqz
 * @CreateDate: 2020/11/8 21:49
 */
@Api(tags = "IssueController", description = "通用问题")
@RestController
@RequestMapping("/admin/issue")
public class IssueController extends BaseController {

    @Autowired
    private IssueService issueService;

    @ApiOperation("分页查询")
    @GetMapping("/list")
    public ResBean list(String question, @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size,
                        @RequestParam(defaultValue = "add_time") String sort,
                        @RequestParam(defaultValue = "desc") String order) {
        logger.info("question:{},page:{},size:{},sort:{},order:{}", question, page, size, sort, order);

        Optional<List<YoungIssue>> youngIssues = issueService.querySelective(question, page, size, sort, order);
        if (!youngIssues.isPresent()) {
            return ResBean.failed("查询失败");
        }

        CommonPage<YoungIssue> restPage = CommonPage.restPage(youngIssues.get());

        return ResBean.success(restPage);
    }

    @ApiOperation("添加通用问题")
    @PostMapping("/create")
    public ResBean create(@Valid @RequestBody YoungIssue issue) {
        logger.info("添加通用问题入参：{}", JSONUtil.toJsonStr(issue));

        Optional<Integer> optional = issueService.add(issue);
        if (!optional.isPresent()) {
            return ResBean.failed("添加失败");
        }
        return ResBean.success(issue);
    }

    @ApiOperation("读取问题详情")
    @GetMapping("/read")
    public ResBean read(@NotNull @RequestParam("id") Integer id) {
        Optional<YoungIssue> optional = issueService.findById(id);
        if (!optional.isPresent()) {
            return ResBean.failed("读取问题失败");
        }

        return ResBean.success(optional.get());
    }


    @ApiOperation("更新通用问题")
    @PostMapping("/update")
    public ResBean update(@Valid @RequestBody YoungIssue issue) {
        logger.info("更新通用问题入参：{}", JSONUtil.toJsonStr(issue));

        Optional<Integer> optional = issueService.update(issue);
        if (!optional.isPresent()) {
            return ResBean.failed("更新通用问题失败");
        }
        return ResBean.success(issue);
    }

    @ApiOperation("根据id删除")
    @PostMapping("/delete")
    public ResBean delete(@RequestBody YoungIssue issue) {

        logger.info("根据id删除问题入参：{}", JSONUtil.toJsonStr(issue));
        Integer id = issue.getId();
        if (id == null) {
            return ResBean.validateFailed("参数校验错误");
        }
        Optional<Integer> optional = issueService.delete(id);
        if (!optional.isPresent()) {
            return ResBean.failed("删除通用问题失败");
        }
        return ResBean.success(issue);
    }
}
