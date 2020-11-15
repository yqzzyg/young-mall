package com.young.mall.controller;

import com.young.db.entity.YoungTopic;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.service.MallTopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 专题管理
 * @Author: yqz
 * @CreateDate: 2020/11/15 22:29
 */
@Api(tags = "AdminTopicController", description = "专题管理")
@RestController
@RequestMapping("/admin/topic")
public class AdminTopicController extends BaseController {

    @Autowired
    private MallTopicService mallTopicService;

    @ApiOperation("分页查询")
    @GetMapping("/list")
    public ResBean list(String title, String subtitle, @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size,
                        @RequestParam(defaultValue = "add_time") String sort,
                        @RequestParam(defaultValue = "desc") String order) {

        Optional<List<YoungTopic>> optional = mallTopicService.list(title, subtitle, page, size, sort, order);
        if (!optional.isPresent()) {
            return ResBean.failed("查询失败");
        }
        CommonPage<YoungTopic> restPage = CommonPage.restPage(optional.get());
        return ResBean.success(restPage);
    }

    @ApiOperation("添加专题")
    @PostMapping("/create")
    public ResBean create(@Valid @RequestBody YoungTopic youngTopic) {
        Optional<Integer> optional = mallTopicService.create(youngTopic);
        if (!optional.isPresent()) {
            return ResBean.failed("添加专题失败");
        }
        return ResBean.success(youngTopic);
    }

    @ApiOperation("专题详情")
    @GetMapping("/read")
    public ResBean read(@NotNull @RequestParam("id") Integer id) {

        Optional<YoungTopic> optional = mallTopicService.findById(id);
        if (!optional.isPresent()) {
            return ResBean.failed("查看专题详情失败");
        }
        return ResBean.success(optional.get());
    }

    @ApiOperation("更新专题详情")
    @PostMapping("/update")
    public ResBean update(@Valid @RequestBody YoungTopic youngTopic) {
        Optional<Integer> optional = mallTopicService.update(youngTopic);
        if (!optional.isPresent() && optional.get() != 1) {
            return ResBean.failed("更新专题详情失败");
        }
        return ResBean.success(youngTopic);
    }

    @ApiOperation("删除专题")
    @PostMapping("/delete")
    public ResBean delete(@RequestBody YoungTopic youngTopic) {
        Optional<Integer> optional = mallTopicService.delete(youngTopic.getId());
        if (!optional.isPresent() && optional.get() != 1) {
            return ResBean.failed("删除专题失败");
        }
        return ResBean.success("删除专题成功");
    }
}
