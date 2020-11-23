package com.young.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungGoodsMapper;
import com.young.db.entity.YoungGoods;
import com.young.db.entity.YoungGoods.Column;
import com.young.db.entity.YoungGoodsExample;
import com.young.mall.service.ClientGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 客户端商品业务
 * @Author: yqz
 * @CreateDate: 2020/11/22 16:57
 */
@Service
public class ClientGoodsServiceImpl implements ClientGoodsService {

    @Autowired
    private YoungGoodsMapper youngGoodsMapper;

    Column[] columns = new Column[]{Column.id, Column.name, Column.brief, Column.picUrl, Column.isHot, Column.isNew,
            Column.counterPrice, Column.retailPrice};

    @Override
    public List<YoungGoods> queryByNew(int page, int size) {

        YoungGoodsExample example = new YoungGoodsExample();
        example.createCriteria().andIsNewEqualTo(true).andIsOnSaleEqualTo(true)
                .andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(page, size);
        List<YoungGoods> newGoodsList = youngGoodsMapper.selectByExampleSelective(example, columns);
        return newGoodsList;
    }

    @Override
    public List<YoungGoods> queryByHot(int page, int size) {

        YoungGoodsExample example = new YoungGoodsExample();
        example.createCriteria().andIsHotEqualTo(true).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("browse desc");

        PageHelper.startPage(page, size);
        List<YoungGoods> newGoodsList = youngGoodsMapper.selectByExampleSelective(example, columns);
        return newGoodsList;
    }

    @Override
    public List<YoungGoods> getGoodByCategoryId(List<Integer> cid, int page, int size) {
        YoungGoodsExample example = new YoungGoodsExample();
        example.or().andCategoryIdIn(cid).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("sort_order  asc");
        PageHelper.startPage(page, size);
        List<YoungGoods> goodsList = youngGoodsMapper.selectByExampleSelective(example, columns);
        return goodsList;
    }
}
