package com.young.mall.service.impl;

import com.young.db.dao.YoungUserFormidMapper;
import com.young.db.entity.YoungUserFormid;
import com.young.mall.service.ClientUserFormIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Description: 预付款表单记录
 * @Author: yqz
 * @CreateDate: 2020/12/29 14:49
 */
@Service
public class ClientUserFormIdServiceImpl implements ClientUserFormIdService {

    @Resource
    private YoungUserFormidMapper youngUserFormidMapper;

    @Override
    public Integer addUserFormId(YoungUserFormid formId) {

        formId.setAddTime(LocalDateTime.now());
        formId.setUpdateTime(LocalDateTime.now());
        return youngUserFormidMapper.insertSelective(formId);
    }
}
