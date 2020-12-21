package com.young.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungBrandMapper;
import com.young.db.entity.YoungBrand;
import com.young.db.entity.YoungBrand.Column;
import com.young.db.entity.YoungBrandExample;
import com.young.mall.service.ClientBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 品牌
 * @Author: yqz
 * @CreateDate: 2020/11/22 17:33
 */
@Service
public class ClientBrandServiceImpl implements ClientBrandService {

    @Autowired
    private YoungBrandMapper youngBrandMapper;
    private Column[] columns = new Column[]{Column.id, Column.name, Column.desc, Column.picUrl, Column.floorPrice};

    @Override
    public List<YoungBrand> queryBrand(int page, int size) {

        YoungBrandExample example = new YoungBrandExample();
        example.createCriteria().andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(page, size);
        List<YoungBrand> brandList = youngBrandMapper.selectByExampleSelective(example, columns);
        return brandList;
    }

    @Override
    public YoungBrand findById(Integer id) {
        return youngBrandMapper.selectByPrimaryKey(id);
    }
}
