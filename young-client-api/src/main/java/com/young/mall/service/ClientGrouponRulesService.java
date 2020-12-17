package com.young.mall.service;

import com.young.db.entity.YoungGrouponRules;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/22 17:53
 */
public interface ClientGrouponRulesService {
    /**
     * 查询团购规则
     *
     * @param page
     * @param size
     * @return
     */
    List<Map<String, Object>> queryList(int page, int size);

    List<Map<String, Object>> queryList(int page, int size, String sort, String order);

    /**
     * 根据商品编号查询团购信息
     *
     * @param goodsSn
     * @return
     */
    List<YoungGrouponRules> queryByGoodsSn(String goodsSn);

    /**
     * 根据ID查找对应团购项
     * @param id
     * @return
     */
    YoungGrouponRules queryById(Integer id);
}
