package com.young.mall.service;

import com.young.db.entity.YoungComment;

/**
 * @Description: 用户评论业务
 * @Author: yqz
 * @CreateDate: 2020/12/24 13:56
 */
public interface ClientCommentService {

    /**
     * 保存评论
     *
     * @param comment
     * @return
     */
    Integer save(YoungComment comment);

}
