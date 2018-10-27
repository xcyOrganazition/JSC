package cn.com.jinshangcheng.ui.mine;

import android.content.Intent;
import android.os.Bundle;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;

/**
 * 银行卡
 */
public class BankCardActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    SwipeMenuRecyclerView recyclerView;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_bank_card;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }


    @OnClick(R.id.bt_newCard)
    public void onViewClicked() {
        Intent intent = new Intent(BankCardActivity.this, NewCardActivity.class);
        startActivity(intent);
    }
}
