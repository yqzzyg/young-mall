package com.young.mall.controller;

import com.young.db.entity.YoungSeckillPromotion;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.service.AdminSeckillPromotionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2021/2/1 16:16
 */
@Controller
@Api(tags = "秒杀活动管理AdminSeckillPromotion", description = "限时购活动管理")
@RestController
@RequestMapping("/admin/seckill")
public class AdminSeckillPromotionController {

    @Resource
    private AdminSeckillPromotionService promotionService;


    @ApiOperation("根据活动名称分页查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResBean<CommonPage<YoungSeckillPromotion>> getItem(@RequestParam(value = "keyword", required = false) String keyword,
                                                              @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<YoungSeckillPromotion> flashPromotionList = promotionService.list(keyword, pageSize, pageNum);

        return ResBean.success(CommonPage.restPage(flashPromotionList));
    }
}
