package cn.com.jinshangcheng.ui.login;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;

import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.UserBean;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.ui.MainActivity;
import cn.com.jinshangcheng.utils.SharedPreferenceUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {

    private CountDownTimer countDownTimer;
    private static final long DELAY_TIME = 1000;
    private String userId = "";

    @Override
    public int setContentViewResource() {
        return R.layout.activity_splash;
    }

    @Override
    public void initData() {
        userId = SharedPreferenceUtils.getStringSP("userId");
    }

    @Override
    public void initView() {
        if (!TextUtils.isEmpty(userId)) {
            getUserInfo();//已经登陆过 请求用户数据
        } else {
            startIntentTimer();
        }
    }

    private void startIntentTimer() {
        countDownTimer = new CountDownTimer(DELAY_TIME, DELAY_TIME) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent;
                if (TextUtils.isEmpty(userId)) {//未登陆
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                SplashActivity.this.finish();
            }
        };
        countDownTimer.start();
    }

    public void getUserInfo() {
        RetrofitService.getRetrofit().getUserInfo(MyApplication.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<UserBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<UserBean> baseBean) {
                        if (baseBean != null && baseBean.data != null) {
                            MyApplication.setUserBean(baseBean.data);
                            startIntentTimer();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
}
