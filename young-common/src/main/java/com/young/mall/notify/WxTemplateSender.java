
package com.young.mall.notify;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import com.young.db.entity.YoungUserFormid;
import com.young.mall.exception.Asserts;
import com.young.mall.service.MallUserFormIdService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


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
    private MallUserFormIdService userFormIdService;

    /**
     * 发送订阅消息
     *
     * @param subscribeMessage
     * @return
     */
    public void sendSubscribeMsg(WxMaSubscribeMessage subscribeMessage) throws WxErrorException {

        wxMaService.getMsgService().sendSubscribeMsg(subscribeMessage);
    }

    private List<WxMaTemplateData> createMsgData(String[] parms) {
        List<WxMaTemplateData> dataList = new ArrayList<WxMaTemplateData>();
        for (int i = 1; i <= parms.length; i++) {
            dataList.add(new WxMaTemplateData("keyword" + i, parms[i - 1]));
        }
        return dataList;
    }
}
