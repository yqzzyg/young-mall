package com.young.mall.controller;

import com.young.db.entity.YoungIssue;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.service.IssueService;
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
}
