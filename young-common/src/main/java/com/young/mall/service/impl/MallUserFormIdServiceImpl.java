package com.young.mall.service.impl;

import com.young.db.dao.YoungUserFormidMapper;
import com.young.db.entity.YoungUserFormid;
import com.young.db.entity.YoungUserFormidExample;
import com.young.mall.service.MallUserFormIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/2 17:09
 */
@Service
public class MallUserFormIdServiceImpl implements MallUserFormIdService {

    @Autowired
    private YoungUserFormidMapper youngUserFormidMapper;

    @Override
    public Optional<YoungUserFormid> queryByOpenId(String openId) {

        YoungUserFormidExample example = new YoungUserFormidExample();
        // 符合找到该用户记录，且可用次数大于1，且还未过期
        example.createCriteria().andOpenidEqualTo(openId).andExpireTimeGreaterThan(LocalDateTime.now());
        YoungUserFormid youngUserFormid = youngUserFormidMapper.selectOneByExample(example);
        return Optional.ofNullable(youngUserFormid);
    }

    @Override
    public Optional<Integer> updateUserFormId(YoungUserFormid userFormid) {

        //更新或者删除缓存
        if (userFormid.getIsprepay() && userFormid.getUseamount() > 1) {
            userFormid.setUseamount(userFormid.getUseamount() - 1);
            userFormid.setUpdateTime(LocalDateTime.now());
            int count = youngUserFormidMapper.updateByPrimaryKey(userFormid);
            return Optional.ofNullable(count);
        } else {
            int count = youngUserFormidMapper.deleteByPrimaryKey(userFormid.getId());
            return Optional.ofNullable(count);
        }
    }
}
