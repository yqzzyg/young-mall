package com.young.mall.service;

import com.young.db.entity.YoungCollect;

import java.util.List;

/**
 * @Description: 用户收藏
 * @Author: yqz
 * @CreateDate: 2020/12/3 17:39
 */
public interface ClientCollectService {

    /**
     * 根据用户id和商品id查询
     *
     * @param uid
     * @param gid
     * @return
     */
    int count(Integer uid, Integer gid);

    /**
     * 查询收藏list
     *
     * @param userId 用户id
     * @param type   收藏类型
     * @param page   分页页数
     * @param size   分页大小
     * @return 查询收藏list
     */
    List<YoungCollect> queryByType(Integer userId, Byte type, Integer page, Integer size);

    /**
     * 查询收藏数量
     *
     * @param userId 用户id
     * @param type   类型
     * @return
     */
    int countByType(Integer userId, Byte type);

    /**
     * 通过收藏类型和收藏value id查询具体收藏
     *
     * @param userId  用户id
     * @param type    类型
     * @param valueId 商品或者专题id
     * @return 收藏
     */
    YoungCollect queryByTypeAndValue(Integer userId, Byte type, Integer valueId);

    /**
     * 删除收藏
     *
     * @param id
     * @return 数量
     */
    Integer deleteById(Integer id);

    /**
     * 添加收藏
     *
     * @param collect
     * @return 数量
     */
    Integer add(YoungCollect collect);
}
