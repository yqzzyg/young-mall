package com.young.mall.service.impl;

import com.young.db.dao.YoungArticleMapper;
import com.young.db.entity.YoungArticle;
import com.young.db.entity.YoungArticle.*;
import com.young.db.entity.YoungArticleExample;
import com.young.mall.service.ClientArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 客户端文章
 * @Author: yqz
 * @CreateDate: 2020/11/22 12:42
 */
@Service
public class ClientArticleServiceImpl implements ClientArticleService {

    @Autowired
    private YoungArticleMapper youngArticleMapper;

    private Column[] columns = new Column[] { Column.id, Column.title, Column.addTime, Column.type };

    @Override
    public List<YoungArticle> queryList(int page, int size, String sort, String order) {

        YoungArticleExample example = new YoungArticleExample();
        example.createCriteria().andDeletedEqualTo(false);
        example.setOrderByClause(sort + " " + order);
        List<YoungArticle> articleList = youngArticleMapper.selectByExampleSelective(example, columns);
        return articleList;
    }
}
