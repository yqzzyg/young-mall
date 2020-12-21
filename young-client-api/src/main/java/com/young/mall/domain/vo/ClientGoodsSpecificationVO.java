package com.young.mall.domain.vo;

import com.young.db.entity.YoungGoodsSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/12/2 21:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientGoodsSpecificationVO {
    private String name;
    private List<YoungGoodsSpecification> valueList;
}
