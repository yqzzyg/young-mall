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

    private static List<YoungRegion> regions;

    protected List<YoungRegion> getRegions() {
        if (regions == null) {
            createRegion();
        }
        return regions;
    }

    private synchronized void createRegion() {
        if (regions == null) {
            regions = youngRegionService.getAll();
        }
    }

}
