package com.young.mall.controller;

import cn.hutool.json.JSONUtil;
import com.young.db.entity.YoungKeyword;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.service.KeyWordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 关键词
 * @Author: yqz
 * @CreateDate: 2020/11/9 19:49
 */
@Api(tags = "KeyWordController", description = "关键词")
@RestController
@RequestMapping("/admin/keyword")
public class KeyWordController extends BaseController {


    @Autowired
    private KeyWordService keyWordService;

    @ApiOperation("分页查询")
    @PreAuthorize("@pms.hasPermission('admin:keyword:list')")
    @GetMapping("/list")
    public ResBean list(String keyword, String url,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size,
                        @RequestParam(defaultValue = "add_time") String sort,
                        @RequestParam(defaultValue = "desc") String order) {
        logger.info("keyword:{},url:{},page:{},size:{},sort:{},order:{}", keyword, url, page, size, sort, order);

        Optional<List<YoungKeyword>> youngKeywords = keyWordService.querySelective(keyword, url, page, size, sort, order);

        if (!youngKeywords.isPresent()) {
            return ResBean.failed("查询失败");
        }
        CommonPage<YoungKeyword> restPage = CommonPage.restPage(youngKeywords.get());
        return ResBean.success(restPage);
    }

    @ApiOperation("创建关键词")
    @PreAuthorize("@pms.hasPermission('admin:keyword:create')")
    @PostMapping("/create")
    public ResBean create(@Valid @RequestBody YoungKeyword keyword) {
        logger.info("创建关键词 入参：{}", JSONUtil.toJsonStr(keyword));

        Optional<Integer> optional = keyWordService.create(keyword);
        if (!optional.isPresent()) {
            return ResBean.failed("创建关键词失败");
        }
        return ResBean.success(keyword);
    }


    @ApiOperation("读取关键词详情")
    @PreAuthorize("@pms.hasPermission('admin:keyword:read')")
    @GetMapping("/read")
    public ResBean read(@NotNull Integer id) {
        logger.info("读取关键词接口入参：{}", id);
        Optional<YoungKeyword> optional = keyWordService.findById(id);
        if (!optional.isPresent()) {
            return ResBean.failed("读取关键词详情失败");
        }
        return ResBean.success(optional.get());
    }

    @ApiOperation("更新关键词")
    @PreAuthorize("@pms.hasPermission('admin:keyword:update')")
    @PostMapping("/update")
    public ResBean update(@Valid @RequestBody YoungKeyword keyword) {

        logger.info("关键词更新入参：{}", JSONUtil.toJsonStr(keyword));

        Optional<Integer> optional = keyWordService.updateById(keyword);
        if (!optional.isPresent()) {
            return ResBean.failed("更新关键词失败");
        }

        return ResBean.success(keyword);
    }

    @ApiOperation("删除关键词对象")
    @PreAuthorize("@pms.hasPermission('admin:keyword:delete')")
    @PostMapping("/delete")
    public ResBean delete(@RequestBody YoungKeyword keyword) {

        logger.info("删除关键词对象入参：{}", JSONUtil.toJsonStr(keyword));
        Integer id = keyword.getId();
        if (id == null) {
            return ResBean.failed("参数错误");
        }
        Optional<Integer> optional = keyWordService.delete(id);
        if (!optional.isPresent()) {
            return ResBean.failed("删除关键词失败");
        }

        return ResBean.success(keyword);

    }
}
