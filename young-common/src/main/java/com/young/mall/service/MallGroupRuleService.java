package com.young.mall.service;

import com.young.db.entity.YoungGrouponRules;
import java.util.List;
import java.util.Optional;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/16 15:23
 */
public interface MallGroupRuleService {

    YoungGrouponRules queryById(Integer id);
    /**
     * 分页查询团购规则
     *
     * @param goodsId
     * @param page
     * @param size
     * @param sort
     * @param order
     * @return
     */
    Optional<List<YoungGrouponRules>> list(String goodsId,
                                           Integer page,
                                           Integer size,
                                           String sort, String order);

    /**
     * 更新团购
     *
     * @param youngGrouponRules
     * @return
     */
    Optional<Integer> updateById(YoungGrouponRules youngGrouponRules);

    /**
     * 增减团购
     *
     * @param youngGrouponRules
     * @return
     */
    Optional<Integer> createRoles(YoungGrouponRules youngGrouponRules);

    /**
     * 删除团购规则
     *
     * @param id
     * @return
     */
    Optional<Integer> delete(Integer id);
}
