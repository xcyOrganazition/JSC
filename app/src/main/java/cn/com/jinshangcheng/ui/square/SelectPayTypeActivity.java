package cn.com.jinshangcheng.ui.square;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;

public class SelectPayTypeActivity extends BaseActivity {

    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.cb_aliPay)
    CheckBox cbAliPay;
    @BindView(R.id.cb_wechatPay)
    CheckBox cbWechatPay;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_select_pay_type;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        cbAliPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbWechatPay.setChecked(false);
                }
            }
        });
        cbWechatPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbAliPay.setChecked(false);
                }
            }
        });

    }



    @OnClick(R.id.bt_confirm)
    public void onViewClicked() {
    }
}
