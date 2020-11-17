package com.young.mall.service;


import com.young.db.entity.YoungGroupon;
import com.young.db.pojo.GroupOnListPojo;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 团购业务
 * @Author: yqz
 * @CreateDate: 2020/11/16 15:16
 */
public interface MallGrouponService {

    List<YoungGroupon> queryJoinRecord(Integer id);
    /**
     * 分页查询团购活动
     * @param goodsSn
     * @param page
     * @param size
     * @param sort
     * @param order
     * @return
     */
    Optional<List<GroupOnListPojo>> list(String goodsSn,
                                         Integer page, Integer size,
                                         String sort, String order);

}
