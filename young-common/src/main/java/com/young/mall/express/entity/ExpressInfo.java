package com.young.mall.express.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/12/21 15:39
 */
@Data
public class ExpressInfo implements Serializable {
    @JsonProperty("LogisticCode")
    private String LogisticCode;
    @JsonProperty("ShipperCode")
    private String ShipperCode;
    @JsonProperty("Traces")
    private List<Traces> Traces;
    @JsonProperty("State")
    private String State;
    @JsonProperty("EBusinessID")
    private String EBusinessID;
    @JsonProperty("Success")
    private boolean Success;
    @JsonProperty("Reason")
    private String Reason;

    private String ShipperName;
}
