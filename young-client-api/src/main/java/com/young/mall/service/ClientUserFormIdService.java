package com.young.mall.service;

import com.young.db.entity.YoungUserFormid;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/12/29 14:46
 */
public interface ClientUserFormIdService {

    /**
     * 添加一个 FormId
     *
     * @param formId
     * @return
     */
    Integer addUserFormId(YoungUserFormid formId);
}
