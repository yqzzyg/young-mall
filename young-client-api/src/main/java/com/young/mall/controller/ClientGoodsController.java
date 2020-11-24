package com.young.mall.controller;

import com.young.mall.common.ResBean;
import com.young.mall.service.ClientGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 用户端商品相关
 * @Author: yqz
 * @CreateDate: 2020/11/24 22:26
 */
@Api(tags = "ClientGoodsController")
@RestController
@RequestMapping("/wx/goods")
public class ClientGoodsController {

    @Autowired
    private ClientGoodsService clientGoodsService;

    @ApiOperation("查询在售商品数量")
    @GetMapping("/count")
    public ResBean<Integer> count() {
        Integer countOnSale = clientGoodsService.getGoodsCountOnSale();
        return ResBean.success(countOnSale);
    }
}
