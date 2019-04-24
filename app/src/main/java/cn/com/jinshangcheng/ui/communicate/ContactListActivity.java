package cn.com.jinshangcheng.ui.communicate;

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
