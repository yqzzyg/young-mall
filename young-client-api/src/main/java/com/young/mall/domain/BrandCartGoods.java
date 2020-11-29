package com.young.mall.domain;

import com.young.db.entity.YoungBrand;
import com.young.db.entity.YoungCart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 用于存储 品牌入驻商购物车商品的对象
 * @Author: yqz
 * @CreateDate: 2020/11/28 17:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandCartGoods implements Serializable {
    private static final long serialVersionUID = -7908381028314100456L;

    private static final Integer DEFAULT_BRAND_ID = 1001000;

    private static final String DEFAULT_BRAND_COMMPANY = "young-mall";

    private static final String DEFAULT_BRAND_NAME = "young-mall";

    private Integer brandId;

    private String brandName;

    private String brandCommpany;

    private List<YoungCart> cartList;

    private BigDecimal bandGoodsTotalPrice;

    private BigDecimal bandFreightPrice;

    public static BrandCartGoods init(YoungBrand brand) {
        BrandCartGoods bcg = new BrandCartGoods();
        if (brand != null) {
            bcg.setBrandId(brand.getId());
            bcg.setBrandCommpany(brand.getCommpany());
            bcg.setBrandName(brand.getName());
        } else {
            bcg.setBrandId(DEFAULT_BRAND_ID);
            bcg.setBrandCommpany(DEFAULT_BRAND_COMMPANY);
            bcg.setBrandName(DEFAULT_BRAND_NAME);
        }
        List<YoungCart> bartList = new ArrayList<YoungCart>();
        bcg.setCartList(bartList);
        return bcg;
    }
}
