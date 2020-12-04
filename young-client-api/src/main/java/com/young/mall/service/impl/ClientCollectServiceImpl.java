package com.young.mall.service.impl;

import com.young.db.dao.YoungCollectMapper;
import com.young.db.entity.YoungCollectExample;
import com.young.mall.service.ClientCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 用户收藏
 * @Author: yqz
 * @CreateDate: 2020/12/3 17:41
 */
@Service
public class ClientCollectServiceImpl implements ClientCollectService {

    @Autowired
    private YoungCollectMapper youngCollectMapper;

    @Override
    public int count(Integer uid, Integer gid) {
        YoungCollectExample example = new YoungCollectExample();
        example.or().andUserIdEqualTo(uid).andValueIdEqualTo(gid).andDeletedEqualTo(false);
        return (int) youngCollectMapper.countByExample(example);
    }
}
