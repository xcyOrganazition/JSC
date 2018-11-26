package cn.com.jinshangcheng.utils;

import java.text.DecimalFormat;

public class NumberUtils {
    public static String formatDouble(double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(d);
    }
    public static String formatDouble(String str) {
        Double d = Double.parseDouble(str);
        com.orhanobut.logger.Logger.w("Double"+d);
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(d);
    }
}
