package cn.com.jinshangcheng.ui.mine;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BankCardBean;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.net.RetrofitService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 申请提现
 */
public class ApplyDrawActivity extends BaseActivity {

    @BindView(R.id.et_drawMoney)
    EditText etDrawMoney;
    @BindView(R.id.ll_cardParent)
    LinearLayout llCardParent;


    double maxMoney;
    private int requestCode = 0x23;
    public static int RESULT_CODE = 0x77;
    private BankCardBean selectedCard;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_apply_draw;
    }

    @Override
    public void initData() {
        maxMoney = 0;
    }

    @Override
    public void initView() {
        getMaxMoney();
        getDefaultCard();
    }


    @OnClick({R.id.bt_drawAll, R.id.bt_applyDraw, R.id.tv_changeCard})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_drawAll://全部提现
                if (selectedCard == null) {
                    showToast("请选择银卡");
                    return;
                }
                if (maxMoney == 0) {
                    showToast("您的提现额度为0");
                    return;
                }
                requsetDrawMoney(String.valueOf(maxMoney));
                break;
            case R.id.tv_changeCard://选择银行卡
                Intent intent = new Intent(ApplyDrawActivity.this, BankCardActivity.class);
                intent.putExtra("isFromSelect", true);
                startActivityForResult(intent, requestCode);
                break;
            case R.id.bt_applyDraw://申请提现
                if (checkMoney()) {
                    requsetDrawMoney(etDrawMoney.getText().toString());
                }
                break;
        }
    }

    public boolean checkMoney() {
        if (selectedCard == null) {
            showToast("请选择银卡");
            return false;
        }
        String money = etDrawMoney.getText().toString();
        if (maxMoney == 0) {
            showToast("您的提现额度为0");
            return false;
        }
        if (TextUtils.isEmpty(money)) {
            showToast("请输入提现金额");
            return false;
        }
        try {
            if (Double.valueOf(money) > maxMoney) {
                showToast("额度不足");
                return true;
            }
        } catch (Exception e) {
            showToast("您的输入有误");
            return false;
        }
        return true;
    }

    public void requsetDrawMoney(String money) {
        showLoading();
        RetrofitService.getRetrofit().drawMyMoney(
                MyApplication.getUserId(), selectedCard.accountbank, selectedCard.accountuser,
                selectedCard.accountnum, money)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        showToast(baseBean.message);
                        if ("200".equals(baseBean.code)) {
                            setResult(RESULT_CODE);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        dismissLoading();
                    }

                    @Override
                    public void onComplete() {
                        dismissLoading();
                    }
                });
    }

    public void getMaxMoney() {
        RetrofitService.getRetrofit().geCanWithdraw(MyApplication.getUserId())
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (null == response.body()) {
                            return;
                        }
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
                        t.printStackTrace();
                    }
                });
    }

    public void getDefaultCard() {
        RetrofitService.getRetrofit().getDefaultCard(MyApplication.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BankCardBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BankCardBean bankCardBean) {
                        if (bankCardBean != null) {
                            selectedCard = bankCardBean;
                            refreshCarView();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void refreshCarView() {
        llCardParent.removeAllViews();
        if (selectedCard != null) {
            View cardView = getLayoutInflater().inflate(R.layout.item_bankcard, null);
            TextView tvBankName = cardView.findViewById(R.id.tv_bankName);
            TextView tvCardNum = cardView.findViewById(R.id.tv_cardNum);
            TextView tvBankAddress = cardView.findViewById(R.id.tv_bankAddress);
            tvBankAddress.setText(String.format("开户行：%s", selectedCard.accountbank));
            tvBankName.setText(selectedCard.accountuser);
            tvCardNum.setText(selectedCard.accountnum);
            llCardParent.addView(cardView);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.requestCode && resultCode == BankCardActivity.RESULT_CODE && data != null) {
            selectedCard = (BankCardBean) data.getSerializableExtra("selectedCard");
            refreshCarView();
        }
    }
}
