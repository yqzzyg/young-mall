package com.young.mall.service.impl;

import com.young.db.dao.YoungCommentMapper;
import com.young.db.entity.YoungComment;
import com.young.mall.service.ClientCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/12/24 14:05
 */
@Service
public class ClientCommentServiceImpl implements ClientCommentService {

    @Resource
    private YoungCommentMapper youngCommentMapper;

    @Override
    public Integer save(YoungComment comment) {

        comment.setAddTime(LocalDateTime.now());
        comment.setUpdateTime(LocalDateTime.now());
        return youngCommentMapper.insertSelective(comment);
    }

}
