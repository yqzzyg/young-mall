package com.young.mall.service;

import com.young.db.entity.YoungGoods;

import java.util.List;

/**
 * @Description: 小程序端
 * @Author: yqz
 * @CreateDate: 2020/11/22 11:28
 */
public interface ClientGoodsService {


    /**
     * 获取新品
     *
     * @param page
     * @param size
     * @return
     */
    List<YoungGoods> queryByNew(int page, int size);

    /**
     * 获取新品上市
     *
     * @param page
     * @param size
     * @return
     */
    List<YoungGoods> queryByHot(int page, int size);
}
