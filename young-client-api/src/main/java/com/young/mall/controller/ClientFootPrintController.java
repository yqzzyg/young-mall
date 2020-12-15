package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageInfo;
import com.young.db.entity.YoungFootprint;
import com.young.db.entity.YoungGoods;
import com.young.mall.common.ResBean;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.service.ClientFootPrintService;
import com.young.mall.service.ClientGoodsService;
import com.young.mall.service.ClientUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 用户足迹
 * @Author: yqz
 * @CreateDate: 2020/12/15 14:18
 */
@Api(tags = "ClientFootPrintController")
@RestController
@RequestMapping("/client/footprint")
public class ClientFootPrintController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private ClientFootPrintService clientFootPrintService;

    @Autowired
    private ClientGoodsService clientGoodsService;

    @ApiOperation("用户足迹列表")
    @GetMapping("/list")
    public ResBean list(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size) {

        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            return ResBean.validateFailed("用户未登录");
        }

        List<YoungFootprint> footprintList = clientFootPrintService.queryByAddTime(userInfo.getYoungUser().getId(), page, size);

        long count = PageInfo.of(footprintList).getTotal();
        int totalPages = (int) Math.ceil((double) count / size);

        List<Object> footprintVoList = new ArrayList<>(footprintList.size());

        for (YoungFootprint footprint : footprintList) {
            Map<String, Object> c = new HashMap<>();
            c.put("id", footprint.getId());
            c.put("goodsId", footprint.getGoodsId());
            c.put("addTime", footprint.getAddTime());

            YoungGoods goods = clientGoodsService.findById(footprint.getGoodsId());
            c.put("name", goods.getName());
            c.put("brief", goods.getBrief());
            c.put("picUrl", goods.getPicUrl());
            c.put("retailPrice", goods.getRetailPrice());

            footprintVoList.add(c);
        }
        Map<String, Object> result = new HashMap<>(4);
        result.put("footprintList", footprintVoList);
        result.put("totalPages", totalPages);

        return ResBean.success(result);
    }

    @ApiOperation("删除用户足迹")
    @PostMapping("/delete")
    public ResBean delete(@RequestBody Map<String, Integer> body) {
        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            return ResBean.validateFailed("用户未登录");
        }

        Integer footprintId = body.get("id");
        if (footprintId == null) {
            return ResBean.validateFailed();
        }

        YoungFootprint footprint = clientFootPrintService.findById(footprintId);

        if (BeanUtil.isEmpty(footprint)) {
            return ResBean.validateFailed();
        }
        if (!footprint.getUserId().equals(userInfo.getYoungUser().getId())) {
            return ResBean.validateFailed();
        }
        //删除足迹
        Integer count = clientFootPrintService.deleteById(footprintId);

        return ResBean.success("删除成功");
    }
}
