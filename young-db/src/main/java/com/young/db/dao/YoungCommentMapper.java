package com.young.db.dao;

import com.young.db.entity.YoungComment;
import com.young.db.entity.YoungCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungCommentMapper {
    long countByExample(YoungCommentExample example);

    int deleteByExample(YoungCommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungComment record);

    int insertSelective(YoungComment record);

    List<YoungComment> selectByExample(YoungCommentExample example);

    YoungComment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungComment record, @Param("example") YoungCommentExample example);

    int updateByExample(@Param("record") YoungComment record, @Param("example") YoungCommentExample example);

    int updateByPrimaryKeySelective(YoungComment record);

    int updateByPrimaryKey(YoungComment record);
}