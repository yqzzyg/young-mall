package com.young.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungTopicMapper;
import com.young.db.entity.YoungTopic;
import com.young.db.entity.YoungTopic.Column;
import com.young.db.entity.YoungTopicExample;
import com.young.mall.service.ClientTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 专题service
 * @Author: yqz
 * @CreateDate: 2020/11/22 18:09
 */
@Service
public class ClientTopicServiceImpl implements ClientTopicService {

    @Autowired
    private YoungTopicMapper topicMapper;

    private Column[] columns = new Column[]{Column.id, Column.title, Column.subtitle, Column.price, Column.picUrl,
            Column.readCount};

    @Override
    public List<YoungTopic> queryList(int page, int size) {
        return queryList(page, size, "add_time", "desc");
    }

    @Override
    public List<YoungTopic> queryList(int page, int size, String sort, String order) {
        YoungTopicExample example = new YoungTopicExample();
        example.createCriteria().andDeletedEqualTo(false);
        example.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page, size);
        return topicMapper.selectByExampleSelective(example, columns);
    }
}
