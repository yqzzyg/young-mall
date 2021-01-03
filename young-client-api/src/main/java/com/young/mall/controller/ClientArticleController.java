package com.young.mall.controller;

import com.young.db.entity.YoungArticle;
import com.young.mall.common.ResBean;
import com.young.mall.service.ClientArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @Description: 公告
 * @Author: yqz
 * @CreateDate: 2020/12/21 18:05
 */

@Api(tags = "ClientArticleController")
@RestController
@RequestMapping("/client/article")
@Validated
public class ClientArticleController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientArticleService clientArticleService;


    @ApiOperation("文章详情")
    @GetMapping("/detail")
    public ResBean detail(@NotNull(message = "公告id不能为空") @RequestParam("id") Integer id) {

        YoungArticle article = clientArticleService.findById(id);
        return ResBean.success(article);
    }
}
