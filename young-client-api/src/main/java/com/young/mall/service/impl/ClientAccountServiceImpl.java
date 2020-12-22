package com.young.mall.service.impl;

import com.young.db.dao.YoungUserAccountMapper;
import com.young.db.entity.YoungUserAccount;
import com.young.db.entity.YoungUserAccountExample;
import com.young.mall.service.ClientAccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 用户账户业务
 * @Author: yqz
 * @CreateDate: 2020/12/22 12:15
 */
@Service
public class ClientAccountServiceImpl implements ClientAccountService {


    @Resource
    private YoungUserAccountMapper youngUserAccountMapper;

    @Override
    public YoungUserAccount findShareUserAccountByUserId(Integer shareUserId) {

        YoungUserAccountExample example = new YoungUserAccountExample();
        example.or().andUserIdEqualTo(shareUserId);
        List<YoungUserAccount> accounts = youngUserAccountMapper.selectByExample(example);
        if (accounts.size() == 0) {
            return null;
        }
        return accounts.get(0);
    }
}
