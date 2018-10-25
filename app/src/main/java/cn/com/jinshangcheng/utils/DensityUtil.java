package cn.com.jinshangcheng.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by xu on 2017/10/11.
 */
public class DensityUtil {
    public DensityUtil() {
    }

    public static int dip2px(Context var0, float var1) {
        float var2 = var0.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (var1 * var2 + 0.5F);
    }

    public static int px2dip(Context var0, float var1) {
        float var2 = var0.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (var1 / var2 + 0.5F);
    }

    public static int sp2px(Context var0, float var1) {
        float var2 = var0.getApplicationContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (var1 * var2 + 0.5F);
    }

    public static int px2sp(Context var0, float var1) {
        float var2 = var0.getApplicationContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (var1 / var2 + 0.5F);
    }

    /**
     * 屏幕宽度 像素
     *
     * @param var0
     * @return
     */
    public static int getWidthPixel(Context var0) {
        WindowManager wm = (WindowManager) var0.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        return width;
    }

    /**
     * 屏幕高度 像素
     *
     * @param var0
     * @return
     */
    public static int getHeightPixel(Context var0) {
        WindowManager wm = (WindowManager) var0.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        return height;
    }
}
