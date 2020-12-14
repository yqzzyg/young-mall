package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import com.young.db.entity.YoungCollect;
import com.young.db.entity.YoungGoods;
import com.young.mall.common.ResBean;
import com.young.mall.domain.AddOrDeleteVo;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.service.ClientCollectService;
import com.young.mall.service.ClientGoodsService;
import com.young.mall.service.ClientUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 用户收藏服务
 * @Author: yqz
 * @CreateDate: 2020/12/14 11:51
 */
@Api(tags = "ClientCollectController")
@RestController
@RequestMapping("/client/collect")
public class ClientCollectController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private ClientCollectService clientCollectService;

    @Autowired
    private ClientGoodsService clientGoodsService;


    /**
     * 用户收藏列表
     *
     * @param type 类型，如果是0则是商品收藏，如果是1则是专题收藏
     * @param page 分页页数
     * @param size 分页大小
     * @return 用户收藏列表
     */
    @ApiOperation("用户收藏列表")
    @GetMapping("/list")
    public ResBean list(@NotNull Byte type,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size) {

        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            return ResBean.validateFailed("用户未登录");
        }
        List<YoungCollect> collectList = clientCollectService.queryByType(userInfo.getYoungUser().getId(), type, page, size);
        int count = clientCollectService.countByType(userInfo.getYoungUser().getId(), type);
        //计算总共有多少页数据
        int totalPages = (int) Math.ceil((double) count / size);

        List<Map<String, Object>> collects = new ArrayList<>(collectList.size());

        for (YoungCollect collect : collectList) {
            Map<String, Object> c = new HashMap<>(16);
            c.put("id", collect.getId());
            c.put("type", collect.getType());
            c.put("valueId", collect.getValueId());

            YoungGoods goods = clientGoodsService.findById(collect.getValueId());
            c.put("name", goods.getName());
            c.put("brief", goods.getBrief());
            c.put("picUrl", goods.getPicUrl());
            c.put("retailPrice", goods.getRetailPrice());

            collects.add(c);
        }
        Map<String, Object> result = new HashMap<>(4);
        result.put("collectList", collects);
        result.put("totalPages", totalPages);
        return ResBean.success(result);
    }


    @ApiOperation("用户收藏添加或删除")
    @PostMapping("/addOrDelete")
    public ResBean addOrDelete(@Valid @RequestBody AddOrDeleteVo addOrDeleteVo) {
        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            return ResBean.validateFailed("用户未登录");
        }

        YoungCollect collect = clientCollectService.queryByTypeAndValue(userInfo.getYoungUser().getId(),
                addOrDeleteVo.getType(), addOrDeleteVo.getValueId());

        String handleType = StringUtils.EMPTY;
        if (!BeanUtil.isEmpty(collect)) {
            handleType = "delete";
            Integer count = clientCollectService.deleteById(collect.getId());
            logger.info("删除收藏结果(数量)：{}", count);
        } else {
            handleType = "add";
            collect = new YoungCollect();
            collect.setUserId(userInfo.getYoungUser().getId());
            collect.setValueId(addOrDeleteVo.getValueId());
            collect.setType(addOrDeleteVo.getType());
            Integer count = clientCollectService.add(collect);
            logger.info("添加收藏结果(数量)：{}", count);
        }

        Map<String, Object> data = new HashMap<>(2);
        data.put("type", handleType);

        return ResBean.success(data);
    }
}
