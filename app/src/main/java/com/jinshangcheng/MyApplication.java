package com.jinshangcheng;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.jinshangcheng.config.ConstParams;
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

import platform.cston.httplib.Cston;

public class MyApplication extends Application {
    private static Context mContext;
    private static MyApplication instance;

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

//        EMClient.getInstance().init(mContext, new EMOptions());
//        EMClient.getInstance().setDebugMode(false);
//        DataBaseManager.initDatabase(mContext);
//        PgyCrashManager.register(this);
//        UMShareAPI.get(this);
//        threadPool = Executors.newFixedThreadPool(3);

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
}
