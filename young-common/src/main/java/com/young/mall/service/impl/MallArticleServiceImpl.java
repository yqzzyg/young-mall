package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungArticleMapper;
import com.young.db.entity.YoungArticle;
import com.young.db.entity.YoungArticleExample;
import com.young.mall.service.MallArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 公共推广业务
 * @Author: yqz
 * @CreateDate: 2020/11/14 12:06
 */
@Service
public class MallArticleServiceImpl implements MallArticleService {

    @Autowired
    private YoungArticleMapper youngArticleMapper;

    @Override
    public Optional<List<YoungArticle>> querySelective(String title, Integer page,
                                                       Integer size, String sort,
                                                       String order) {

        YoungArticleExample example = new YoungArticleExample();

        YoungArticleExample.Criteria criteria = example.createCriteria();
        if (StrUtil.isNotEmpty(title)) {
            criteria.andTitleLike("%" + title + "%");
        }
        criteria.andDeletedEqualTo(false);
        if (StrUtil.isNotEmpty(sort) && StrUtil.isNotEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        List<YoungArticle> articleList = youngArticleMapper.selectByExample(example);

        return Optional.ofNullable(articleList);
    }

    @Override
    public Optional<YoungArticle> details(Integer aid) {

        YoungArticle youngArticle = youngArticleMapper.selectByPrimaryKey(aid);

        return Optional.ofNullable(youngArticle);
    }

    @Override
    public Optional<Integer> create(YoungArticle youngArticle) {
        youngArticle.setAddTime(LocalDateTime.now());
        youngArticle.setUpdateTime(LocalDateTime.now());

        int count = youngArticleMapper.insertSelective(youngArticle);
        return Optional.ofNullable(count);
    }

    @Override
    public Optional<Integer> update(YoungArticle article) {

        article.setUpdateTime(LocalDateTime.now());
        int count = youngArticleMapper.updateByPrimaryKeySelective(article);
        return Optional.ofNullable(count);
    }

    @Override
    public Optional<Integer> delete(Integer aid) {

        int count = youngArticleMapper.logicalDeleteByPrimaryKey(aid);

        return Optional.ofNullable(count);
    }

    @Override
    public boolean checkExist(String title) {
        YoungArticleExample example = new YoungArticleExample();
        example.createCriteria().andTitleEqualTo(title).andDeletedEqualTo(false);
        int count = (int) youngArticleMapper.countByExample(example);

        return count > 0;
    }
}
