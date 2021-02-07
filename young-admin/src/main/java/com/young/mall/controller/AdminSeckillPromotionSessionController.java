package com.young.mall.controller;

import com.young.db.entity.YoungSeckillPromotionSession;
import com.young.db.pojo.SeckillPromotionSessionDetail;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.service.AdminSeckillSessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 秒杀购场次管理(秒杀时间段列表)
 * @Author: yqz
 * @CreateDate: 2021/2/1 21:36
 */
@Api(tags = "秒杀购场次管理AdminSeckillPromotionSessionController")
@RestController
@RequestMapping("/admin/flashSession")
public class AdminSeckillPromotionSessionController {

    @Resource
    private AdminSeckillSessionService seckillSessionService;


    @ApiOperation("修改场次")
    @PostMapping(value = "/update/{id}")
    public ResBean update(@PathVariable Long id, @RequestBody YoungSeckillPromotionSession promotionSession) {
        int count = seckillSessionService.update(id, promotionSession);
        if (count > 0) {
            return ResBean.success(count);
        }
        return ResBean.failed();
    }

    @ApiOperation("添加场次")
    @PostMapping(value = "/create/{flashPromotionId}")
    public ResBean create(@PathVariable Long flashPromotionId, @RequestBody YoungSeckillPromotionSession promotionSession) {
        int count = seckillSessionService.create(flashPromotionId, promotionSession);
        if (count > 0) {
            return ResBean.success(count);
        }
        return ResBean.failed();
    }

    @ApiOperation("修改启用状态")
    @PostMapping(value = "/update/status/{id}")
    public ResBean updateStatus(@PathVariable Long id, Integer status) {
        int count = seckillSessionService.updateStatus(id, status);
        if (count > 0) {
            return ResBean.success(count);
        }
        return ResBean.failed();
    }

    @ApiOperation("删除场次")
    @PostMapping(value = "/delete/{id}")
    public ResBean delete(@PathVariable Long id) {
        int count = seckillSessionService.delete(id);
        if (count > 0) {
            return ResBean.success(count);
        }
        return ResBean.failed();
    }

    @ApiOperation("获取场次详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResBean<YoungSeckillPromotionSession> getItem(@PathVariable Long id) {
        YoungSeckillPromotionSession promotionSession = seckillSessionService.getItem(id);
        return ResBean.success(promotionSession);
    }


    @ApiOperation("获取全部可选场次及其数量(设置商品入口)")
    @GetMapping(value = "/selectList")
    public ResBean<CommonPage<SeckillPromotionSessionDetail>> selectList(Long flashPromotionId,
                                                                         @RequestParam(value = "size", defaultValue = "5") Integer size,
                                                                         @RequestParam(value = "page", defaultValue = "1") Integer page) {
        List<SeckillPromotionSessionDetail> promotionSessionList = seckillSessionService.selectList(flashPromotionId, size, page);

        CommonPage<SeckillPromotionSessionDetail> restPage = CommonPage.restPage(promotionSessionList);
        return ResBean.success(restPage);
    }
}
