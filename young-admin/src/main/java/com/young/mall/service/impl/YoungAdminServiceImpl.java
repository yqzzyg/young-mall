package com.young.mall.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.young.db.dao.YoungAdminMapper;
import com.young.db.entity.YoungAdmin;
import com.young.db.entity.YoungAdminExample;
import com.young.mall.service.YoungAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/10/25 17:16
 */
@Service
class YoungAdminServiceImpl implements YoungAdminService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private YoungAdminMapper adminMapper;

    @Override
    public Optional<YoungAdmin> findAdminByName(String username) {

        YoungAdminExample example = new YoungAdminExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        List<YoungAdmin> youngAdmins = adminMapper.selectByExample(example);
        logger.info("根据用户名，从数据库中查出的用户：{}", JSONUtil.toJsonStr(youngAdmins));
        if (CollectionUtil.isNotEmpty(youngAdmins)){
            YoungAdmin admin = youngAdmins.get(0);
            return Optional.ofNullable(admin);
        }
        return Optional.ofNullable(null);
    }
}
