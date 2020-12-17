package com.young.mall.controller;

import com.young.db.entity.YoungRegion;
import com.young.mall.common.ResBean;
import com.young.mall.service.ClientRegionService;
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
import java.util.List;

/**
 * @Description: 区域地市服务
 * @Author: yqz
 * @CreateDate: 2020/12/17 17:14
 */
@Api(tags = "ClientRegionController")
@RestController
@RequestMapping("/client/region")
@Validated
public class ClientRegionController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientRegionService clientRegionService;

    /**
     * 区域数据
     * 根据父区域ID，返回子区域数据。 如果父区域ID是0，则返回省级区域数据；
     *
     * @param pid 父区域ID
     * @return 区域数据
     */
    @ApiOperation("区域数据")
    @GetMapping("/list")
    public ResBean list(@NotNull(message = "地市区域pid不能为空") @RequestParam("pid") Integer pid) {

        List<YoungRegion> regionList = clientRegionService.queryByPid(pid);

        return ResBean.success(regionList);
    }
}
