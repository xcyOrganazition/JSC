package cn.com.jinshangcheng;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import com.baidu.mapapi.SDKInitializer;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

import java.util.Iterator;
import java.util.List;

import cn.com.jinshangcheng.bean.CarBean;
import cn.com.jinshangcheng.bean.UserBean;
import cn.com.jinshangcheng.config.ConstParams;
import platform.cston.httplib.Cston;
import platform.cston.httplib.bean.AuthorizationInfo;

public class MyApplication extends Application {
    private static Context mContext;
    private static MyApplication instance;

    private static AuthorizationInfo authorInfo = null;
    private static String userId = null;//用户Id
    private static String carId = null;//当前选中的CarId
    private static UserBean userBean;//用户信息
    private static CarBean currentCarBean = null;//当前选择的车辆

    public MyApplication() {
        instance = this;
    }

    public static CarBean getCurrentCarBean() {
        return currentCarBean;
    }

    public static void setCurrentCarBean(CarBean currentCarBean) {
        MyApplication.currentCarBean = currentCarBean;
    }

    public MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //初始化Logger
        initLoggerSetting();
        //初始化驾图相关
        SDKInitializer.initialize(this);//初始化百度地图SDK

        initImageLoader(this);//初始化第三方图片加载类
        initEMClient(this);//初始化环信相关

        Cston.Auth.init(this);//初始化CstSdk
        Cston.Auth.setDebug(ConstParams.DEBUG);
    }

    /**
     * 初始化环信相关
     *
     * @param context
     */
    private void initEMClient(MyApplication context) {
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        if (processAppName == null || !processAppName.equalsIgnoreCase(context.getPackageName())) {
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
        // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);
        //初始化
        EMClient.getInstance().init(context, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(ConstParams.DEBUG);

    }

    public static String getCarId() {
        return carId;
    }

    public static void setCarId(String carId) {
        MyApplication.carId = carId;
    }

    public static String getUserId() {
        return userId;
        //22 王辉 ，39 明明  23 韩贺 1毛总
//        return "22";
    }

    public static void setUserId(String userId) {
        MyApplication.userId = userId;
    }

    public static void setAuthorInfo(AuthorizationInfo authorInfo) {
        MyApplication.authorInfo = authorInfo;
    }

    public static AuthorizationInfo getAuthorInfo() {
        return MyApplication.authorInfo;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Logger.clearLogAdapters();

    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 初始化Logger相关
     */
    private void initLoggerSetting() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // 是否显示线程信息 默认true
                .methodCount(0)         // 方法行数
                .methodOffset(7)        // 方法偏移量
                .tag("JSC")   // Tag
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return ConstParams.DEBUG;//是否打印日志
            }
        });
    }

    /**
     * 初始化ImageLoader
     *
     * @param context
     */
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.memoryCacheSize(2 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.defaultDisplayImageOptions(DisplayImageOptions.createSimple());
        config.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000));
        ImageLoader.getInstance().init(config.build());
    }

    /**
     * 关于Recycler的头部和底部
     */
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new MaterialHeader(context).setColorSchemeColors(R.color.colorPrimary);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });

        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {

                return new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Translate)
                        .setAnimatingColor(mContext.getResources().getColor(R.color.colorPrimary))
                        .setIndicatorColor(mContext.getResources().getColor(R.color.colorPrimary))
                        .setNormalColor(mContext.getResources().getColor(R.color.colorPrimary));
                //指定为经典Footer，默认是 BallPulseFooter
                //  return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return processName;
    }

    /**
     * 获取用户信息
     */
    public static UserBean getUserBean() {
        if (userBean == null) {
            userBean = new UserBean();
        }
        return userBean;
    }

    /**
     * 保存用户信息
     *
     * @param userBean
     */
    public static void setUserBean(UserBean userBean) {
        MyApplication.userBean = userBean;
    }
}
