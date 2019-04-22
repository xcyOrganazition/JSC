package cn.com.jinshangcheng.ui.communicate;

import android.view.View;

import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.utils.CommonUtils;

public class ContactListActivity extends BaseActivity {

    @Override
    public int setContentViewResource() {
        return R.layout.activity_contact_list;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        View container = findViewById(R.id.container);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container,new ContactListFragment())
                .commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CommonUtils.hideSoftKeyboard(ContactListActivity.this);
    }
}
