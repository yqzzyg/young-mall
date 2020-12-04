package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungCommentMapper;
import com.young.db.entity.YoungComment;
import com.young.db.entity.YoungCommentExample;
import com.young.mall.common.ResBean;
import com.young.mall.dto.CommentDto;
import com.young.mall.service.MallCommentService;
import com.young.mall.enums.AdminResponseCode;
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
public class MallCommentServiceImpl implements MallCommentService {
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

        youngComment.setReplyTime(LocalDateTime.now());
        int count = youngCommentMapper.updateByPrimaryKeySelective(youngComment);
        return Optional.ofNullable(count);
    }


    @Override
    public ResBean reply(CommentDto commentDto) {

        // 目前只支持回复一次
        YoungComment comment = findById(commentDto.getCommentId()).get();
        Integer replySts = comment.getReplySts();

        if (replySts == 1) {
            logger.info("该评论已回复");
            return ResBean.failed(AdminResponseCode.ORDER_REPLY_EXIST);
        }
        YoungComment youngComment = new YoungComment();
        youngComment.setId(commentDto.getCommentId());
        youngComment.setReplyContent(commentDto.getContent());
        youngComment.setReplySts(1);
        Integer count = save(youngComment).get();
        return ResBean.success(count);
    }

    @Override
    public Integer delete(Integer cid) {

        int count = youngCommentMapper.logicalDeleteByPrimaryKey(cid);
        return count;
    }

    @Override
    public List<YoungComment> queryGoodsByGid(Integer id, int page, int size) {
        YoungCommentExample example = new YoungCommentExample();
        example.setOrderByClause(YoungComment.Column.addTime.desc());
        example.or().andValueIdEqualTo(id).andTypeEqualTo((byte) 0).andDeletedEqualTo(false);
        PageHelper.startPage(page, size);
        return youngCommentMapper.selectByExample(example);
    }
}
