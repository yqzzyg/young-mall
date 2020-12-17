package com.young.mall.service.impl;

import com.young.db.entity.YoungRegion;
import com.young.mall.service.ClientRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/12/17 15:02
 */
@Service
public class GetRegionService {

    @Autowired
    private ClientRegionService youngRegionService;

    private static List<YoungRegion> DtsRegions;

    protected List<YoungRegion> getRegions() {
        if (DtsRegions == null) {
            createRegion();
        }
        return DtsRegions;
    }

    private synchronized void createRegion() {
        if (DtsRegions == null) {
            DtsRegions = youngRegionService.getAll();
        }
    }

}
