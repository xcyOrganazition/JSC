package cn.com.jinshangcheng.ui.login;

import android.content.Intent;
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
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.ui.mine.AddCarActivity;
import cn.com.jinshangcheng.utils.CommonUtils;
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
                            if ("".equals(jsonObject.getString("code"))) {
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


    @OnClick({R.id.bt_comfirm, R.id.tv_jumpOver})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.bt_comfirm:
                String phoneNum = etInviterPhone.getText().toString();
                if (!CommonUtils.isMobilePhone(phoneNum)) {
                    showToast("手机号输入有误");
                    return;
                }
                upDateParentId(phoneNum);
                break;
            case R.id.tv_jumpOver:
                String leaderPhoneNum = "18577881008";
                upDateParentId(leaderPhoneNum);
                break;
        }
    }
}
