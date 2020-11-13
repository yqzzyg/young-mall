package com.young.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungCommentMapper;
import com.young.db.entity.YoungComment;
import com.young.db.entity.YoungCommentExample;
import com.young.mall.common.ResBean;
import com.young.mall.dto.CommentDto;
import com.young.mall.service.YoungCommentService;
import com.young.mall.utils.AdminResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 评论相关业务
 * @Author: yqz
 * @CreateDate: 2020/11/13 21:31
 */
@Service
public class YoungCommentServiceImpl implements YoungCommentService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private YoungCommentMapper youngCommentMapper;

    @Override
    public Optional<List<YoungComment>> querySelective(String userId, String valueId,
                                                       Integer page, Integer size,
                                                       String sort, String order) {

        YoungCommentExample example = new YoungCommentExample();
        YoungCommentExample.Criteria criteria = example.createCriteria();
        // type=2 是订单商品回复，这里过滤
        criteria.andTypeNotEqualTo((byte) 2);

        if (StrUtil.isNotEmpty(userId)) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (StrUtil.isNotEmpty(valueId)) {
            criteria.andValueIdEqualTo(Integer.valueOf(valueId));
        }

        if (StrUtil.isNotEmpty(sort) && StrUtil.isNotEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        List<YoungComment> commentList = youngCommentMapper.selectByExample(example);

        return Optional.ofNullable(commentList);
    }

    @Override
    public Optional<YoungComment> findById(Integer id) {

        YoungComment youngComment = youngCommentMapper.selectByPrimaryKey(id);
        return Optional.ofNullable(youngComment);
    }

    @Override
    public Optional<Integer> save(YoungComment youngComment) {
        youngComment.setAddTime(LocalDateTime.now());
        youngComment.setUpdateTime(LocalDateTime.now());
        int count = youngCommentMapper.insertSelective(youngComment);
        return Optional.ofNullable(count);
    }


    @Override
    public ResBean reply(CommentDto commentDto) {

        // 目前只支持回复一次
        YoungComment comment = findById(commentDto.getCommentId()).get();
        if (!BeanUtil.isEmpty(comment)) {
            logger.info("该评论已回复");
            return ResBean.failed(AdminResponseCode.ORDER_REPLY_EXIST);
        }
        YoungComment youngComment = new YoungComment();
        youngComment.setType(((byte) 2));
        youngComment.setValueId(commentDto.getCommentId());
        youngComment.setContent(commentDto.getContent());
        youngComment.setUserId(0); // 评价回复没有用
        youngComment.setStar((short) 0); // 评价回复没有用
        youngComment.setHasPicture(false); // 评价回复没有用
        youngComment.setPicUrls(new String[]{}); // 评价回复没有用

        Integer count = save(youngComment).get();
        return ResBean.success(count);
    }
}
