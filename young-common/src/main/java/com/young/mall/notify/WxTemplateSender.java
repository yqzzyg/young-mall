
package com.young.mall.notify;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import com.young.db.entity.YoungUserFormid;
import com.young.mall.exception.Asserts;
import com.young.mall.service.YoungUserFormIdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @Description: 微信模版消息通知
 * @Author: yqz
 * @CreateDate: 2020/11/2 17:04
 */

public class WxTemplateSender {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxMaService wxMaService;

    @Autowired
    private YoungUserFormIdService userFormIdService;

    /**
     * 发送微信消息(模板消息),不带跳转
     *
     * @param touser    用户 OpenID
     * @param templatId 模板消息ID
     * @param parms     详细内容
     */

    public void sendWechatMsg(String touser, String templatId, String[] parms) {
        sendMsg(touser, templatId, parms, "", "", "");
    }


    /**
     * 发送微信消息(模板消息),带跳转
     *
     * @param touser    用户 OpenID
     * @param templatId 模板消息ID
     * @param parms     详细内容
     * @param page      跳转页面
     */

    public void sendWechatMsg(String touser, String templatId, String[] parms, String page) {
        sendMsg(touser, templatId, parms, page, "", "");
    }

    private void sendMsg(String touser, String templatId, String[] parms, String page, String color,
                         String emphasisKeyword) {
        Optional<YoungUserFormid> optional = userFormIdService.queryByOpenId(touser);
        if (!optional.isPresent()) {
            Asserts.fail("用户 OpenID 查询失败");
        }
        YoungUserFormid userFormid = optional.get();

        if (userFormid == null) {
            return;
        }

        WxMaTemplateMessage msg = new WxMaTemplateMessage();
        msg.setTemplateId(templatId);
        msg.setToUser(touser);
        msg.setFormId(userFormid.getFormid());
        msg.setPage(page);
        msg.setColor(color);
        msg.setEmphasisKeyword(emphasisKeyword);
        msg.setData(createMsgData(parms));
        try {
            wxMaService.getMsgService().sendTemplateMsg(msg);
            if (userFormIdService.updateUserFormId(userFormid).get() == 0) {
                logger.warn("更新数据已失效");
            }
        } catch (Exception e) {
            logger.info("发送消息，更新数据失败:{}", e.getMessage());
        }
    }

    private List<WxMaTemplateData> createMsgData(String[] parms) {
        List<WxMaTemplateData> dataList = new ArrayList<WxMaTemplateData>();
        for (int i = 1; i <= parms.length; i++) {
            dataList.add(new WxMaTemplateData("keyword" + i, parms[i - 1]));
        }
        return dataList;
    }
}
