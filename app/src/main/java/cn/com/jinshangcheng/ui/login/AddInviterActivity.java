package cn.com.jinshangcheng.ui.login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.ui.mine.AddCarActivity;

public class AddInviterActivity extends BaseActivity {

    @BindView(R.id.et_inviterPhone)
    EditText etInviterPhone;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_add_inviter;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }


    @OnClick({R.id.bt_comfirm, R.id.tv_jumpOver})
    public void onViewClicked(View view) {
        Intent intent = new Intent(AddInviterActivity.this, AddCarActivity.class);

        switch (view.getId()) {
            case R.id.bt_comfirm:

                break;
            case R.id.tv_jumpOver:
                break;
        }
        startActivity(intent);
        AddInviterActivity.this.finish();
    }
}
