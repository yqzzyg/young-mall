package com.young.mall.service.impl;

import com.young.db.dao.YoungGoodsSpecificationMapper;
import com.young.db.entity.YoungGoodsSpecification;
import com.young.db.entity.YoungGoodsSpecificationExample;
import com.young.mall.domain.vo.ClientGoodsSpecificationVO;
import com.young.mall.service.ClientGoodsSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 商品规格
 * @Author: yqz
 * @CreateDate: 2020/12/2 21:33
 */
@Service
public class ClientGoodsSpecificationServiceImpl implements ClientGoodsSpecificationService {

    @Autowired
    private YoungGoodsSpecificationMapper youngGoodsSpecificationMapper;

    @Override
    public List<ClientGoodsSpecificationVO> getSpecificationVoList(Integer id) {

        List<YoungGoodsSpecification> goodsSpecificationList = queryByGid(id);

        Map<String, ClientGoodsSpecificationVO> map = new HashMap<>();

        //返回的结果集
        List<ClientGoodsSpecificationVO> specificationVoList = new ArrayList<>();

        for (YoungGoodsSpecification goodsSpecification : goodsSpecificationList) {
            //获取商品规格名称
            String specification = goodsSpecification.getSpecification();
            ClientGoodsSpecificationVO goodsSpecificationVO = map.get(specification);
            //如果临时map中没有商品属性，则重新new一个
            if (goodsSpecificationVO == null) {
                goodsSpecificationVO = new ClientGoodsSpecificationVO();
                //设置属性名
                goodsSpecificationVO.setName(specification);

                //商品属性结果集
                List<YoungGoodsSpecification> valueList = new ArrayList<>();
                valueList.add(goodsSpecification);
                //设置属性集
                goodsSpecificationVO.setValueList(valueList);
                map.put(specification, goodsSpecificationVO);

                specificationVoList.add(goodsSpecificationVO);
            } else {
                List<YoungGoodsSpecification> valueList = goodsSpecificationVO.getValueList();
                valueList.add(goodsSpecification);
            }
        }
        return specificationVoList;
    }

    @Override
    public List<YoungGoodsSpecification> queryByGid(Integer id) {
        YoungGoodsSpecificationExample example = new YoungGoodsSpecificationExample();
        example.or().andGoodsIdEqualTo(id).andDeletedEqualTo(false);
        return youngGoodsSpecificationMapper.selectByExample(example);
    }
}
