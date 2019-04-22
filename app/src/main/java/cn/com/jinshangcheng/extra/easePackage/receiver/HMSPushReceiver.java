/*
 *  * EaseMob CONFIDENTIAL
 * __________________
 * Copyright (C) 2017 EaseMob Technologies. All rights reserved.
 *
 * NOTICE: All information contained herein is, and remains
 * the property of EaseMob Technologies.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from EaseMob Technologies.
 */
package cn.com.jinshangcheng.extra.easePackage.receiver;

import android.content.Context;
import android.os.Bundle;

import com.hyphenate.chat.EMClient;
import com.hyphenate.util.EMLog;

public class HMSPushReceiver {
    private static final String TAG = HMSPushReceiver.class.getSimpleName();

    public void onToken(Context context, String token, Bundle extras){
        //没有失败回调，假定token失败时token为null
        if(token != null && !token.equals("")){
            EMLog.d("HWHMSPush", "register huawei hms push token success token:" + token);
            EMClient.getInstance().sendHMSPushTokenToServer("10492024", token);
        }else{
            EMLog.e("HWHMSPush", "register huawei hms push token fail!");
        }
    }
}
