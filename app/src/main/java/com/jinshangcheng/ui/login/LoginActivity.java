package com.jinshangcheng.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jinshangcheng.R;
import com.jinshangcheng.base.BaseActivity;
import com.jinshangcheng.ui.MainActivity;
import com.jinshangcheng.utils.SharedPreferenceUtils;
import com.orhanobut.logger.Logger;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import platform.cston.httplib.bean.AuthorizationInfo;
import platform.cston.httplib.search.AuthUser;
import platform.cston.httplib.search.OnResultListener;

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

    @Override
    public int setContentViewResource() {
        return R.layout.activity_login;
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


    private void attemptLogin() {
        etPhoneNum.setError(null);
        etPassword.setError(null);

        String phoneNum = etPhoneNum.getText().toString();
        String password = etPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(phoneNum)) {
            etPhoneNum.setError("请输入手机号");
            focusView = etPhoneNum;
            cancel = true;

        }
        if (!TextUtils.isEmpty(password)) {
            etPassword.setError("请输入密码");
            focusView = etPassword;
            cancel = true;
        }
        if (!cancel) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }


    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }


    /**
     * 获取验证码
     */
    private void getVerify() {

        String phoneNum = etPhoneNum.getText().toString();
        countDown(coldDown);
        finishTime = System.currentTimeMillis() + coldDown;


    }


    @Override
    protected void onDestroy() {
        if (finishTime != 0L) {
            SharedPreferenceUtils.setLongSP("CodeTime", finishTime);
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
        new CountDownTimer(now, 1000) {
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

        switch (view.getId()) {
            case R.id.bt_code:
                getVerify();
                break;
            case R.id.bt_login:
//                attemptLogin();
                testJiaTu();
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

