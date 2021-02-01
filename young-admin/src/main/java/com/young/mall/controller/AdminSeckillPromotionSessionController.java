package com.young.mall.controller;

import com.young.db.pojo.SeckillPromotionSessionDetail;
import com.young.mall.common.ResBean;
import com.young.mall.service.AdminSeckillSessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 秒杀购场次管理
 * @Author: yqz
 * @CreateDate: 2021/2/1 21:36
 */
@Api(tags = "秒杀购场次管理AdminSeckillPromotionSessionController")
@RestController
@RequestMapping("/admin/flashSession")
public class AdminSeckillPromotionSessionController {


    @Resource
    private AdminSeckillSessionService seckillPromotionSessionService;

    @ApiOperation("获取全部可选场次及其数量")
    @RequestMapping(value = "/selectList", method = RequestMethod.GET)
    @ResponseBody
    public ResBean<List<SeckillPromotionSessionDetail>> selectList(Long flashPromotionId) {
        List<SeckillPromotionSessionDetail> promotionSessionList = seckillPromotionSessionService.selectList(flashPromotionId);
        return ResBean.success(promotionSessionList);
    }
}
