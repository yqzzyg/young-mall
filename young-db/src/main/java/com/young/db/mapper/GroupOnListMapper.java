package com.young.db.mapper;

import com.young.db.pojo.GroupOnListPojo;

import java.util.List;

/**
 * @Description: 联表查询团购活动
 * @Author: yqz
 * @CreateDate: 2020/11/17 10:50
 */
public interface GroupOnListMapper {

    List<GroupOnListPojo> list(GroupOnListPojo groupOnListPojo);
}
