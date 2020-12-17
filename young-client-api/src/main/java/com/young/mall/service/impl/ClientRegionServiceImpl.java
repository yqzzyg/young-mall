package com.young.mall.service.impl;

import com.young.db.dao.YoungRegionMapper;
import com.young.db.entity.YoungRegion;
import com.young.db.entity.YoungRegionExample;
import com.young.mall.service.ClientRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 行政区域
 * @Author: yqz
 * @CreateDate: 2020/12/17 15:00
 */
@Service
public class ClientRegionServiceImpl implements ClientRegionService {


    @Autowired
    private YoungRegionMapper youngRegionMapper;

    @Override
    public List<YoungRegion> getAll() {
        YoungRegionExample example = new YoungRegionExample();
        byte b = 4;
        example.or().andTypeNotEqualTo(b);
        return youngRegionMapper.selectByExample(example);
    }

    @Override
    public YoungRegion findById(Integer id) {
        return youngRegionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<YoungRegion> queryByPid(Integer parentId) {
        YoungRegionExample example = new YoungRegionExample();
        example.or().andPidEqualTo(parentId);
        return youngRegionMapper.selectByExample(example);
    }
}
