package com.young.db.dao;

import com.young.db.entity.YoungIssue;
import com.young.db.entity.YoungIssueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungIssueMapper {
    long countByExample(YoungIssueExample example);

    int deleteByExample(YoungIssueExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungIssue record);

    int insertSelective(YoungIssue record);

    List<YoungIssue> selectByExample(YoungIssueExample example);

    YoungIssue selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungIssue record, @Param("example") YoungIssueExample example);

    int updateByExample(@Param("record") YoungIssue record, @Param("example") YoungIssueExample example);

    int updateByPrimaryKeySelective(YoungIssue record);

    int updateByPrimaryKey(YoungIssue record);
}