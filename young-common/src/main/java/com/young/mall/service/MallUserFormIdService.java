package com.young.mall.service;

import com.young.db.entity.YoungUserFormid;

import java.util.Optional;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/2 17:08
 */
public interface MallUserFormIdService {


    /**
     * 查找是否有可用的FromId
     * @param openId
     * @return
     */
    Optional<YoungUserFormid> queryByOpenId(String openId);

    /**
     * 更新或删除FormId
     * @param userFormid
     * @return
     */
    Optional<Integer> updateUserFormId(YoungUserFormid userFormid);
}
