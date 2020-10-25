package com.young.db.dao;

import com.young.db.entity.YoungCoupon;
import com.young.db.entity.YoungCouponExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungCouponMapper {
    long countByExample(YoungCouponExample example);

    int deleteByExample(YoungCouponExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungCoupon record);

    int insertSelective(YoungCoupon record);

    List<YoungCoupon> selectByExample(YoungCouponExample example);

    YoungCoupon selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungCoupon record, @Param("example") YoungCouponExample example);

    int updateByExample(@Param("record") YoungCoupon record, @Param("example") YoungCouponExample example);

    int updateByPrimaryKeySelective(YoungCoupon record);

    int updateByPrimaryKey(YoungCoupon record);
}