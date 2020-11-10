package com.young.db.pojo;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/10 9:23
 */
@Data
public class CatAndBrand {
    private Integer value;
    private String label;
    private List<CatAndBrand> chilrd;
}
