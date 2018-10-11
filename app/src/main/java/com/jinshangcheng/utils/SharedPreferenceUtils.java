package com.jinshangcheng.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.jinshangcheng.MyApplication;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferenceUtils {

    private static final String SP_NAME = "config";
    private static SharedPreferences sp;
    private static Context mContext;

    /**
     * 获取SP对象
     *
     * @return sp对象
     */
    public static SharedPreferences getSp() {
        mContext = MyApplication.getContext();
        if (sp == null) {
            sp = mContext.getSharedPreferences(SP_NAME, MODE_PRIVATE);
        }
        return sp;
    }


    public static String getStringSP(String key) {
        return getSp().getString(key, "");
    }

    public static void setStringSP(String key, String value) {
        getSp().edit().putString(key, value).apply();
    }

    public static boolean getBooleanSP(String key) {
        return getSp().getBoolean(key, false);
    }

    public static void setBooleanSP(String key, boolean value) {
        getSp().edit().putBoolean(key, value).apply();
    }

    public static long getLongSP(String key) {
        return getSp().getLong(key, 0L);
    }

    public static void setLongSP(String key, long value) {
        getSp().edit().putLong(key, value).apply();
    }


}
