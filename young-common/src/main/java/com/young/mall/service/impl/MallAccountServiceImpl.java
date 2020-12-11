package com.young.mall.service.impl;

import com.young.db.dao.YoungUserAccountMapper;
import com.young.db.entity.YoungUserAccount;
import com.young.db.entity.YoungUserAccountExample;
import com.young.mall.exception.Asserts;
import com.young.mall.service.MallAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 账户业务
 * @Author: yqz
 * @CreateDate: 2020/12/11 11:01
 */
@Service
public class MallAccountServiceImpl implements MallAccountService {

    @Autowired
    private YoungUserAccountMapper youngUserAccountMapper;


    @Override
    public YoungUserAccount findShareUserAccountByUserId(Integer shareUserId) {

        YoungUserAccountExample example = new YoungUserAccountExample();
        example.or().andUserIdEqualTo(shareUserId);

        List<YoungUserAccount> accountList = youngUserAccountMapper.selectByExample(example);

        if (accountList.size() == 0) {
            return null;
        }
   /*     if (accountList.size() > 1) {
            Asserts.fail("该用户存在多个账户");
        }*/
        return accountList.get(0);
    }
}
