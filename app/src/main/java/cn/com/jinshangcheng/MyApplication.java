package cn.com.jinshangcheng;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import cn.com.jinshangcheng.config.ConstParams;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogStrategy;
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
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import platform.cston.httplib.Cston;
import platform.cston.httplib.bean.AuthInfo;
import platform.cston.httplib.bean.AuthorizationInfo;
import platform.cston.httplib.search.AuthUser;

public class MyApplication extends Application {
    private static Context mContext;
    private static MyApplication instance;

    private static AuthorizationInfo authorInfo = null;


    public MyApplication() {
        instance = this;
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
        Cston.Auth.init(this);//初始化CstSdk
        Cston.Auth.setDebug(true);

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

                return new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
                //指定为经典Footer，默认是 BallPulseFooter
                //  return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
}