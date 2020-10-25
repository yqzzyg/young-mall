package com.young.db.entity;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class YoungRegion implements Serializable {
    private Integer id;

    @ApiModelProperty(value = "行政区域父ID，例如区县的pid指向市，市的pid指向省，省的pid则是0")
    private Integer pid;

    @ApiModelProperty(value = "行政区域名称")
    private String name;

    @ApiModelProperty(value = "行政区域类型，如如1则是省， 如果是2则是市，如果是3则是区县")
    private Byte type;

    @ApiModelProperty(value = "行政区域编码")
    private Integer code;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pid=").append(pid);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", code=").append(code);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}