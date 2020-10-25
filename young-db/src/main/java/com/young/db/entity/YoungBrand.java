package com.young.db.entity;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class YoungBrand implements Serializable {
    private Integer id;

    @ApiModelProperty(value = "品牌商名称")
    private String name;

    @ApiModelProperty(value = "品牌商简介")
    private String desc;

    @ApiModelProperty(value = "品牌商页的品牌商图片")
    private String picUrl;

    private Byte sortOrder;

    @ApiModelProperty(value = "品牌商的商品低价，仅用于页面展示")
    private BigDecimal floorPrice;

    @ApiModelProperty(value = "创建时间")
    private Date addTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "分享二维码图片")
    private String shareUrl;

    @ApiModelProperty(value = "管理用户id")
    private Integer adminId;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean deleted;

    private String commpany;

    @ApiModelProperty(value = "自动监控更新商品")
    private Boolean autoUpdateGood;

    @ApiModelProperty(value = "店铺url地址")
    private String shopUrl;

    @ApiModelProperty(value = "默认的商品类别id")
    private Integer defaultCategoryId;

    @ApiModelProperty(value = "默认商品页面数")
    private Integer defaultPages;

    @ApiModelProperty(value = "店铺商品溢价")
    private Integer addPrecent;

    @ApiModelProperty(value = "提货地址")
    private String address;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "提货时间配置")
    private String fetchTimeRules;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Byte getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Byte sortOrder) {
        this.sortOrder = sortOrder;
    }

    public BigDecimal getFloorPrice() {
        return floorPrice;
    }

    public void setFloorPrice(BigDecimal floorPrice) {
        this.floorPrice = floorPrice;
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

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getCommpany() {
        return commpany;
    }

    public void setCommpany(String commpany) {
        this.commpany = commpany;
    }

    public Boolean getAutoUpdateGood() {
        return autoUpdateGood;
    }

    public void setAutoUpdateGood(Boolean autoUpdateGood) {
        this.autoUpdateGood = autoUpdateGood;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public Integer getDefaultCategoryId() {
        return defaultCategoryId;
    }

    public void setDefaultCategoryId(Integer defaultCategoryId) {
        this.defaultCategoryId = defaultCategoryId;
    }

    public Integer getDefaultPages() {
        return defaultPages;
    }

    public void setDefaultPages(Integer defaultPages) {
        this.defaultPages = defaultPages;
    }

    public Integer getAddPrecent() {
        return addPrecent;
    }

    public void setAddPrecent(Integer addPrecent) {
        this.addPrecent = addPrecent;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getFetchTimeRules() {
        return fetchTimeRules;
    }

    public void setFetchTimeRules(String fetchTimeRules) {
        this.fetchTimeRules = fetchTimeRules;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", desc=").append(desc);
        sb.append(", picUrl=").append(picUrl);
        sb.append(", sortOrder=").append(sortOrder);
        sb.append(", floorPrice=").append(floorPrice);
        sb.append(", addTime=").append(addTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", shareUrl=").append(shareUrl);
        sb.append(", adminId=").append(adminId);
        sb.append(", deleted=").append(deleted);
        sb.append(", commpany=").append(commpany);
        sb.append(", autoUpdateGood=").append(autoUpdateGood);
        sb.append(", shopUrl=").append(shopUrl);
        sb.append(", defaultCategoryId=").append(defaultCategoryId);
        sb.append(", defaultPages=").append(defaultPages);
        sb.append(", addPrecent=").append(addPrecent);
        sb.append(", address=").append(address);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", fetchTimeRules=").append(fetchTimeRules);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}