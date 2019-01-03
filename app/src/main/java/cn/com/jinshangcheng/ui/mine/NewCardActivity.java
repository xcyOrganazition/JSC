package cn.com.jinshangcheng.ui.mine;

import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;

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

/**
 * 添加银行卡
 */
public class NewCardActivity extends BaseActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_cardNum)
    EditText etCardNum;
    @BindView(R.id.et_bank)
    EditText etBank;
    @BindView(R.id.cb_defaultCard)
    CheckBox cbDefaultCard;

    BankCardBean cardBean;
    public static final int RESULT_CODE = 0x31;
    private boolean isNewCard;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_new_card;
    }

    @Override
    public void initData() {
        isNewCard = getIntent().getBooleanExtra("isFromNew", false);
        cardBean = (BankCardBean) getIntent().getSerializableExtra("card");
    }

    @Override
    public void initView() {
        if (cardBean != null) {
            etBank.setText(cardBean.accountbank);
            etName.setText(cardBean.accountuser);
            etCardNum.setText(cardBean.accountnum);
            cbDefaultCard.setChecked(cardBean.isdefault == 0);
        }
    }

    @OnClick(R.id.bt_newAddress)
    public void onViewClicked() {
        if (TextUtils.isEmpty(etName.getText().toString().trim())) {
            showToast("姓名不能为空");
            return;
        } else if (TextUtils.isEmpty(etCardNum.getText().toString().trim())) {
            showToast("银行卡号不能为空");
            return;
        } else if (TextUtils.isEmpty(etBank.getText().toString().trim())) {
            showToast("开户行不能为空");
            return;
        }
        if (isNewCard) {
            uploadCardInfo();
        } else {
            updateCard();
        }
    }

    public void uploadCardInfo() {

        RetrofitService.getRetrofit().addBankCard(
                MyApplication.getUserId(),
                etCardNum.getText().toString().trim(),
                etName.getText().toString().trim(),
                etBank.getText().toString().trim(),
                cbDefaultCard.isChecked() ? 0 : 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<BankCardBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<BankCardBean> addressBaseBean) {
                        showToast(addressBaseBean.message);
                        if (addressBaseBean.code.equals("0")) {
                            setResult(RESULT_CODE);
                            finish();
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

    public void updateCard() {
        RetrofitService.getRetrofit().updateCard(
                MyApplication.getUserId(),
                cardBean.accountid,
                etCardNum.getText().toString().trim(),
                etName.getText().toString().trim(),
                etBank.getText().toString().trim(),
                cbDefaultCard.isChecked() ? 0 : 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean addressBaseBean) {
                        showToast(addressBaseBean.message);
                        if (addressBaseBean.code.equals("0")) {
                            setResult(RESULT_CODE);
                            finish();
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
}
