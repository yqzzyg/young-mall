package com.young.mall.express.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/12/21 15:39
 */
@Data
public class Traces implements Serializable {

    @JsonProperty("AcceptStation")
    private String acceptStation;
    @JsonProperty("AcceptTime")
    private String acceptTime;
}
