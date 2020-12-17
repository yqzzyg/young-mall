package com.young.mall.service.impl;

import com.young.db.dao.YoungAddressMapper;
import com.young.db.entity.YoungAddress;
import com.young.db.entity.YoungAddressExample;
import com.young.db.entity.YoungRegion;
import com.young.mall.common.ResBean;
import com.young.mall.service.ClientAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Description: 收货地址service
 * @Author: yqz
 * @CreateDate: 2020/12/16 14:51
 */
@Service
public class ClientAddressServiceImpl implements ClientAddressService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private YoungAddressMapper youngAddressMapper;

    @Autowired
    private GetRegionService regionService;


    private final static ArrayBlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(6);

    private final static RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(3, 6, 1000, TimeUnit.MILLISECONDS,
            WORK_QUEUE, HANDLER);

    @Override
    public YoungAddress findDefault(Integer userId) {
        YoungAddressExample example = new YoungAddressExample();
        example.or().andUserIdEqualTo(userId)
                .andIsDefaultEqualTo(true)
                .andDeletedEqualTo(false);
        return youngAddressMapper.selectOneByExample(example);
    }

    @Override
    public YoungAddress findById(Integer id) {
        return youngAddressMapper.selectByPrimaryKey(id);
    }

    @Override
    public ResBean list(Integer userId) {

        List<YoungAddress> addressList = this.queryByUid(userId);

        List<Map<String, Object>> addressVoList = new ArrayList<>(addressList.size());
        List<YoungRegion> regionList = regionService.getRegions();

        for (YoungAddress address : addressList) {
            Map<String, Object> addressVo = new HashMap<>();
            addressVo.put("id", address.getId());
            addressVo.put("name", address.getName());
            addressVo.put("mobile", address.getMobile());
            addressVo.put("isDefault", address.getIsDefault());

            Callable<String> provinceCallable = () -> regionList.stream()
                    .filter(region -> region.getId().equals(address.getProvinceId())).findAny().orElse(null)
                    .getName();
            Callable<String> cityCallable = () -> regionList.stream()
                    .filter(region -> region.getId().equals(address.getCityId())).findAny().orElse(null).getName();
            Callable<String> areaCallable = () -> regionList.stream()
                    .filter(region -> region.getId().equals(address.getAreaId())).findAny().orElse(null).getName();
            FutureTask<String> provinceNameCallableTask = new FutureTask<>(provinceCallable);
            FutureTask<String> cityNameCallableTask = new FutureTask<>(cityCallable);
            FutureTask<String> areaNameCallableTask = new FutureTask<>(areaCallable);
            executorService.submit(provinceNameCallableTask);
            executorService.submit(cityNameCallableTask);
            executorService.submit(areaNameCallableTask);

            String detailedAddress = "";
            try {
                String province = provinceNameCallableTask.get();
                String city = cityNameCallableTask.get();
                String area = areaNameCallableTask.get();
                String addr = address.getAddress();
                detailedAddress = province + city + area + " " + addr;
            } catch (Exception e) {
                logger.error("【行政区域获取出错】获取收货地址列表错误！关键参数：{}", userId);
                e.printStackTrace();
            }
            addressVo.put("detailedAddress", detailedAddress);

            addressVoList.add(addressVo);
        }


        return ResBean.success(addressVoList);
    }


    @Override
    public List<YoungAddress> queryByUid(Integer userId) {
        YoungAddressExample example = new YoungAddressExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return youngAddressMapper.selectByExample(example);
    }

    @Override
    public Integer resetDefault(Integer userId) {
        YoungAddress address = new YoungAddress();
        address.setIsDefault(false);
        address.setUpdateTime(LocalDateTime.now());
        YoungAddressExample example = new YoungAddressExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false).andIsDefaultEqualTo(true);
        return youngAddressMapper.updateByExampleSelective(address, example);
    }

    @Override
    public Integer add(YoungAddress address) {
        address.setAddTime(LocalDateTime.now());
        address.setUpdateTime(LocalDateTime.now());
        return youngAddressMapper.insertSelective(address);
    }

    @Override
    public Integer update(YoungAddress address) {
        address.setUpdateTime(LocalDateTime.now());
        return youngAddressMapper.updateByPrimaryKeySelective(address);
    }

    @Override
    public Integer delete(Integer id) {

        return youngAddressMapper.logicalDeleteByPrimaryKey(id);
    }
}
