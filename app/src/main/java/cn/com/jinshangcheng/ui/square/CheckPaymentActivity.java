package cn.com.jinshangcheng.ui.square;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.ui.mine.MyOrderActivity;
import cn.com.jinshangcheng.widget.CheckPaymentDialog;

public class CheckPaymentActivity extends BaseActivity {


    @BindView(R.id.et_verificationCode)
    EditText etVerificationCode;
    @BindView(R.id.tv_howTo)
    TextView tvHowTo;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_check_payment;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }


    @OnClick({R.id.bt_checkNow, R.id.checkLatter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_checkNow:
                CheckPaymentDialog dialog = new CheckPaymentDialog();
                dialog.show(getFragmentManager(),"");
                break;
            case R.id.checkLatter:
                Intent intent = new Intent(CheckPaymentActivity.this, MyOrderActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
