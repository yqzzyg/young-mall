package com.young.mall.service;

import com.young.db.entity.YoungAddress;
import com.young.mall.common.ResBean;

import java.util.List;

/**
 * @Description: 收获地址service
 * @Author: yqz
 * @CreateDate: 2020/12/16 14:47
 */
public interface ClientAddressService {

    /**
     * 查询默认收获地址
     *
     * @param userId 用户id
     * @return
     */
    YoungAddress findDefault(Integer userId);

    /**
     * 通过id查询收货地址
     *
     * @param id
     * @return
     */
    YoungAddress findById(Integer id);

    /**
     * 用户收货地址列表
     *
     * @param userId 用户ID
     * @return 收货地址列表
     */
    ResBean list(Integer userId);

    /**
     * 通过用户id查询地址列表
     *
     * @param userId 用户id
     * @return 地址列表
     */
    List<YoungAddress> queryByUid(Integer userId);

    /**
     * 取消用户的默认地址配置
     *
     * @param userId 用户id
     * @return
     */
    Integer resetDefault(Integer userId);

    /**
     * 添加地址
     *
     * @param address
     * @return
     */
    Integer add(YoungAddress address);

    /**
     * 更新收货地址
     *
     * @param address
     * @return
     */
    Integer update(YoungAddress address);

    /**
     * 删除收货地址
     *
     * @param id
     * @return
     */
    Integer delete(Integer id);

}
