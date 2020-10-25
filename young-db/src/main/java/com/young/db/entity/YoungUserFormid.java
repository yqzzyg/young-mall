package com.young.db.entity;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class YoungUserFormid implements Serializable {
    private Integer id;

    @ApiModelProperty(value = "缓存的FormId")
    private String formid;

    @ApiModelProperty(value = "是FormId还是prepayId")
    private Boolean isprepay;

    @ApiModelProperty(value = "可用次数，fromId为1，prepay为3，用1次减1")
    private Integer useamount;

    @ApiModelProperty(value = "过期时间，腾讯规定为7天")
    private Date expireTime;

    @ApiModelProperty(value = "微信登录openid")
    private String openid;

    @ApiModelProperty(value = "创建时间")
    private Date addTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean deleted;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid;
    }

    public Boolean getIsprepay() {
        return isprepay;
    }

    public void setIsprepay(Boolean isprepay) {
        this.isprepay = isprepay;
    }

    public Integer getUseamount() {
        return useamount;
    }

    public void setUseamount(Integer useamount) {
        this.useamount = useamount;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", formid=").append(formid);
        sb.append(", isprepay=").append(isprepay);
        sb.append(", useamount=").append(useamount);
        sb.append(", expireTime=").append(expireTime);
        sb.append(", openid=").append(openid);
        sb.append(", addTime=").append(addTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleted=").append(deleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}