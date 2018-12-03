package cn.com.jinshangcheng.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.LoginBean;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.ui.MainActivity;
import cn.com.jinshangcheng.utils.CommonUtils;
import cn.com.jinshangcheng.utils.SharedPreferenceUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import platform.cston.httplib.bean.AuthorizationInfo;
import platform.cston.httplib.search.AuthUser;
import platform.cston.httplib.search.OnResultListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 登陆
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_phoneNum)
    EditText etPhoneNum;
    @BindView(R.id.bt_code)
    Button btCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.cb_accept)
    CheckBox cbAccept;
    @BindView(R.id.bt_login)
    Button btLogin;
    private long finishTime;
    private long coldDown = 120000;//120s 冷却时间
    private String countDownHint;//倒计时显示文字
    private CountDownTimer timer;
    private String mVeriyCode = "";

    @Override
    public int setContentViewResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarFullTransparent();
    }

    /**
     * 全透状态栏
     */
    protected void setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 20) {//4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }


    @Override
    public void initData() {
        countDownHint = this.getString(R.string.countDownHint);
    }

    @Override
    public void initView() {
        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
    }

    //验证输入是否正确
    private void attemptLogin() {
        etPhoneNum.setError(null);
        etPassword.setError(null);

        String phoneNum = etPhoneNum.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        View focusView = null;

//        if (TextUtils.isEmpty(phoneNum)) {
//            etPhoneNum.setError("请输入手机号");
//            focusView = etPhoneNum;
//            return;
//
//        }
//        if (TextUtils.isEmpty(password)) {
//            etPassword.setError("请输入验证码");
//            etPassword.requestFocus();
//            return;
//        }
//        if (mVeriyCode.equals("") || !mVeriyCode.equals(password)) {
//            etPassword.setError("验证码不正确");
//            focusView = etPassword;
//            return;
//        }
        doLogin(phoneNum);
    }


    /**
     * 获取验证码
     */
    private void getVerify() {
        String phoneNum = etPhoneNum.getText().toString();
        if (!CommonUtils.isMobilePhone(phoneNum)) {
            showToast("手机号输入有误");
            return;
        }
        RetrofitService.getRetrofit().getVerifyCode(phoneNum).enqueue(
                new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (null == response.body()) {
                            return;
                        }
                        countDown(coldDown);
                        finishTime = System.currentTimeMillis() + coldDown;
                        try {
                            String s = response.body().toString();
                            JSONObject body = new JSONObject(s);
                            mVeriyCode = body.getString("verifycode");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                        showToast("验证码获取失败");
                    }
                }
        );
    }

    public void doLogin(String phone) {

        RetrofitService.getRetrofit().login(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        Intent intent = new Intent(LoginActivity.this, AddInviterActivity.class);
//                Intent intent2 = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
//                startActivities(new Intent[]{intent2, intent});
                        LoginActivity.this.finish();
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
        if (finishTime != 0L) {
            SharedPreferenceUtils.setLongSP("CodeTime", finishTime);
        }
        if (timer != null) {
            timer.cancel();//清除timer 防止内存侧漏
        }
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            long lastTime = SharedPreferenceUtils.getLongSP("CodeTime");
            if (lastTime != 0) {
                long remainTime = lastTime - System.currentTimeMillis();
                if (remainTime > 1000)
                    countDown(remainTime);
            }
        } catch (Exception e) {

        }
    }

    public void countDown(final long now) {
        timer = new CountDownTimer(now, 1000) {
            @Override
            public void onTick(long l) {
                if (btCode == null) {
                    return;
                }
                btCode.setClickable(false);
                btCode.setEnabled(false);
                int i = (int) Math.floor(l / 1000);
                btCode.setText(String.format(countDownHint, i));
            }

            @Override
            public void onFinish() {
                if (btCode == null) {
                    return;
                }
                btCode.setClickable(true);
                btCode.setEnabled(true);
                btCode.setText("重新获取");

            }
        }.start();
    }


    @OnClick({R.id.bt_code, R.id.bt_login})
    public void onViewClicked(View view) {
        CommonUtils.hideSoftKeyboard(LoginActivity.this);

        switch (view.getId()) {
            case R.id.bt_code:
                getVerify();//获取验证码
                break;
            case R.id.bt_login://登陆
                attemptLogin();
                break;
        }
    }

    private void testJiaTu() {
        //检查是否已有授权
        boolean authorizationSuccess = AuthUser.getInstance().isAuthorization();
        if (authorizationSuccess) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            //调用驾图授权页面
            AuthUser.getInstance().Authorization(this, new OnResultListener.OnAuthorizationListener() {
                @Override
                public void onAuthorizationResult(boolean isSuccess, String result) {
                    if (isSuccess) {
                        Logger.i("登陆成功" + result);
                        AuthorizationInfo authorInfo = AuthUser.getInstance().ResetOpenIdAndOpenCarId();
                        Logger.w("authorInfo", authorInfo);
                    } else {
                        Logger.i("登陆失败" + result);

                    }
                }
            });
        }


    }
}

