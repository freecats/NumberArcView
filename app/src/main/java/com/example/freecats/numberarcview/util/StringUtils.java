package com.example.freecats.numberarcview.util;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

import java.text.DecimalFormat;

public class StringUtils {

    /**
     * 格式化分数文本，保留一位小数
     *
     * @param num
     * @return
     */
    public static String formatSingleNumberDot(double num) {
        DecimalFormat df = new DecimalFormat("#0.0");
        return df.format(num);

    }

    /**
     * 格式化文本，保留两位数或者三位数
     *
     * @param num
     * @return
     */
    public static String formatNumber(int num) {
        String result = String.valueOf(num);
        if (num >= 0 && num < 100) {
            result = String.format("%02d", num);
        } else if (num >= 100) {
            result = String.format("%03d", num);
        }
        return result;

    }

    /**
     * 获取Html类型的文本信息
     *
     * @param text 显示文本
     * @return spanned
     */
    @SuppressWarnings("deprecation")
    public static Spanned getTextFromHtml(String text) {
        if (Build.VERSION.SDK_INT >= 24) {
            return Html.fromHtml(getSafetyText(text), Html.FROM_HTML_MODE_LEGACY);
        } else {
            return (Html.fromHtml(getSafetyText(text)));
        }
    }

    /**
     * 取得安全非空字符串,避免空指针异常
     * <br>一般用于替代 setText(null == text ? "" ：text) 这样的写法
     *
     * @param text 原字符串
     * @return 如果text为空，那么返回"",否则返回text本身
     */
    public static String getSafetyText(String text) {
        if (TextUtils.isEmpty(text)) return "";
        return text;
    }

}
