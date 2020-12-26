package com.young.mall.controller;

import com.young.db.entity.YoungFeedback;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.service.FeedBackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 意见反馈
 * @Author: yqz
 * @CreateDate: 2020/10/31 21:05
 */
@Api(tags = "FeedBackController", description = "意见反馈")
@RestController
@RequestMapping("/admin/feedback")
public class FeedBackController extends BaseController {

    @Autowired
    private FeedBackService feedBackService;

    @ApiOperation("查询用户反馈集合")
    @PreAuthorize("@pms.hasPermission('admin:feedback:list')")
    @GetMapping("/list")
    public ResBean queryFeedBackList(Integer userId, String username,
                                     @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer size,
                                     @RequestParam(defaultValue = "add_time") String sort,
                                     @RequestParam(defaultValue = "desc") String order) {
        logger.error("userId:{},username:{},page:{},size:{},sort:{},order:{}", userId, username, page, size, sort, order);

        Optional<List<YoungFeedback>> optionalList = feedBackService.queryFeedBackList(userId, username, page, size, sort, order);

        if (!optionalList.isPresent()) {
            return ResBean.failed("用户反馈查询失败");
        }

        CommonPage<YoungFeedback> restPage = CommonPage.restPage(optionalList.get());

        return ResBean.success(restPage);

    }
}
