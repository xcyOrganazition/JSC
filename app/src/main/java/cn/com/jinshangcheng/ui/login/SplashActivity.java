package cn.com.jinshangcheng.ui.login;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.TimerTask;

import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.ui.MainActivity;

public class SplashActivity extends BaseActivity {

    private CountDownTimer countDownTimer;
    private static final long DELAY_TIME = 1000;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_splash;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        countDownTimer = new CountDownTimer(DELAY_TIME, DELAY_TIME) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                SplashActivity.this.finish();
            }
        };
        countDownTimer.start();

    }
}
