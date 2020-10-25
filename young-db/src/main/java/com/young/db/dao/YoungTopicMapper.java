package com.young.db.dao;

import com.young.db.entity.YoungTopic;
import com.young.db.entity.YoungTopicExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungTopicMapper {
    long countByExample(YoungTopicExample example);

    int deleteByExample(YoungTopicExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungTopic record);

    int insertSelective(YoungTopic record);

    List<YoungTopic> selectByExampleWithBLOBs(YoungTopicExample example);

    List<YoungTopic> selectByExample(YoungTopicExample example);

    YoungTopic selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungTopic record, @Param("example") YoungTopicExample example);

    int updateByExampleWithBLOBs(@Param("record") YoungTopic record, @Param("example") YoungTopicExample example);

    int updateByExample(@Param("record") YoungTopic record, @Param("example") YoungTopicExample example);

    int updateByPrimaryKeySelective(YoungTopic record);

    int updateByPrimaryKeyWithBLOBs(YoungTopic record);

    int updateByPrimaryKey(YoungTopic record);
}