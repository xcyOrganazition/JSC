package cn.com.jinshangcheng.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.jinshangcheng.base.BaseActivity;


public class CommonUtils {

    public static long lastClickTime;
    private static InputMethodManager inputMethodManager;

    /**
     * 检测网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    /**
     * 通过字符串获得类名
     *
     * @param className
     * @return
     */
    public static BaseActivity getclass(String className)// className是类名
    {
        BaseActivity activity = null;
        try {
            activity = (BaseActivity) Class.forName(className).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activity;
    }

    /**
     * 检测Sdcard是否存在
     *
     * @return
     */
    public static boolean isExitsSdcard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    /**
     * 隐藏键盘
     */
    public static void hideSoftKeyboard(Activity activity) {
        inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (inputMethodManager.isActive() && activity.getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (activity.getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public static void backgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        activity.getWindow().setAttributes(lp);
//        Window window = activity.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.alpha = bgAlpha; // 0.0-1.0
//        window.setAttributes(lp);
    }

    /**
     * 防止重复点击
     *
     * @return 是否重复点击
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 是否是固话
     */
    public static boolean isTelePhone(String tp) {
        if (!TextUtils.isEmpty(tp)) {
            if (tp.length() <= 11) {
                Pattern pat = Pattern
                        .compile("(^0[0-9]{2,5}\\-[0-9]{7,11}\\-[0-9]{1,5}$)|(^[0-9]{7,11}\\-[0-9]{1,5}$)|(^0[0-9]{2,5}\\-[0-9]{7,11}$)|(^[0-9]{7,20}$)");
                Matcher mat = pat.matcher(tp);
                return mat.find();
            } else
                return false;
        } else
            return false;
    }

    /**
     * 是否是手机
     */
    public static boolean isMobilePhone(String mp) {
        if (!TextUtils.isEmpty(mp)) {
            if (mp.length() <= 11) {
                Pattern pat = Pattern.compile("^1[3-9]\\d{9}$");
                Matcher mat = pat.matcher(mp);
                return mat.find();
            } else
                return false;
        } else
            return false;
    }

    /**
     * 是否是邮编
     */
    public static boolean isZipCode(String zc) {
        if (!TextUtils.isEmpty(zc)) {
            if (zc.length() <= 6) {
                Pattern pat = Pattern.compile("\\b[0-9]{6}(?:-[0-9]{4})?\\b");
                Matcher mat = pat.matcher(zc);
                return mat.find();
            } else
                return false;
        } else
            return false;
    }

    /**
     * 是否邮箱号码
     */
    public static boolean isEmail(String em) {
        if (!TextUtils.isEmpty(em)) {
            String strPattern = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
            Pattern p = Pattern.compile(strPattern);
            Matcher m = p.matcher(em);
            return m.matches();
        } else
            return false;
    }

    /**
     * 获取文件类型
     */
    public static String getMIMEType(File f) {
        String type = "";
        String fName = f.getName();
        String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg")
                || end.equals("wav")) {
            type = "audio";
        } else if (end.equals("3gp") || end.equals("mp4")) {
            type = "video";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")) {
            type = "image";
        } else if (end.equals("apk")) {
            type = "application/vnd.android.package-archive";
        } else {
            type = "*";
        }
        if (end.equals("apk")) {
        } else {
            type += "/*";
        }
        return type;
    }

    /**
     * 自然数表示时显示格式转换
     *
     * @param str
     * @return
     */
    public static String bigDecimalFormat(String str) {
        String format;
        boolean isIntger = false;
        if (!TextUtils.isEmpty(str)) {
            Double d = new BigDecimal(Double.parseDouble(str)).doubleValue();
            if (d != 0) {
                DecimalFormat df = new DecimalFormat("#.000000");
                format = df.format(d);
                String tmp[] = format.split("\\.");
                if (tmp.length <= 1) {
                    DecimalFormat inter = new DecimalFormat("#.0");
                    format = inter.format(d);
                    return format;
                }
                for (int i = 0; i < 6; i++) {
                    int num = Integer.parseInt((tmp[1].charAt(i) + ""));
                    if (num == 0)
                        isIntger = true;
                }
                if (isIntger) {
                    DecimalFormat inter = new DecimalFormat("#.0");
                    format = inter.format(d);
                    return format;
                }
                return format;
            } else
                format = "0.0";
        } else {
            format = "暂无";
        }
        return format;
    }

    /**
     * 获取当前activity
     */
    public static String getTopActivity(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        if (runningTaskInfos != null)
            return runningTaskInfos.get(0).topActivity.getClassName();
        else
            return "";
    }

    /**
     * 判断字符串是否包含汉字
     */

    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 快速排序法
     *
     * @param a
     */
    public static void quick(int[] a) {
        if (a.length > 0) {
            quickSort(a, 0, a.length - 1);
        }
    }

    private static void quickSort(int[] a, int low, int high) {
        if (low < high) { // 如果不加这个判断递归会无法退出导致堆栈溢出异常
            int middle = getMiddle(a, low, high);
            quickSort(a, 0, middle - 1);
            quickSort(a, middle + 1, high);
        }
    }

    private static int getMiddle(int[] a, int low, int high) {
        int temp = a[low];// 基准元素
        while (low < high) {
            // 找到比基准元素小的元素位置
            while (low < high && a[high] >= temp) {
                high--;
            }
            a[low] = a[high];
            while (low < high && a[low] <= temp) {
                low++;
            }
            a[high] = a[low];
        }
        a[low] = temp;
        return low;
    }

    /**
     * 获取最大值
     */

    public static double ArrayListMax(List<String> sampleList) {
        double maxDevation = 0.0;
        int totalCount = sampleList.size();
        if (totalCount >= 1) {
            double max = Double.parseDouble(sampleList.get(0));
            for (int i = 0; i < totalCount; i++) {
                double temp = Double.parseDouble(sampleList.get(i));
                if (temp > max) {
                    max = temp;
                }
            }
            maxDevation = max;
        }
        return maxDevation;
    }

    /**
     * 地址截字  保留前半部分  后半部分为*
     *
     * @param fullAddress
     * @return
     */
    public static String hidenAddress(String fullAddress) {
        if (TextUtils.isEmpty(fullAddress)) {
            return "";
        }
        StringBuilder hiddenStr = new StringBuilder();
        int index = (int) Math.floor(fullAddress.length() / 2);
        hiddenStr.append(fullAddress.substring(0, index));
        for (int i = 0; i < (fullAddress.length() - index); i++) {
            hiddenStr.append("*");
        }
        return hiddenStr.toString();
    }

    public static boolean isTopActivity(Context context, String ActivityName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        System.out.println(cn.getClassName());
        return cn.getClassName().contains(ActivityName);
    }

    //获取是否存在NavigationBar
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }

    public static int getNavigationBarHeight(Activity mActivity) {
        if (!checkDeviceHasNavigationBar(mActivity)) {
            return 0;
        }
        Resources resources = mActivity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Logger.w("dbw", "Navi height:" + height);
        return height;
    }

}
