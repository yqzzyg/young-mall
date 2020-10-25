package com.young.db.dao;

import com.young.db.entity.YoungDelPicture;
import com.young.db.entity.YoungDelPictureExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungDelPictureMapper {
    long countByExample(YoungDelPictureExample example);

    int deleteByExample(YoungDelPictureExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungDelPicture record);

    int insertSelective(YoungDelPicture record);

    List<YoungDelPicture> selectByExample(YoungDelPictureExample example);

    YoungDelPicture selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungDelPicture record, @Param("example") YoungDelPictureExample example);

    int updateByExample(@Param("record") YoungDelPicture record, @Param("example") YoungDelPictureExample example);

    int updateByPrimaryKeySelective(YoungDelPicture record);

    int updateByPrimaryKey(YoungDelPicture record);
}