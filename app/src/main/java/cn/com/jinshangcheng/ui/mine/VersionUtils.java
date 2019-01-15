package cn.com.jinshangcheng.ui.mine;

import android.content.Context;
import android.content.pm.PackageManager;

public class VersionUtils {
    /**
     * 获取版本号名称
     *
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }
}
