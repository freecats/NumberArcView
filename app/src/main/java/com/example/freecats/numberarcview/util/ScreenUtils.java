package com.example.freecats.numberarcview.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.example.freecats.numberarcview.BaseApplication;

public class ScreenUtils {

    public static ScreenUtils instance = null;

    public static ScreenUtils getInstance() {
        if (instance == null) {
            synchronized (ScreenUtils.class) {
                if (instance == null) {
                    instance = new ScreenUtils();
                }
            }
        }
        return instance;
    }

    private ScreenUtils() {

    }



    public float dpToPx(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    public int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, BaseApplication.getGlobalContext().getResources().getDisplayMetrics());

    }

}
