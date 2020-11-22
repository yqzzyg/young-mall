package com.young.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungGoodsMapper;
import com.young.db.dao.YoungGrouponRulesMapper;
import com.young.db.entity.YoungGoods;
import com.young.db.entity.YoungGoods.Column;
import com.young.db.entity.YoungGoodsExample;
import com.young.db.entity.YoungGrouponRules;
import com.young.db.entity.YoungGrouponRulesExample;
import com.young.mall.service.ClientGrouponRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 团购规则
 * @Author: yqz
 * @CreateDate: 2020/11/22 17:54
 */
@Service
public class ClientGrouponRulesServiceImpl implements ClientGrouponRulesService {

    @Autowired
    private YoungGrouponRulesMapper grouponRulesMapper;

    @Autowired
    private YoungGoodsMapper goodsMapper;

    private Column[] goodsColumns = new Column[]{Column.id, Column.name,
            Column.brief, Column.picUrl, Column.counterPrice, Column.retailPrice};


    @Override
    public List<Map<String, Object>> queryList(int page, int size) {
        return queryList(page, size, "add_time", "desc");
    }

    @Override
    public List<Map<String, Object>> queryList(int page, int size, String sort, String order) {
        YoungGrouponRulesExample example = new YoungGrouponRulesExample();
        example.or().andDeletedEqualTo(false);
        example.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page, size);
        List<YoungGrouponRules> grouponRules = grouponRulesMapper.selectByExample(example);

        List<Map<String, Object>> grouponList = new ArrayList<>(grouponRules.size());
        for (YoungGrouponRules rule : grouponRules) {
            String goodsSn = rule.getGoodsSn();

            YoungGoodsExample goodsExample = new YoungGoodsExample();
            goodsExample.createCriteria().andGoodsSnEqualTo(goodsSn).andDeletedEqualTo(false);

            List<YoungGoods> youngGoodsList = goodsMapper.selectByExampleSelective(goodsExample, goodsColumns);
            YoungGoods youngGoods = youngGoodsList.get(0);
            if (youngGoods == null) {
                continue;
            }
            Map<String, Object> item = new HashMap<>();
            item.put("goods", youngGoods);
            item.put("groupon_price", youngGoods.getRetailPrice().subtract(rule.getDiscount()));
            item.put("groupon_member", rule.getDiscountMember());
            grouponList.add(item);
        }
        return grouponList;
    }
}
