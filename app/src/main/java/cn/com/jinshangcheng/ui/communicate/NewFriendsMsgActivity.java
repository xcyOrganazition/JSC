package cn.com.jinshangcheng.ui.communicate;

import android.view.View;
import android.widget.ListView;


import java.util.Collections;
import java.util.List;

import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.adapter.NewFriendsMsgAdapter;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.extra.easePackage.db.InviteMessgeDao;
import cn.com.jinshangcheng.extra.easePackage.domain.InviteMessage;

/**
 * Application and notification
 *
 */
public class NewFriendsMsgActivity extends BaseActivity {

    private NewFriendsMsgAdapter adapter;

    @Override
    public int setContentViewResource() {
        return R.layout.em_activity_new_friends_msg;
    }

    @Override
    public void initData() {

        InviteMessgeDao dao = new InviteMessgeDao(this);
        List<InviteMessage> msgs = dao.getMessagesList();
        Collections.reverse(msgs);

        adapter = new NewFriendsMsgAdapter(this, 1, msgs);

        dao.saveUnreadMessageCount(0);
    }

    @Override
    public void initView() {
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

    }


    public void back(View view) {
        finish();
    }
}
