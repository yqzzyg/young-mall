package com.young.mall.service.impl;

import com.young.db.entity.YoungArticle;
import com.young.mall.service.ClientArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 客户端文章
 * @Author: yqz
 * @CreateDate: 2020/11/22 12:42
 */
@Service
public class ClientArticleServiceImpl implements ClientArticleService {
    @Override
    public List<YoungArticle> queryList(int page, int size, String sort, String order) {
        return null;
    }
}
