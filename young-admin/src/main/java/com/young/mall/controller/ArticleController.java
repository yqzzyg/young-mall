package com.young.mall.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.young.db.entity.YoungArticle;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.enums.AdminResponseCode;
import com.young.mall.enums.ArticleType;
import com.young.mall.service.MallArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 公告
 * @Author: yqz
 * @CreateDate: 2020/11/14 12:03
 */
@Api(tags = "ArticleController", description = "公告")
@RestController
@RequestMapping("/admin/article")
public class ArticleController extends BaseController {

    @Autowired
    private MallArticleService youngArticleService;

    @ApiOperation("分页查询公共")
    @GetMapping("/list")
    public ResBean list(String title, @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size,
                        @RequestParam(defaultValue = "add_time") String sort,
                        @RequestParam(defaultValue = "desc") String order) {
        logger.error("title:{},page:{},size:{},sort:{},order:{}", title, page, size, sort, order);

        Optional<List<YoungArticle>> listOptional = youngArticleService.querySelective(title, page, size, sort, order);
        if (!listOptional.isPresent()) {
            return ResBean.failed("查询失败");
        }
        CommonPage<YoungArticle> restPage = CommonPage.restPage(listOptional.get());
        return ResBean.success(restPage);
    }

    @ApiOperation("公告详情")
    @GetMapping("/detail")
    public ResBean detail(@NotNull @RequestParam("id") Integer id) {
        logger.error("查看公告详情入参：{}", id);
        Optional<YoungArticle> articleOptional = youngArticleService.details(id);
        if (!articleOptional.isPresent()) {
            return ResBean.failed("查看公告详情失败");
        }
        YoungArticle article = articleOptional.get();
        logger.error("查看公告详情出参：{}", article);
        return ResBean.success(article);
    }

    @ApiOperation("更新公告")
    @PostMapping("/update")
    public ResBean update(@Valid @RequestBody YoungArticle youngArticle) {
        logger.error("更新公告入参：{}", JSONUtil.toJsonStr(youngArticle));
        if (StrUtil.isEmpty(youngArticle.getType())) {
            //如果没有传入类型，默认为信息公告
            youngArticle.setType(ArticleType.ANNOUNCE.type());
        }
        Optional<Integer> optional = youngArticleService.update(youngArticle);
        if (!optional.isPresent() || optional.get() <= 0) {
            logger.error("更新公告失败");
            return ResBean.failed("更新公告失败");
        }
        return ResBean.success(optional.get());
    }

    @ApiOperation("删除公告")
    @PostMapping("/delete")
    public ResBean delete(@RequestBody YoungArticle youngArticle) {
        logger.error("删除公告入参：{}", youngArticle);

        Integer id = youngArticle.getId();
        if (id == null) {
            return ResBean.validateFailed("参数错误");
        }
        Optional<Integer> optional = youngArticleService.delete(id);
        if (!optional.isPresent()) {
            return ResBean.failed("删除公告失败");
        }
        return ResBean.success(optional.get());
    }

    @ApiOperation("添加公告")
    @PostMapping("/create")
    public ResBean create(@Valid @RequestBody YoungArticle youngArticle) {
        logger.error("添加公告入参：{}", youngArticle);

        String title = youngArticle.getTitle();
        if (youngArticleService.checkExist(title)) {
            return ResBean.failed(AdminResponseCode.ARTICLE_NAME_EXIST);
        }
        if (StrUtil.isEmpty(youngArticle.getType())) {
            //如果没有传入类型，默认为信息公告
            youngArticle.setType(ArticleType.ANNOUNCE.type());
        }

        Optional<Integer> optional = youngArticleService.create(youngArticle);
        if (!optional.isPresent() || optional.get() <= 0) {
            return ResBean.failed("添加公告失败");
        }
        return ResBean.success("添加公告成功");
    }
}
