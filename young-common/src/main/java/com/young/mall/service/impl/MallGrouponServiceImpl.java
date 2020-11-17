package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungGrouponMapper;
import com.young.db.entity.YoungGroupon;
import com.young.db.entity.YoungGrouponExample;
import com.young.db.mapper.GroupOnListMapper;
import com.young.db.pojo.GroupOnListPojo;
import com.young.mall.service.MallGrouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 团购活动
 * @Author: yqz
 * @CreateDate: 2020/11/16 21:24
 */
@Service
public class MallGrouponServiceImpl implements MallGrouponService {

    @Autowired
    private YoungGrouponMapper youngGrouponMapper;

    @Autowired
    private GroupOnListMapper groupOnListMapper;

    @Override
    public List<YoungGroupon> queryJoinRecord(Integer id) {
        YoungGrouponExample example = new YoungGrouponExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false).andPayedEqualTo(true);
        example.orderBy("add_time desc");
        return youngGrouponMapper.selectByExample(example);
    }

    @Override
    public Optional<List<GroupOnListPojo>> list(String goodsSn,
                                                Integer page, Integer size,
                                                String sort, String order) {

        GroupOnListPojo pojo = new GroupOnListPojo();
        pojo.setGoodsSn(goodsSn);
        PageHelper.startPage(page, size);
        List<GroupOnListPojo> list = groupOnListMapper.list(pojo);
        return Optional.ofNullable(list);
    }
}
