package com.young.mall.domain.dto;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/9 22:08
 */
@Data
public class CatAndBrandVo {
    private Integer value;
    private String label;
    private List children;
}
