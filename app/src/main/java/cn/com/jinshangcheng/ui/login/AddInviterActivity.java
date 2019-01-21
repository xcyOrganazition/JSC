package cn.com.jinshangcheng.ui.login;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.LoginBean;
import cn.com.jinshangcheng.bean.UserBean;
import cn.com.jinshangcheng.net.NetApi;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.ui.mine.AddCarActivity;
import cn.com.jinshangcheng.utils.CommonUtils;
import cn.com.jinshangcheng.utils.SharedPreferenceUtils;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddInviterActivity extends BaseActivity {

    @BindView(R.id.et_inviterPhone)
    EditText etInviterPhone;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_add_inviter;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Deprecated
    public void upDateParentId(String phoneNum) {
        showLoading();
        RetrofitService.getRetrofit().upDateParentId(MyApplication.getUserId(), phoneNum)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        dismissLoading();
                        String stringObj = response.body().toString();
                        try {
                            JSONObject jsonObject = new JSONObject(stringObj);
                            showToast(jsonObject.getString("message"));
                            if ("0".equals(jsonObject.getString("code"))) {
                                Intent intent = new Intent(AddInviterActivity.this, AddCarActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        dismissLoading();
                    }
                });
    }

    public void upDateParentIdNew(String phoneNum) {
        showLoading();
        final NetApi retrofit = RetrofitService.getRetrofit();
        retrofit.registOrLoginNew(SharedPreferenceUtils.getStringSP("phoneNum"), phoneNum)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<LoginBean, ObservableSource<BaseBean<UserBean>>>() {
                    @Override
                    public ObservableSource<BaseBean<UserBean>> apply(LoginBean loginBean) throws Exception {
                        if (!"0".equals(loginBean.code)) {
                            showToast(loginBean.message);
                            dismissLoading();
                            return null;
                        }
                        MyApplication.setUserId(loginBean.userid);
                        SharedPreferenceUtils.setStringSP("userId", loginBean.userid);
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
                            Intent intent = new Intent(AddInviterActivity.this, AddCarActivity.class);
                            startActivity(intent);
                            finish();
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


    @OnClick({R.id.bt_comfirm, R.id.tv_jumpOver})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.bt_comfirm:
                String phoneNum = etInviterPhone.getText().toString();
                if (!CommonUtils.isMobilePhone(phoneNum)) {
                    showToast("手机号输入有误");
                    return;
                }
//                upDateParentId(phoneNum);
                upDateParentIdNew(phoneNum);
                break;
            case R.id.tv_jumpOver:
//                String leaderPhoneNum = "18577881008";
//                upDateParentId(leaderPhoneNum);
                upDateParentIdNew("");
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            return true;//不执行父类点击事件
        }else {
            return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
        }
    }
}
