package cn.com.jinshangcheng.ui.mine;

import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;

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

    @Override
    public int setContentViewResource() {
        return R.layout.activity_new_card;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.bt_newAddress)
    public void onViewClicked() {
    }
}
