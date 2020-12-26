package com.young.mall.controller;

import com.young.db.entity.YoungSearchHistory;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.service.SearchHistoryService;
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
 * @Description: 用户搜索历史controller
 * @Author: yqz
 * @CreateDate: 2020/10/31 18:25
 */
@Api(tags = "HistoryController", description = "用户搜索历史")
@RestController
@RequestMapping("/admin/history")
public class HistoryController extends BaseController {


    @Autowired
    private SearchHistoryService historyService;

    @ApiOperation("用户搜索历史记录")
    @PreAuthorize("@pms.hasPermission('admin:history:list')")
    @GetMapping("/list")
    public ResBean querySearchHistoryList(String userId, String keyword,
                                          @RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size,
                                          @RequestParam(defaultValue = "add_time") String sort,
                                          @RequestParam(defaultValue = "desc") String order) {

        logger.error("userId:{},keyword:{},page:{},size:{},sort:{},order:{}", userId, keyword, page, size, sort, order);

        Optional<List<YoungSearchHistory>> optionalList = historyService.querySearchHistory(userId, keyword, page, size, sort, order);

        if (!optionalList.isPresent()) {
            return ResBean.failed("未查询到记录");
        }

        CommonPage<YoungSearchHistory> restPage = CommonPage.restPage(optionalList.get());
        return ResBean.success(restPage);
    }
}
