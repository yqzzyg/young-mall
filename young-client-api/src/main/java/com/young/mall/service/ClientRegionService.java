package com.young.mall.service;

import com.young.db.entity.YoungRegion;

import java.util.List;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/12/17 15:00
 */
public interface ClientRegionService {

    /**
     * 查询所有地市
     *
     * @return
     */
    List<YoungRegion> getAll();

    /**
     * 根据id查询地市
     *
     * @param id
     * @return
     */
    YoungRegion findById(Integer id);

    /**
     * 根据父id查询
     *
     * @param parentId 父id
     * @return 地市列表
     */
    List<YoungRegion> queryByPid(Integer parentId);
}
