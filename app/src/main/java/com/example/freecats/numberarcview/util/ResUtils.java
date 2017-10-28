package com.example.freecats.numberarcview.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.example.freecats.numberarcview.BaseApplication;


/**
 * 资源获取工具类
 * <br>Created by wbj on 2016/7/7.
 */
public class ResUtils {

    private static Context mContext = null;

    public static ResUtils instance = null;

    public static ResUtils getInstance() {
        if (instance == null) {
            synchronized (ResUtils.class) {
                if (instance == null) {
                    instance = new ResUtils();
                    mContext = BaseApplication.getGlobalContext();
                }
            }
        }
        return instance;
    }

    public int getColor(int res) {
        return ContextCompat.getColor(mContext, res);
    }

}
