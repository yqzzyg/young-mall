package com.young.db.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class YoungBrandExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public YoungBrandExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andDescIsNull() {
            addCriterion("desc is null");
            return (Criteria) this;
        }

        public Criteria andDescIsNotNull() {
            addCriterion("desc is not null");
            return (Criteria) this;
        }

        public Criteria andDescEqualTo(String value) {
            addCriterion("desc =", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotEqualTo(String value) {
            addCriterion("desc <>", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThan(String value) {
            addCriterion("desc >", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThanOrEqualTo(String value) {
            addCriterion("desc >=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThan(String value) {
            addCriterion("desc <", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThanOrEqualTo(String value) {
            addCriterion("desc <=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLike(String value) {
            addCriterion("desc like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotLike(String value) {
            addCriterion("desc not like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescIn(List<String> values) {
            addCriterion("desc in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotIn(List<String> values) {
            addCriterion("desc not in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescBetween(String value1, String value2) {
            addCriterion("desc between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotBetween(String value1, String value2) {
            addCriterion("desc not between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andPicUrlIsNull() {
            addCriterion("pic_url is null");
            return (Criteria) this;
        }

        public Criteria andPicUrlIsNotNull() {
            addCriterion("pic_url is not null");
            return (Criteria) this;
        }

        public Criteria andPicUrlEqualTo(String value) {
            addCriterion("pic_url =", value, "picUrl");
            return (Criteria) this;
        }

        public Criteria andPicUrlNotEqualTo(String value) {
            addCriterion("pic_url <>", value, "picUrl");
            return (Criteria) this;
        }

        public Criteria andPicUrlGreaterThan(String value) {
            addCriterion("pic_url >", value, "picUrl");
            return (Criteria) this;
        }

        public Criteria andPicUrlGreaterThanOrEqualTo(String value) {
            addCriterion("pic_url >=", value, "picUrl");
            return (Criteria) this;
        }

        public Criteria andPicUrlLessThan(String value) {
            addCriterion("pic_url <", value, "picUrl");
            return (Criteria) this;
        }

        public Criteria andPicUrlLessThanOrEqualTo(String value) {
            addCriterion("pic_url <=", value, "picUrl");
            return (Criteria) this;
        }

        public Criteria andPicUrlLike(String value) {
            addCriterion("pic_url like", value, "picUrl");
            return (Criteria) this;
        }

        public Criteria andPicUrlNotLike(String value) {
            addCriterion("pic_url not like", value, "picUrl");
            return (Criteria) this;
        }

        public Criteria andPicUrlIn(List<String> values) {
            addCriterion("pic_url in", values, "picUrl");
            return (Criteria) this;
        }

        public Criteria andPicUrlNotIn(List<String> values) {
            addCriterion("pic_url not in", values, "picUrl");
            return (Criteria) this;
        }

        public Criteria andPicUrlBetween(String value1, String value2) {
            addCriterion("pic_url between", value1, value2, "picUrl");
            return (Criteria) this;
        }

        public Criteria andPicUrlNotBetween(String value1, String value2) {
            addCriterion("pic_url not between", value1, value2, "picUrl");
            return (Criteria) this;
        }

        public Criteria andSortOrderIsNull() {
            addCriterion("sort_order is null");
            return (Criteria) this;
        }

        public Criteria andSortOrderIsNotNull() {
            addCriterion("sort_order is not null");
            return (Criteria) this;
        }

        public Criteria andSortOrderEqualTo(Byte value) {
            addCriterion("sort_order =", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotEqualTo(Byte value) {
            addCriterion("sort_order <>", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThan(Byte value) {
            addCriterion("sort_order >", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThanOrEqualTo(Byte value) {
            addCriterion("sort_order >=", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThan(Byte value) {
            addCriterion("sort_order <", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThanOrEqualTo(Byte value) {
            addCriterion("sort_order <=", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderIn(List<Byte> values) {
            addCriterion("sort_order in", values, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotIn(List<Byte> values) {
            addCriterion("sort_order not in", values, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderBetween(Byte value1, Byte value2) {
            addCriterion("sort_order between", value1, value2, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotBetween(Byte value1, Byte value2) {
            addCriterion("sort_order not between", value1, value2, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andFloorPriceIsNull() {
            addCriterion("floor_price is null");
            return (Criteria) this;
        }

        public Criteria andFloorPriceIsNotNull() {
            addCriterion("floor_price is not null");
            return (Criteria) this;
        }

        public Criteria andFloorPriceEqualTo(BigDecimal value) {
            addCriterion("floor_price =", value, "floorPrice");
            return (Criteria) this;
        }

        public Criteria andFloorPriceNotEqualTo(BigDecimal value) {
            addCriterion("floor_price <>", value, "floorPrice");
            return (Criteria) this;
        }

        public Criteria andFloorPriceGreaterThan(BigDecimal value) {
            addCriterion("floor_price >", value, "floorPrice");
            return (Criteria) this;
        }

        public Criteria andFloorPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("floor_price >=", value, "floorPrice");
            return (Criteria) this;
        }

        public Criteria andFloorPriceLessThan(BigDecimal value) {
            addCriterion("floor_price <", value, "floorPrice");
            return (Criteria) this;
        }

        public Criteria andFloorPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("floor_price <=", value, "floorPrice");
            return (Criteria) this;
        }

        public Criteria andFloorPriceIn(List<BigDecimal> values) {
            addCriterion("floor_price in", values, "floorPrice");
            return (Criteria) this;
        }

        public Criteria andFloorPriceNotIn(List<BigDecimal> values) {
            addCriterion("floor_price not in", values, "floorPrice");
            return (Criteria) this;
        }

        public Criteria andFloorPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("floor_price between", value1, value2, "floorPrice");
            return (Criteria) this;
        }

        public Criteria andFloorPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("floor_price not between", value1, value2, "floorPrice");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNull() {
            addCriterion("add_time is null");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNotNull() {
            addCriterion("add_time is not null");
            return (Criteria) this;
        }

        public Criteria andAddTimeEqualTo(Date value) {
            addCriterion("add_time =", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualTo(Date value) {
            addCriterion("add_time <>", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThan(Date value) {
            addCriterion("add_time >", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("add_time >=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThan(Date value) {
            addCriterion("add_time <", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualTo(Date value) {
            addCriterion("add_time <=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeIn(List<Date> values) {
            addCriterion("add_time in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotIn(List<Date> values) {
            addCriterion("add_time not in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeBetween(Date value1, Date value2) {
            addCriterion("add_time between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotBetween(Date value1, Date value2) {
            addCriterion("add_time not between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andShareUrlIsNull() {
            addCriterion("share_url is null");
            return (Criteria) this;
        }

        public Criteria andShareUrlIsNotNull() {
            addCriterion("share_url is not null");
            return (Criteria) this;
        }

        public Criteria andShareUrlEqualTo(String value) {
            addCriterion("share_url =", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlNotEqualTo(String value) {
            addCriterion("share_url <>", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlGreaterThan(String value) {
            addCriterion("share_url >", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlGreaterThanOrEqualTo(String value) {
            addCriterion("share_url >=", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlLessThan(String value) {
            addCriterion("share_url <", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlLessThanOrEqualTo(String value) {
            addCriterion("share_url <=", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlLike(String value) {
            addCriterion("share_url like", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlNotLike(String value) {
            addCriterion("share_url not like", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlIn(List<String> values) {
            addCriterion("share_url in", values, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlNotIn(List<String> values) {
            addCriterion("share_url not in", values, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlBetween(String value1, String value2) {
            addCriterion("share_url between", value1, value2, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlNotBetween(String value1, String value2) {
            addCriterion("share_url not between", value1, value2, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andAdminIdIsNull() {
            addCriterion("admin_id is null");
            return (Criteria) this;
        }

        public Criteria andAdminIdIsNotNull() {
            addCriterion("admin_id is not null");
            return (Criteria) this;
        }

        public Criteria andAdminIdEqualTo(Integer value) {
            addCriterion("admin_id =", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdNotEqualTo(Integer value) {
            addCriterion("admin_id <>", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdGreaterThan(Integer value) {
            addCriterion("admin_id >", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("admin_id >=", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdLessThan(Integer value) {
            addCriterion("admin_id <", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdLessThanOrEqualTo(Integer value) {
            addCriterion("admin_id <=", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdIn(List<Integer> values) {
            addCriterion("admin_id in", values, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdNotIn(List<Integer> values) {
            addCriterion("admin_id not in", values, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdBetween(Integer value1, Integer value2) {
            addCriterion("admin_id between", value1, value2, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdNotBetween(Integer value1, Integer value2) {
            addCriterion("admin_id not between", value1, value2, "adminId");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNull() {
            addCriterion("deleted is null");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNotNull() {
            addCriterion("deleted is not null");
            return (Criteria) this;
        }

        public Criteria andDeletedEqualTo(Boolean value) {
            addCriterion("deleted =", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualTo(Boolean value) {
            addCriterion("deleted <>", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThan(Boolean value) {
            addCriterion("deleted >", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("deleted >=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThan(Boolean value) {
            addCriterion("deleted <", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("deleted <=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedIn(List<Boolean> values) {
            addCriterion("deleted in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotIn(List<Boolean> values) {
            addCriterion("deleted not in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("deleted between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("deleted not between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andCommpanyIsNull() {
            addCriterion("commpany is null");
            return (Criteria) this;
        }

        public Criteria andCommpanyIsNotNull() {
            addCriterion("commpany is not null");
            return (Criteria) this;
        }

        public Criteria andCommpanyEqualTo(String value) {
            addCriterion("commpany =", value, "commpany");
            return (Criteria) this;
        }

        public Criteria andCommpanyNotEqualTo(String value) {
            addCriterion("commpany <>", value, "commpany");
            return (Criteria) this;
        }

        public Criteria andCommpanyGreaterThan(String value) {
            addCriterion("commpany >", value, "commpany");
            return (Criteria) this;
        }

        public Criteria andCommpanyGreaterThanOrEqualTo(String value) {
            addCriterion("commpany >=", value, "commpany");
            return (Criteria) this;
        }

        public Criteria andCommpanyLessThan(String value) {
            addCriterion("commpany <", value, "commpany");
            return (Criteria) this;
        }

        public Criteria andCommpanyLessThanOrEqualTo(String value) {
            addCriterion("commpany <=", value, "commpany");
            return (Criteria) this;
        }

        public Criteria andCommpanyLike(String value) {
            addCriterion("commpany like", value, "commpany");
            return (Criteria) this;
        }

        public Criteria andCommpanyNotLike(String value) {
            addCriterion("commpany not like", value, "commpany");
            return (Criteria) this;
        }

        public Criteria andCommpanyIn(List<String> values) {
            addCriterion("commpany in", values, "commpany");
            return (Criteria) this;
        }

        public Criteria andCommpanyNotIn(List<String> values) {
            addCriterion("commpany not in", values, "commpany");
            return (Criteria) this;
        }

        public Criteria andCommpanyBetween(String value1, String value2) {
            addCriterion("commpany between", value1, value2, "commpany");
            return (Criteria) this;
        }

        public Criteria andCommpanyNotBetween(String value1, String value2) {
            addCriterion("commpany not between", value1, value2, "commpany");
            return (Criteria) this;
        }

        public Criteria andAutoUpdateGoodIsNull() {
            addCriterion("auto_update_good is null");
            return (Criteria) this;
        }

        public Criteria andAutoUpdateGoodIsNotNull() {
            addCriterion("auto_update_good is not null");
            return (Criteria) this;
        }

        public Criteria andAutoUpdateGoodEqualTo(Boolean value) {
            addCriterion("auto_update_good =", value, "autoUpdateGood");
            return (Criteria) this;
        }

        public Criteria andAutoUpdateGoodNotEqualTo(Boolean value) {
            addCriterion("auto_update_good <>", value, "autoUpdateGood");
            return (Criteria) this;
        }

        public Criteria andAutoUpdateGoodGreaterThan(Boolean value) {
            addCriterion("auto_update_good >", value, "autoUpdateGood");
            return (Criteria) this;
        }

        public Criteria andAutoUpdateGoodGreaterThanOrEqualTo(Boolean value) {
            addCriterion("auto_update_good >=", value, "autoUpdateGood");
            return (Criteria) this;
        }

        public Criteria andAutoUpdateGoodLessThan(Boolean value) {
            addCriterion("auto_update_good <", value, "autoUpdateGood");
            return (Criteria) this;
        }

        public Criteria andAutoUpdateGoodLessThanOrEqualTo(Boolean value) {
            addCriterion("auto_update_good <=", value, "autoUpdateGood");
            return (Criteria) this;
        }

        public Criteria andAutoUpdateGoodIn(List<Boolean> values) {
            addCriterion("auto_update_good in", values, "autoUpdateGood");
            return (Criteria) this;
        }

        public Criteria andAutoUpdateGoodNotIn(List<Boolean> values) {
            addCriterion("auto_update_good not in", values, "autoUpdateGood");
            return (Criteria) this;
        }

        public Criteria andAutoUpdateGoodBetween(Boolean value1, Boolean value2) {
            addCriterion("auto_update_good between", value1, value2, "autoUpdateGood");
            return (Criteria) this;
        }

        public Criteria andAutoUpdateGoodNotBetween(Boolean value1, Boolean value2) {
            addCriterion("auto_update_good not between", value1, value2, "autoUpdateGood");
            return (Criteria) this;
        }

        public Criteria andShopUrlIsNull() {
            addCriterion("shop_url is null");
            return (Criteria) this;
        }

        public Criteria andShopUrlIsNotNull() {
            addCriterion("shop_url is not null");
            return (Criteria) this;
        }

        public Criteria andShopUrlEqualTo(String value) {
            addCriterion("shop_url =", value, "shopUrl");
            return (Criteria) this;
        }

        public Criteria andShopUrlNotEqualTo(String value) {
            addCriterion("shop_url <>", value, "shopUrl");
            return (Criteria) this;
        }

        public Criteria andShopUrlGreaterThan(String value) {
            addCriterion("shop_url >", value, "shopUrl");
            return (Criteria) this;
        }

        public Criteria andShopUrlGreaterThanOrEqualTo(String value) {
            addCriterion("shop_url >=", value, "shopUrl");
            return (Criteria) this;
        }

        public Criteria andShopUrlLessThan(String value) {
            addCriterion("shop_url <", value, "shopUrl");
            return (Criteria) this;
        }

        public Criteria andShopUrlLessThanOrEqualTo(String value) {
            addCriterion("shop_url <=", value, "shopUrl");
            return (Criteria) this;
        }

        public Criteria andShopUrlLike(String value) {
            addCriterion("shop_url like", value, "shopUrl");
            return (Criteria) this;
        }

        public Criteria andShopUrlNotLike(String value) {
            addCriterion("shop_url not like", value, "shopUrl");
            return (Criteria) this;
        }

        public Criteria andShopUrlIn(List<String> values) {
            addCriterion("shop_url in", values, "shopUrl");
            return (Criteria) this;
        }

        public Criteria andShopUrlNotIn(List<String> values) {
            addCriterion("shop_url not in", values, "shopUrl");
            return (Criteria) this;
        }

        public Criteria andShopUrlBetween(String value1, String value2) {
            addCriterion("shop_url between", value1, value2, "shopUrl");
            return (Criteria) this;
        }

        public Criteria andShopUrlNotBetween(String value1, String value2) {
            addCriterion("shop_url not between", value1, value2, "shopUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultCategoryIdIsNull() {
            addCriterion("default_category_id is null");
            return (Criteria) this;
        }

        public Criteria andDefaultCategoryIdIsNotNull() {
            addCriterion("default_category_id is not null");
            return (Criteria) this;
        }

        public Criteria andDefaultCategoryIdEqualTo(Integer value) {
            addCriterion("default_category_id =", value, "defaultCategoryId");
            return (Criteria) this;
        }

        public Criteria andDefaultCategoryIdNotEqualTo(Integer value) {
            addCriterion("default_category_id <>", value, "defaultCategoryId");
            return (Criteria) this;
        }

        public Criteria andDefaultCategoryIdGreaterThan(Integer value) {
            addCriterion("default_category_id >", value, "defaultCategoryId");
            return (Criteria) this;
        }

        public Criteria andDefaultCategoryIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("default_category_id >=", value, "defaultCategoryId");
            return (Criteria) this;
        }

        public Criteria andDefaultCategoryIdLessThan(Integer value) {
            addCriterion("default_category_id <", value, "defaultCategoryId");
            return (Criteria) this;
        }

        public Criteria andDefaultCategoryIdLessThanOrEqualTo(Integer value) {
            addCriterion("default_category_id <=", value, "defaultCategoryId");
            return (Criteria) this;
        }

        public Criteria andDefaultCategoryIdIn(List<Integer> values) {
            addCriterion("default_category_id in", values, "defaultCategoryId");
            return (Criteria) this;
        }

        public Criteria andDefaultCategoryIdNotIn(List<Integer> values) {
            addCriterion("default_category_id not in", values, "defaultCategoryId");
            return (Criteria) this;
        }

        public Criteria andDefaultCategoryIdBetween(Integer value1, Integer value2) {
            addCriterion("default_category_id between", value1, value2, "defaultCategoryId");
            return (Criteria) this;
        }

        public Criteria andDefaultCategoryIdNotBetween(Integer value1, Integer value2) {
            addCriterion("default_category_id not between", value1, value2, "defaultCategoryId");
            return (Criteria) this;
        }

        public Criteria andDefaultPagesIsNull() {
            addCriterion("default_pages is null");
            return (Criteria) this;
        }

        public Criteria andDefaultPagesIsNotNull() {
            addCriterion("default_pages is not null");
            return (Criteria) this;
        }

        public Criteria andDefaultPagesEqualTo(Integer value) {
            addCriterion("default_pages =", value, "defaultPages");
            return (Criteria) this;
        }

        public Criteria andDefaultPagesNotEqualTo(Integer value) {
            addCriterion("default_pages <>", value, "defaultPages");
            return (Criteria) this;
        }

        public Criteria andDefaultPagesGreaterThan(Integer value) {
            addCriterion("default_pages >", value, "defaultPages");
            return (Criteria) this;
        }

        public Criteria andDefaultPagesGreaterThanOrEqualTo(Integer value) {
            addCriterion("default_pages >=", value, "defaultPages");
            return (Criteria) this;
        }

        public Criteria andDefaultPagesLessThan(Integer value) {
            addCriterion("default_pages <", value, "defaultPages");
            return (Criteria) this;
        }

        public Criteria andDefaultPagesLessThanOrEqualTo(Integer value) {
            addCriterion("default_pages <=", value, "defaultPages");
            return (Criteria) this;
        }

        public Criteria andDefaultPagesIn(List<Integer> values) {
            addCriterion("default_pages in", values, "defaultPages");
            return (Criteria) this;
        }

        public Criteria andDefaultPagesNotIn(List<Integer> values) {
            addCriterion("default_pages not in", values, "defaultPages");
            return (Criteria) this;
        }

        public Criteria andDefaultPagesBetween(Integer value1, Integer value2) {
            addCriterion("default_pages between", value1, value2, "defaultPages");
            return (Criteria) this;
        }

        public Criteria andDefaultPagesNotBetween(Integer value1, Integer value2) {
            addCriterion("default_pages not between", value1, value2, "defaultPages");
            return (Criteria) this;
        }

        public Criteria andAddPrecentIsNull() {
            addCriterion("add_precent is null");
            return (Criteria) this;
        }

        public Criteria andAddPrecentIsNotNull() {
            addCriterion("add_precent is not null");
            return (Criteria) this;
        }

        public Criteria andAddPrecentEqualTo(Integer value) {
            addCriterion("add_precent =", value, "addPrecent");
            return (Criteria) this;
        }

        public Criteria andAddPrecentNotEqualTo(Integer value) {
            addCriterion("add_precent <>", value, "addPrecent");
            return (Criteria) this;
        }

        public Criteria andAddPrecentGreaterThan(Integer value) {
            addCriterion("add_precent >", value, "addPrecent");
            return (Criteria) this;
        }

        public Criteria andAddPrecentGreaterThanOrEqualTo(Integer value) {
            addCriterion("add_precent >=", value, "addPrecent");
            return (Criteria) this;
        }

        public Criteria andAddPrecentLessThan(Integer value) {
            addCriterion("add_precent <", value, "addPrecent");
            return (Criteria) this;
        }

        public Criteria andAddPrecentLessThanOrEqualTo(Integer value) {
            addCriterion("add_precent <=", value, "addPrecent");
            return (Criteria) this;
        }

        public Criteria andAddPrecentIn(List<Integer> values) {
            addCriterion("add_precent in", values, "addPrecent");
            return (Criteria) this;
        }

        public Criteria andAddPrecentNotIn(List<Integer> values) {
            addCriterion("add_precent not in", values, "addPrecent");
            return (Criteria) this;
        }

        public Criteria andAddPrecentBetween(Integer value1, Integer value2) {
            addCriterion("add_precent between", value1, value2, "addPrecent");
            return (Criteria) this;
        }

        public Criteria andAddPrecentNotBetween(Integer value1, Integer value2) {
            addCriterion("add_precent not between", value1, value2, "addPrecent");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNull() {
            addCriterion("longitude is null");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNotNull() {
            addCriterion("longitude is not null");
            return (Criteria) this;
        }

        public Criteria andLongitudeEqualTo(BigDecimal value) {
            addCriterion("longitude =", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotEqualTo(BigDecimal value) {
            addCriterion("longitude <>", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThan(BigDecimal value) {
            addCriterion("longitude >", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("longitude >=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThan(BigDecimal value) {
            addCriterion("longitude <", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("longitude <=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeIn(List<BigDecimal> values) {
            addCriterion("longitude in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotIn(List<BigDecimal> values) {
            addCriterion("longitude not in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("longitude between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("longitude not between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNull() {
            addCriterion("latitude is null");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNotNull() {
            addCriterion("latitude is not null");
            return (Criteria) this;
        }

        public Criteria andLatitudeEqualTo(BigDecimal value) {
            addCriterion("latitude =", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotEqualTo(BigDecimal value) {
            addCriterion("latitude <>", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThan(BigDecimal value) {
            addCriterion("latitude >", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("latitude >=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThan(BigDecimal value) {
            addCriterion("latitude <", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("latitude <=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIn(List<BigDecimal> values) {
            addCriterion("latitude in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotIn(List<BigDecimal> values) {
            addCriterion("latitude not in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("latitude between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("latitude not between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andFetchTimeRulesIsNull() {
            addCriterion("fetch_time_rules is null");
            return (Criteria) this;
        }

        public Criteria andFetchTimeRulesIsNotNull() {
            addCriterion("fetch_time_rules is not null");
            return (Criteria) this;
        }

        public Criteria andFetchTimeRulesEqualTo(String value) {
            addCriterion("fetch_time_rules =", value, "fetchTimeRules");
            return (Criteria) this;
        }

        public Criteria andFetchTimeRulesNotEqualTo(String value) {
            addCriterion("fetch_time_rules <>", value, "fetchTimeRules");
            return (Criteria) this;
        }

        public Criteria andFetchTimeRulesGreaterThan(String value) {
            addCriterion("fetch_time_rules >", value, "fetchTimeRules");
            return (Criteria) this;
        }

        public Criteria andFetchTimeRulesGreaterThanOrEqualTo(String value) {
            addCriterion("fetch_time_rules >=", value, "fetchTimeRules");
            return (Criteria) this;
        }

        public Criteria andFetchTimeRulesLessThan(String value) {
            addCriterion("fetch_time_rules <", value, "fetchTimeRules");
            return (Criteria) this;
        }

        public Criteria andFetchTimeRulesLessThanOrEqualTo(String value) {
            addCriterion("fetch_time_rules <=", value, "fetchTimeRules");
            return (Criteria) this;
        }

        public Criteria andFetchTimeRulesLike(String value) {
            addCriterion("fetch_time_rules like", value, "fetchTimeRules");
            return (Criteria) this;
        }

        public Criteria andFetchTimeRulesNotLike(String value) {
            addCriterion("fetch_time_rules not like", value, "fetchTimeRules");
            return (Criteria) this;
        }

        public Criteria andFetchTimeRulesIn(List<String> values) {
            addCriterion("fetch_time_rules in", values, "fetchTimeRules");
            return (Criteria) this;
        }

        public Criteria andFetchTimeRulesNotIn(List<String> values) {
            addCriterion("fetch_time_rules not in", values, "fetchTimeRules");
            return (Criteria) this;
        }

        public Criteria andFetchTimeRulesBetween(String value1, String value2) {
            addCriterion("fetch_time_rules between", value1, value2, "fetchTimeRules");
            return (Criteria) this;
        }

        public Criteria andFetchTimeRulesNotBetween(String value1, String value2) {
            addCriterion("fetch_time_rules not between", value1, value2, "fetchTimeRules");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}