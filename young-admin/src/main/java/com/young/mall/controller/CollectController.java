package com.young.mall.controller;

import com.young.db.entity.YoungCollect;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.service.CollectService;
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
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/10/30 22:17
 */
@Api(tags = "CollectController", description = "用户收藏")
@RequestMapping("/admin/collect")
@RestController
public class CollectController extends BaseController {

    @Autowired
    private CollectService collectService;

    @ApiOperation("查询所有用户收藏")
    @PreAuthorize("@pms.hasPermission('admin:collect:list')")
    @GetMapping("/list")
    public ResBean queryCollectList(String userId, String valueId,
                                    @RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    @RequestParam(defaultValue = "add_time") String sort,
                                    @RequestParam(defaultValue = "desc") String order) {
        logger.info("userId:{},valueId:{},page:{},size:{},sort:{},order:{}", userId, valueId, page, size, sort, order);

        Optional<List<YoungCollect>> listOptional = collectService.queryCollectList(userId, valueId, page, size, sort, order);

        if (!listOptional.isPresent()) {
            return ResBean.failed("查询会员收藏失败");
        }

        CommonPage<YoungCollect> restPage = CommonPage.restPage(listOptional.get());

        return ResBean.success(restPage);
    }
}
