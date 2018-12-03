package cn.com.jinshangcheng.ui.position;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.baidu.navisdk.adapter.BaiduNaviManagerFactory;
import com.baidu.navisdk.adapter.IBNTTSManager;
import com.baidu.navisdk.adapter.IBaiduNaviManager;
import com.orhanobut.logger.Logger;

import java.io.File;

import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;

public class LeadRoadActivity extends BaseActivity {


    private String mSDCardPath;
    private final String APP_FOLDER_NAME = "JSC";
    private static final String[] authBaseArr = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int authBaseRequestCode = 1;


    @Override
    public int setContentViewResource() {
        return R.layout.activity_lead_road;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (!hasBasePhoneAuth()) {
                this.requestPermissions(authBaseArr, authBaseRequestCode);
                return;
            }
        }
        initBaiduNavi();
    }
    public void initBaiduNavi(){
        if (!initDirs()) {
            return;
        }
        //初始化百度导航
        BaiduNaviManagerFactory.getBaiduNaviManager().init(this,
                mSDCardPath, APP_FOLDER_NAME, new IBaiduNaviManager.INaviInitListener() {

                    @Override
                    public void onAuthResult(int status, String msg) {
                        if (0 == status) {
                            Logger.w("key校验成功");
                        } else {
                            Logger.w("key校验失败");
                        }
                    }

                    @Override
                    public void initStart() {
                        Logger.w("百度导航引擎初始化开始");
                    }

                    @Override
                    public void initSuccess() {
                        Logger.w("百度导航引擎初始化成功");
                        // 初始化tts
                        initTTS();
                    }

                    @Override
                    public void initFailed() {
                        Logger.w("百度导航引擎初始化失败");
                    }
                });
    }

    //检查权限
    private boolean hasBasePhoneAuth() {
        PackageManager pm = this.getPackageManager();
        for (String auth : authBaseArr) {
            if (pm.checkPermission(auth, this.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    // 外置tts时需要实现的接口回调
    private IBNTTSManager.IBNOuterTTSPlayerCallback mTTSCallback = new IBNTTSManager.IBNOuterTTSPlayerCallback() {

        @Override
        public int getTTSState() {
//            /** 播放器空闲 */
//            int PLAYER_STATE_IDLE = 1;
//            /** 播放器正在播报 */
//            int PLAYER_STATE_PLAYING = 2;
            return PLAYER_STATE_IDLE;
        }

        @Override
        public int playTTSText(String text, String s1, int i, String s2) {
            Log.e("BNSDKDemo", "playTTSText:" + text);
            return 0;
        }

        @Override
        public void stopTTS() {
            Log.e("BNSDKDemo", "stopTTS");
        }
    };

    private void initTTS() {
        // 使用内置TTS
//        BaiduNaviManagerFactory.getTTSManager().initTTS(getApplicationContext(),
//                getSdcardDir(), APP_FOLDER_NAME, NormalUtils.getTTSAppID());

        // 不使用内置TTS
        BaiduNaviManagerFactory.getTTSManager().initTTS(mTTSCallback);

        // 注册同步内置tts状态回调
        BaiduNaviManagerFactory.getTTSManager().setOnTTSStateChangedListener(
                new IBNTTSManager.IOnTTSPlayStateChangedListener() {
                    @Override
                    public void onPlayStart() {
                        Log.e("BNSDKDemo", "ttsCallback.onPlayStart");
                    }

                    @Override
                    public void onPlayEnd(String speechId) {
                        Log.e("BNSDKDemo", "ttsCallback.onPlayEnd");
                    }

                    @Override
                    public void onPlayError(int code, String message) {
                        Log.e("BNSDKDemo", "ttsCallback.onPlayError");
                    }
                }
        );

        // 注册内置tts 异步状态消息
        BaiduNaviManagerFactory.getTTSManager().setOnTTSStateChangedHandler(
                new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        Log.e("BNSDKDemo", "ttsHandler.msg.what=" + msg.what);
                    }
                }
        );
    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == authBaseRequestCode) {
            for (int ret : grantResults) {
                if (ret == 0) {
                    continue;
                } else {
                   showToast( "缺少导航基本的权限!");
                    return;
                }
            }
            initBaiduNavi();
        }
    }
}
