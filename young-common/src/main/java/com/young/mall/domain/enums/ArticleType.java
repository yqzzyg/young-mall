package com.young.mall.domain.enums;

import cn.hutool.core.util.StrUtil;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/14 12:32
 */
public enum ArticleType {
    NOTICE("0", "通知"),
    ANNOUNCE("1", "公告");

    private final String type;
    private final String desc;
    ArticleType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static ArticleType getInstance(String type) {
        if (StrUtil.isNotBlank(type)) {
            for (ArticleType tmp : ArticleType.values()) {
                if (type.equals(tmp.type)) {
                    return tmp;
                }
            }
        }
        return null;
    }

    public String type() {
        return type;
    }

    public String desc() {
        return desc;
    }
}
