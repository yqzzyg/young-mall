package com.young.mall.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/18 22:09
 */
@Data
public class StatVo {
    private String[] columns = new String[0];
    private List<Map> rows = new ArrayList<>();

    public String[] getColumns() {
        return columns;
    }
}
