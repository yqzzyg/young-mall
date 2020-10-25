package com.young.db.dao;

import com.young.db.entity.YoungCouponUser;
import com.young.db.entity.YoungCouponUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungCouponUserMapper {
    long countByExample(YoungCouponUserExample example);

    int deleteByExample(YoungCouponUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungCouponUser record);

    int insertSelective(YoungCouponUser record);

    List<YoungCouponUser> selectByExample(YoungCouponUserExample example);

    YoungCouponUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungCouponUser record, @Param("example") YoungCouponUserExample example);

    int updateByExample(@Param("record") YoungCouponUser record, @Param("example") YoungCouponUserExample example);

    int updateByPrimaryKeySelective(YoungCouponUser record);

    int updateByPrimaryKey(YoungCouponUser record);
}