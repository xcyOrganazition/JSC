package cn.com.jinshangcheng.utils;

import java.text.DecimalFormat;

public class NumberUtils {
    public static String formatDouble(double d) {
        DecimalFormat df = new DecimalFormat("#.##");
        String s = df.format(d);
        return s;
    }
    public static String formatDouble(long d) {
        DecimalFormat df = new DecimalFormat("#.##");
        String s = df.format(d);
        return s;
    }
    public static String formatDouble(String str) {
        Double d = Double.parseDouble(str);
        com.orhanobut.logger.Logger.w("Double"+d);
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(d);
    }

    /**
     * 计算平均油耗
     * @param fuel 总油耗
     * @param mile 总里程
     * @return 平均油耗
     */
    public static String getOilAvg(String fuel,String mile){
        try {
            Double fuelD = Double.parseDouble(fuel);
            Double mileD = Double.parseDouble(mile);
            if (mileD==0) {
                return "暂无";
            }
            return formatDouble(Math.round(fuelD*10000D / mileD )/ 100D);
        } catch (Exception e) {
            return "暂无";
        }
    }
}
