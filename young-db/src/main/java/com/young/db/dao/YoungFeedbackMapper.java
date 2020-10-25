package com.young.db.dao;

import com.young.db.entity.YoungFeedback;
import com.young.db.entity.YoungFeedbackExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungFeedbackMapper {
    long countByExample(YoungFeedbackExample example);

    int deleteByExample(YoungFeedbackExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungFeedback record);

    int insertSelective(YoungFeedback record);

    List<YoungFeedback> selectByExample(YoungFeedbackExample example);

    YoungFeedback selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungFeedback record, @Param("example") YoungFeedbackExample example);

    int updateByExample(@Param("record") YoungFeedback record, @Param("example") YoungFeedbackExample example);

    int updateByPrimaryKeySelective(YoungFeedback record);

    int updateByPrimaryKey(YoungFeedback record);
}