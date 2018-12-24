package cn.com.jinshangcheng.ui.mine;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.utils.CommonUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePhoneActivity extends BaseActivity {

    @BindView(R.id.et_phoneNum)
    EditText etPhoneNum;
    @BindView(R.id.bt_code)
    Button btCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.email_login_form)
    LinearLayout emailLoginForm;

    private long coldDown = 120000;//120s 冷却时间
    private String countDownHint;//倒计时显示文字
    private CountDownTimer timer;
    private String mVeriyCode = "";
    private String phoneNum = "";
    public static final int RESULT_CODE = 0x543;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_change_phone;
    }

    @Override
    public void initData() {
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
        CommonUtils.hideSoftKeyboard(this);
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
        changeUserPhone(phoneNum);
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

    public void changeUserPhone(final String phoneNum) {
        showLoading();
        RetrofitService.getRetrofit().updateUserPhone(MyApplication.getUserId(), phoneNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        dismissLoading();
                        showToast(baseBean.message);
                        if ("0".equals(baseBean.code)) {
                            Intent intent = new Intent();
                            intent.putExtra("newPhone", phoneNum);
                            setResult(RESULT_CODE, intent);
                            finish();
                        }
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

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();//清除timer 防止内存侧漏
        }
        super.onDestroy();
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
        switch (view.getId()) {
            case R.id.bt_code:
                getVerify();//获取验证码
                break;
            case R.id.bt_login:
                attemptLogin();
                break;
        }
    }
}
