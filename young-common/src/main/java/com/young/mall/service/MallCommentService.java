package com.young.mall.service;

import com.young.db.entity.YoungComment;
import com.young.mall.common.ResBean;
import com.young.mall.dto.CommentDto;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 评论相关业务
 * @Author: yqz
 * @CreateDate: 2020/11/13 21:29
 */
public interface MallCommentService {

    /**
     * 分页查询评论
     *
     * @param userId  用户ID
     * @param valueId 商品ID
     * @param page
     * @param size
     * @param sort
     * @param order
     * @return
     */
    Optional<List<YoungComment>> querySelective(String userId, String valueId,
                                                Integer page, Integer size,
                                                String sort, String order);

    /**
     * 通过评论id查找评论
     *
     * @param id
     * @return
     */
    Optional<YoungComment> findById(Integer id);

    /**
     * 保存回复
     *
     * @param youngComment
     * @return
     */
    Optional<Integer> save(YoungComment youngComment);

    /**
     * 回复订单评论
     *
     * @param commentDto
     * @return
     */
    ResBean reply(CommentDto commentDto);

    /**
     * 删除评论
     * @param cid
     * @return
     */
    Integer delete(Integer cid);
}
