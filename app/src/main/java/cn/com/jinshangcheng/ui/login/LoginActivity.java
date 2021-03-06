package cn.com.jinshangcheng.ui.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.LoginBean;
import cn.com.jinshangcheng.bean.UserBean;
import cn.com.jinshangcheng.config.ConstParams;
import cn.com.jinshangcheng.net.NetApi;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.ui.MainActivity;
import cn.com.jinshangcheng.utils.CommonUtils;
import cn.com.jinshangcheng.utils.SharedPreferenceUtils;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import platform.cston.httplib.bean.AuthorizationInfo;
import platform.cston.httplib.search.AuthUser;
import platform.cston.httplib.search.OnResultListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 登录
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
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    private long finishTime;
    private long coldDown = 120000;//120s 冷却时间
    private String countDownHint;//倒计时显示文字
    private CountDownTimer timer;
    private String mVeriyCode = "";
    private String phoneNum = "";

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
        phoneNum = SharedPreferenceUtils.getStringSP("phoneNum");
        countDownHint = this.getString(R.string.countDownHint);
    }

    @Override
    public void initView() {
        if (!TextUtils.isEmpty(phoneNum)) {
            etPhoneNum.setText(phoneNum);
            etPhoneNum.setSelection(phoneNum.length());
        }
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
        InputMethodManager mInputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(etPhoneNum.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        mInputMethodManager.hideSoftInputFromWindow(etPassword.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

        etPhoneNum.setError(null);
        etPassword.setError(null);

        String phoneNum = etPhoneNum.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        View focusView = null;

        if (TextUtils.isEmpty(phoneNum)) {
            etPhoneNum.setError("请输入手机号");
            focusView = etPhoneNum;
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("请输入验证码");
            etPassword.requestFocus();
            return;
        }
        if (mVeriyCode.equals("") || !mVeriyCode.equals(password)) {
            etPassword.setError("验证码不正确");
            focusView = etPassword;
            return;
        }
        if (!cbAccept.isChecked()) {
            showToast("请阅读并同意服务协议");
            return;
        }

//        doLogin(phoneNum);//老的登录接口 不再使用
        doLoginNew(phoneNum);
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

    public void doLoginNew(final String phone) {
        showLoading();
        RetrofitService.getRetrofit().loginNew(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<BaseBean<UserBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<UserBean> baseBean) {
                        dismissLoading();
                        Intent intentInviter = new Intent(LoginActivity.this, AddInviterActivity.class);
                        Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
                        SharedPreferenceUtils.setStringSP("phoneNum", phone);
                        if ("0".equals(baseBean.code)) {
                            MyApplication.setUserBean(baseBean.data);
                            MyApplication.setUserId(baseBean.data.userid);
                            SharedPreferenceUtils.setStringSP("userId", baseBean.data.userid);
                            //已绑定推荐人 跳转MainActivity
                            startActivity(intentMain);
                        } else if ("1".equals(baseBean.code)) {
                            //未绑定推荐人 跳转推荐人页面
                            startActivities(new Intent[]{intentMain, intentInviter});
                        }
                        LoginActivity.this.finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Deprecated
    public void doLogin(final String phone) {
        showLoading();
        final NetApi retrofit = RetrofitService.getRetrofit();
        retrofit.login(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<LoginBean, ObservableSource<BaseBean<UserBean>>>() {
                    @Override
                    public ObservableSource<BaseBean<UserBean>> apply(LoginBean loginBean) {
                        if (!"0".equals(loginBean.code)) {
                            showToast(loginBean.message);
                            dismissLoading();
                            return null;
                        }
                        MyApplication.setUserId(loginBean.userid);
                        SharedPreferenceUtils.setStringSP("userId", loginBean.userid);
                        SharedPreferenceUtils.setStringSP("phoneNum", phone);
                        return retrofit.getUserInfo(loginBean.userid);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<UserBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<UserBean> baseBean) {
                        if ("0".equals(baseBean.code) && baseBean.data != null) {
                            MyApplication.setUserBean(baseBean.data);
                            Intent intentInviter = new Intent(LoginActivity.this, AddInviterActivity.class);
                            Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
                            if (!"1".equals(baseBean.data.userid) && TextUtils.isEmpty(baseBean.data.parentid)) {
                                //未绑定推荐人 跳转推荐人页面
                                startActivities(new Intent[]{intentMain, intentInviter});
                            } else {
                                //已绑定推荐人 跳转MainActivity
                                startActivity(intentMain);
                            }
                            LoginActivity.this.finish();
                        } else {
                            showToast(baseBean.message);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        dismissLoading();
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


    @OnClick({R.id.bt_code, R.id.bt_login, R.id.tv_agreement, R.id.tv_callCustomerService})
    public void onViewClicked(View view) {
        CommonUtils.hideSoftKeyboard(LoginActivity.this);

        switch (view.getId()) {
            case R.id.bt_code:
                getVerify();//获取验证码
                break;
            case R.id.bt_login://登陆
                attemptLogin();
                break;
            case R.id.tv_agreement://查看协议
                Intent intent = new Intent(LoginActivity.this, AgreementActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_callCustomerService://联系客服
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ConstParams.CUSTOMER_SERVICE));
                startActivity(dialIntent);
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

