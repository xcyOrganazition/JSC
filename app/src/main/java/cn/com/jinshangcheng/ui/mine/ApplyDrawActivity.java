package cn.com.jinshangcheng.ui.mine;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 申请提现
 */
public class ApplyDrawActivity extends BaseActivity {

    @BindView(R.id.et_drawMoney)
    EditText etDrawMoney;
    double maxMoney;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_apply_draw;
    }

    @Override
    public void initData() {
        maxMoney = 100;

    }

    @Override
    public void initView() {
        getMaxMoney();
    }


    @OnClick({R.id.bt_drawAll, R.id.bt_applyDraw, R.id.tv_changeCard})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_drawAll://全部提现
                break;
            case R.id.tv_changeCard://选择银行卡
                Intent intent = new Intent(ApplyDrawActivity.this, BankCardActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_applyDraw://申请提现
                break;
        }
    }

    public void getMaxMoney() {
        RetrofitService.getRetrofit().geCanWithdraw(MyApplication.getUserId())
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        String jsonObj = response.body().toString();
                        try {
                            JSONObject jsonObject = new JSONObject(jsonObj);
                            maxMoney = jsonObject.getDouble("canWithdra");
                            etDrawMoney.setHint(String.format("可提现金额%s元", maxMoney));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
    }
}
