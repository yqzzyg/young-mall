package com.young.mall.service;

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
}
