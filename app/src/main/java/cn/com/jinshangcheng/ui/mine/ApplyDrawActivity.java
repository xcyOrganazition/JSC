package cn.com.jinshangcheng.ui.mine;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;

/**
 * 申请提现
 */
public class ApplyDrawActivity extends BaseActivity {

    @BindView(R.id.et_drawMoney)
    EditText etDrawMoney;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_apply_draw;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

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
}
