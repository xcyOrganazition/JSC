package cn.com.jinshangcheng.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;

/**
 * 我的收益
 */
public class MoneyActivity extends BaseActivity {

    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_income)
    TextView tvIncome;
    @BindView(R.id.tv_withdraw)
    TextView tvWithdraw;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_money;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.tv_history, R.id.bt_requestDraw})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_history://提现记录
                intent = new Intent(MoneyActivity.this, WithDrawActivity.class);
                break;
            case R.id.bt_requestDraw://申请提现
                intent = new Intent(MoneyActivity.this, ApplyDrawActivity.class);
                break;
        }
        startActivity(intent);
    }
}
