package cn.com.jinshangcheng.utils;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DateUtils {
    public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
    public static SimpleDateFormat df2 = new SimpleDateFormat("yyyy.MM.dd");// 设置日期格式
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
    public static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat sdf3 = new SimpleDateFormat("yy-MM-dd HH:mm");
    public static SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    public static SimpleDateFormat sdf5 = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy");


    public static String getMondayOfThisWeek() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return df.format(cal.getTime());
    }

    public static String getFirstOfThisMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        String first = df.format(c.getTime());
        return first;
    }

    public static String getFirstOfThisYear() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return df.format(getYearFirst(currentYear));
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 比较两个日期之间的大小
     *
     * @param d1
     * @param d2
     * @return 前者大于等于后者返回true 反之false
     */
    public static boolean compareHaveDate(Date d1, Date d2) {
        int result = d1.compareTo(d2);
        if (result >= 0)
            return true;
        else
            return false;
    }

    /**
     * 比较两个日期相差的天数
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int compareTwoDate(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);

        int day1 = c1.get(Calendar.DAY_OF_YEAR);
        int day2 = c2.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;
    }

    /**
     * 比较两个日期相差
     */
    public static long compareTwoTime(Date d1, Date d2) {
        long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
        return diff;
    }

    /**
     * 比较两个日期是否相差指定小时 前者减去后者大于time ,返回true ,反之false
     */
    public static boolean IsMinusDate(Date d1, Date d2, long time) {
        long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
        long hours = diff / (1000 * 60 * 60);
        if (hours < time) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 两个日期差 duration : 指定每天的小时
     */
    public static String MinusDate(Date d1, Date d2) {
        String str = "";
        long diff = d2.getTime() - d1.getTime();// 这样得到的差值是微秒级别
        long hours = diff / (1000 * 60 * 60);
        str = hours + "小时";
        return str;
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDate(SimpleDateFormat format) {
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = format.format(curDate);
        return str;
    }

    /**
     * 获取指定日期
     * 比如： 昨天传 -1 ，明天传1
     */
    public static String getSpecificDate(int value) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, value);
        String day = df.format(cal.getTime());
        return day;
    }

    /**
     * 获取指定年份
     */
    public static String getSpecificYear(int value) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, value);
        String day = df.format(cal.getTime());
        return day;
    }

    /**
     * 获取当前时间
     * HH:MM
     */
    public static String getCurrentTime() {
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = sdf5.format(curDate);
        return str;
    }

    /******************************* 时间滚轮 *****************************************/
    /**
     * 出生日期
     */
    static Calendar calendar = Calendar.getInstance();
    public static ArrayList<String> list_birth_dateyear = new ArrayList<String>();
    public static String[] str_birth_datemonth_big = {"01", "03", "05", "07", "08", "10", "12"};
    public static String[] str_birth_datemonth_little = {"04", "06", "09", "11"};
    public static String[] str_birth_datemonth = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};


    // 年份
    public static List<String> getAfterYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String[] str_after_dateyear = new String[10];
        for (int i = 0; i < 10; i++) {
            str_after_dateyear[i] = (year + i) + "";
        }
        return Arrays.asList(str_after_dateyear);
    }

    public static List<String> getBirthMonthBig() {
        return Arrays.asList(str_birth_datemonth_big);
    }

    public static List<String> getBirthMonthLittle() {
        return Arrays.asList(str_birth_datemonth_little);
    }

    public static List<String> getBirthMonth() {
        return Arrays.asList(str_birth_datemonth);
    }

    public static List<String> getBirthDay() {
        return Arrays.asList(getCountDayOfMonth(String.valueOf(calendar.get(Calendar.YEAR)), String.valueOf(calendar.get(Calendar.MONTH) + 1)));
    }

    // 小时
    public static List<String> getHour() {
        String[] str_hour = new String[23 - 0 + 1];
        for (int i = 0; i <= 23; i++) {
            if (i < 10) {
                str_hour[i] = "0" + i;
            } else {
                str_hour[i] = "" + i;
            }
        }
        return Arrays.asList(str_hour);
    }

    // 分钟数
    public static List<String> getMinute() {
        String[] str_minute = new String[59 - 0 + 1];
        for (int i = 0; i <= 59; i++) {
            if (i < 10) {
                str_minute[i] = "0" + i;
            } else {
                str_minute[i] = "" + i;
            }
        }
        return Arrays.asList(str_minute);
    }

    /**
     * 获取月份内的日期数
     *
     * @param strYear
     * @param month
     * @return
     */
    public static String[] getCountDayOfMonth(String strYear, String month) {

        Logger.i("month==" + month);
        if (month.length() == 1) {
            month = "0" + month;
        }
        int year = Integer.valueOf(strYear);
        String[] days = null;
        // 判断月份
        if (getBirthMonthBig().contains(month)) { // 大月
            days = new String[31];
            for (int day = 0; day < 31; day++) {
                days[day] = String.valueOf(day + 1);
            }
        } else if (getBirthMonthLittle().contains(month)) { // 小月
            days = new String[30];
            for (int day = 0; day < 30; day++) {
                days[day] = String.valueOf(day + 1);
            }
        } else {// 2月份
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) // 闰月
            {
                days = new String[29];
                for (int day = 0; day < 29; day++) {
                    days[day] = String.valueOf(day + 1);
                }
            } else {
                days = new String[28];
                for (int day = 0; day < 28; day++) {
                    days[day] = String.valueOf(day + 1);
                }
            }

        }

        Logger.i("days.length==" + days.length);
        for (int i = 0; i < days.length; i++) {
            if (days[i].length() == 1) {
                days[i] = "0" + days[i];
            }
        }
        return days;
    }

    /**
     * 将周的数字转换为汉字一、二等
     *
     * @param weekDay
     * @return
     */
    public static String getChinese(int weekDay) {
        String chinese = "";
        switch (weekDay) {
            case Calendar.MONDAY:
                chinese = "星期一";
                break;
            case Calendar.TUESDAY:
                chinese = "星期二";
                break;
            case Calendar.WEDNESDAY:
                chinese = "星期三";
                break;
            case Calendar.THURSDAY:
                chinese = "星期四";
                break;
            case Calendar.FRIDAY:
                chinese = "星期五";
                break;
            case Calendar.SATURDAY:
                chinese = "星期六";
                break;
            case Calendar.SUNDAY:
                chinese = "星期日";
                break;
            default:
                break;
        }

        return chinese;

    }


    public static String getWeekChinese(int week) {
        String strWeek = "";
        switch (week) {
            case 1:
                strWeek = "一";
                break;
            case 2:
                strWeek = "二";
                break;
            case 3:
                strWeek = "三";
                break;
            case 4:
                strWeek = "四";
                break;
            case 5:
                strWeek = "五";
                break;
            case 6:
                strWeek = "六";
                break;
            case 7:
                strWeek = "日";
                break;
            default:
                break;
        }
        return strWeek;
    }

    // 1-30
    public static List<String> getDay() {
        String[] str_day = new String[30 - 1 + 1];
        for (int i = 1; i <= 30; i++) {
            str_day[i - 1] = i + "";
        }
        return Arrays.asList(str_day);
    }

    /**
     * 判断两个date是否是同一天
     *
     * @param date1
     * @param Date2
     * @return
     */
    public static boolean inSameDay(Date date1, Date Date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int year1 = calendar.get(Calendar.YEAR);
        int day1 = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTime(Date2);
        int year2 = calendar.get(Calendar.YEAR);
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);

        if ((year1 == year2) && (day1 == day2)) {
            return true;
        }
        return false;
    }

    /**
     * 获取 HH:mm 格式的时间
     *
     * @param time
     * @return
     */

    public static String getHMTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        } else {
            Date date = null;
            String result = "";
            try {
                date = sdf.parse(time);
                result = sdf5.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    /**
     * 获取 MM-dd HH:mm 格式的时间
     *
     * @param time
     * @return
     */

    public static String getMMddHMTime(long time) {
        String result ;
        Date date = new Date(time);
        sdf4.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        result = sdf4.format(date);
        return result;

    }

    /**
     * 获取 yyyy-MM-dd 格式的时间
     *
     * @param time
     * @return
     */

    public static String getYMDTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        } else {
            Date date = null;
            String result = "";
            try {
                date = sdf.parse(time);
                result = df.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    /**
     * 获取 yyyy-MM-dd 格式的时间
     *
     * @param time
     * @return
     */

    public static String getYMDTime(long time) {
        Date date = new Date(time);
        df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String result = df.format(date);
        if (time == 0) {
            result = "";
        }
        return result;

    }

    /**
     * 获取 yyyy-MM 格式的时间
     *
     * @param time
     * @return
     */

    public static String getYMTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 设置日期格式
        Date date = new Date(time);
        String result = sdf.format(date);
        if (time == 0) {
            result = "";
        }
        return result;

    }


    /**
     * 获取  yyyy-MM-dd HH:mm 格式的时间
     * 将yyyy-MM-dd HH:mm:ss 格式的 日期转化成
     * yyyy-MM-dd HH:mm
     */
    public static String getYMDHMTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        } else {
            Date date = null;
            String result = "";
            try {
                date = sdf.parse(time);
                result = sdf2.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    /**
     * 获取  yyyy-MM-dd HH:mm 格式的时间
     * 将yyyy-MM-dd HH:mm:ss 格式的 日期转化成
     * yyyy-MM-dd HH:mm
     */
    public static String getYMDHMSTime(String time) {


        if (TextUtils.isEmpty(time)) {
            return "";
        } else {
            Date date = new Date(Long.parseLong(time));
            String result = "";
            result = sdf2.format(date);
            return result;
        }
    }

    public static String getYMDHMSTime(long time) {

        Date date = new Date(time);
        String result = "";
        result = sdf2.format(date);
        return result;
    }

    /**
     * 获取一个日期的星期数
     *
     * @param pTime
     * @return
     */
    public static int getWeek(String pTime) {
        Calendar c = Calendar.getInstance();
        try {

            c.setTime(df.parse(pTime));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return c.get(Calendar.DAY_OF_WEEK);
    }


    /**
     * 特殊格式的时间显示
     * 24小时以内显示 HH:mm
     * 24小时以上显示 MM月dd日
     */
    public static String getFormat(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日");
        Date currentDate = new Date(System.currentTimeMillis());
        long time = currentDate.getTime() - date.getTime();
        if ((time / 1000 / 3600) < 24) {
            return timeFormat.format(date.getTime());
        } else {
            return dateFormat.format(date.getTime());
        }
    }

}
