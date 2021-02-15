package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import com.young.db.entity.YoungAddress;
import com.young.mall.common.ResBean;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.service.ClientAddressService;
import com.young.mall.service.ClientUserService;
import com.young.mall.service.ClientRegionService;
import com.young.mall.util.RegexUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 用户收货地址服务
 * @Author: yqz
 * @CreateDate: 2020/12/17 12:06
 */
@Api(tags = "ClientAddressController")
@RestController
@RequestMapping("/client/address")
public class ClientAddressController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientAddressService clientAddressService;

    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private ClientRegionService youngRegionService;

    @ApiOperation("用户收货地址列表")
    @GetMapping("/list")
    public ResBean list() {
        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            logger.error("获取用户收货地址列表失败，未登录。");
            return ResBean.unauthorized("请登录！");
        }
        return clientAddressService.list(userInfo.getYoungUser().getId());
    }

    @ApiOperation("添加或更新收货地址")
    @PostMapping("save")
    public ResBean save(@RequestBody YoungAddress address) {
        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            logger.error("添加或更新收货地址失败，未登录。");
            return ResBean.unauthorized("请登录！");
        }
        Integer userId = userInfo.getYoungUser().getId();

        ResBean error = validate(address);
        if (!BeanUtil.isEmpty(error)) {
            return error;
        }

        // 如果设置本次地址为默认地址，则需要重置其他收货地址的默认选项
        if (address.getIsDefault()) {
            Integer count = clientAddressService.resetDefault(userId);
        }

        if (address.getId() == null || address.getId().equals(0)) {
            address.setId(null);
            address.setUserId(userId);
            clientAddressService.add(address);
            // 更新地址
        } else {
            address.setUserId(userId);
            if (clientAddressService.update(address) == 0) {
                return ResBean.failed("更新数据失败");
            }
        }
        return ResBean.success(address.getId());
    }

    /**
     * 收货地址详情
     *
     * @param id
     * @return
     */
    @ApiOperation("收货地址详情")
    @GetMapping("/detail")
    public ResBean detail(@NotNull @RequestParam("id") Integer id) {
        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            logger.error("添加或更新收货地址失败，未登录。");
            return ResBean.unauthorized("请登录！");
        }

        Integer userId = userInfo.getYoungUser().getId();
        YoungAddress address = clientAddressService.findById(id);

        if (BeanUtil.isEmpty(address)) {
            return ResBean.validateFailed();
        }
        Map<String, Object> data = new HashMap<>();
        data.put("id", address.getId());
        data.put("name", address.getName());
        data.put("provinceId", address.getProvinceId());
        data.put("cityId", address.getCityId());
        data.put("areaId", address.getAreaId());
        data.put("mobile", address.getMobile());
        data.put("address", address.getAddress());
        data.put("isDefault", address.getIsDefault());
        String pname = youngRegionService.findById(address.getProvinceId()).getName();
        data.put("provinceName", pname);
        String cname = youngRegionService.findById(address.getCityId()).getName();
        data.put("cityName", cname);
        String dname = youngRegionService.findById(address.getAreaId()).getName();
        data.put("areaName", dname);
        return ResBean.success(data);
    }


    @ApiOperation("删除收货地址")
    @PostMapping("/delete")
    public ResBean delete(@RequestBody YoungAddress address) {
        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            logger.error("删除收货地址失败，未登录。");
            return ResBean.unauthorized("请登录！");
        }

        Integer id = address.getId();
        if (id == null) {
            return ResBean.validateFailed();
        }
        Integer count = clientAddressService.delete(id);
        logger.error("收货地址删除个数：{}", count);
        return ResBean.success("删除成功");
    }


    private ResBean validate(YoungAddress address) {
        String name = address.getName();
        if (StringUtils.isEmpty(name)) {
            return ResBean.validateFailed();
        }

        // 测试收货手机号码是否正确
        String mobile = address.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            return ResBean.validateFailed();
        }
        if (!RegexUtil.isMobileExact(mobile)) {
            return ResBean.validateFailed();
        }

        Integer pid = address.getProvinceId();
        if (pid == null) {
            return ResBean.validateFailed();
        }
        if (youngRegionService.findById(pid) == null) {
            return ResBean.validateFailed();
        }

        Integer cid = address.getCityId();
        if (cid == null) {
            return ResBean.validateFailed();
        }
        if (youngRegionService.findById(cid) == null) {
            return ResBean.validateFailed();
        }

        Integer aid = address.getAreaId();
        if (aid == null) {
            return ResBean.validateFailed();
        }
        if (youngRegionService.findById(aid) == null) {
            return ResBean.validateFailed();
        }

        String detailedAddress = address.getAddress();
        if (StringUtils.isEmpty(detailedAddress)) {
            return ResBean.validateFailed();
        }

        Boolean isDefault = address.getIsDefault();
        if (isDefault == null) {
            return ResBean.validateFailed();
        }
        return null;
    }

}
