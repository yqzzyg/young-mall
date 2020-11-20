package com.young.mall.service.impl;

import com.young.db.dao.YoungSystemMapper;
import com.young.db.entity.YoungSystem;
import com.young.db.entity.YoungSystemExample;
import com.young.mall.service.AdminSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description: 系统配置
 * @Author: yqz
 * @CreateDate: 2020/11/20 17:01
 */
@Service
public class AdminSystemServiceImpl implements AdminSystemService {

    @Autowired
    private YoungSystemMapper youngSystemMapper;

    @Override
    public Optional<Map<String, String>> listMall() {
        YoungSystemExample example = new YoungSystemExample();
        example.createCriteria().andKeyNameLike("young_mall_%").andDeletedEqualTo(false);
        List<YoungSystem> systemList = youngSystemMapper.selectByExample(example);
        Map<String, String> data = new HashMap<>();
        for (YoungSystem system : systemList) {
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return Optional.ofNullable(data);
    }

    @Override
    public void updateConfig(Map<String, Object> map) {

        for (String key : map.keySet()) {
            YoungSystemExample example = new YoungSystemExample();
            example.createCriteria().andKeyNameEqualTo(key).andDeletedEqualTo(false);

            YoungSystem youngSystem = new YoungSystem();
            youngSystem.setKeyName(key);
            youngSystem.setKeyValue(map.get(key).toString());
            youngSystem.setUpdateTime(LocalDateTime.now());
            int count = youngSystemMapper.updateByExampleSelective(youngSystem, example);
        }
    }

    @Override
    public Optional<Map<String, String>> listExpress() {

        YoungSystemExample example = new YoungSystemExample();
        example.createCriteria().andKeyNameLike("young_express_%").andDeletedEqualTo(false);
        List<YoungSystem> systemList = youngSystemMapper.selectByExample(example);
        Map<String, String> data = new HashMap<>();
        for (YoungSystem system : systemList) {
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return Optional.ofNullable(data);
    }

    @Override
    public Optional<Map<String, String>> listOrder() {
        YoungSystemExample example = new YoungSystemExample();
        example.createCriteria().andKeyNameLike("young_order_%").andDeletedEqualTo(false);
        List<YoungSystem> systemList = youngSystemMapper.selectByExample(example);
        Map<String, String> data = new HashMap<>();
        for(YoungSystem system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return Optional.ofNullable(data);
    }

    @Override
    public Optional<Map<String, String>> listWx() {
        YoungSystemExample example = new YoungSystemExample();
        example.createCriteria().andKeyNameLike("young_wx_%").andDeletedEqualTo(false);
        List<YoungSystem> systemList = youngSystemMapper.selectByExample(example);
        Map<String, String> data = new HashMap<>();
        for (YoungSystem system : systemList) {
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return Optional.ofNullable(data);
    }
}
