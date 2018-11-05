package cn.com.jinshangcheng.ui.login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.ui.MainActivity;

/**
 * 绑定盒子
 */
public class BindBoxActivity extends BaseActivity {

    @BindView(R.id.et_boxCode)
    EditText etBoxCode;
    @BindView(R.id.et_carCode)
    EditText etCarCode;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_bind_box;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }


    @OnClick({R.id.bt_bindBox, R.id.bt_notBindNow, R.id.tv_buy})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.bt_bindBox:
                intent = new Intent(BindBoxActivity.this, MainActivity.class);
                break;
            case R.id.bt_notBindNow:
                intent = new Intent(BindBoxActivity.this, MainActivity.class);
                break;
            case R.id.tv_buy:
                intent = new Intent(BindBoxActivity.this, MainActivity.class);
                break;
        }
        startActivity(intent);
    }
}
