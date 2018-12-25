package cn.com.jinshangcheng.ui.square;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.OrderBean;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.ui.mine.MyOrderActivity;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CheckPaymentActivity extends BaseActivity {


    @BindView(R.id.et_verificationCode)
    EditText etVerificationCode;
    @BindView(R.id.tv_howTo)
    TextView tvHowTo;

    private OrderBean orderBean;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_check_payment;
    }

    @Override
    public void initData() {

        orderBean = (OrderBean) getIntent().getSerializableExtra("orderBean");
    }

    @Override
    public void initView() {

    }


    @OnClick({R.id.bt_checkNow, R.id.checkLatter, R.id.tv_howTo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_howTo:
                Intent intent1 = new Intent(CheckPaymentActivity.this, HowToActivity.class);
                startActivity(intent1);
                break;
            case R.id.bt_checkNow:
                payByPayCode();
                break;
            case R.id.checkLatter:
                Intent intent = new Intent(CheckPaymentActivity.this, MyOrderActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    public void payByPayCode() {
        RetrofitService.getRetrofit().payByPayCode(MyApplication.getUserId(), orderBean.getOrderid(), etVerificationCode.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        dismissLoading();
                        showToast("创建成功");
                        Intent intent1 = new Intent(getApplicationContext(), MyOrderActivity.class);
                        startActivity(intent1);
                        finish();
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
}
