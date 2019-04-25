/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.com.jinshangcheng.ui.communicate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.hyphenate.easeui.adapter.EaseContactAdapter;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.widget.EaseSidebar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.com.jinshangcheng.DemoHelper;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.extra.easePackage.Constant;

@SuppressLint("Registered")
public class PickContactNoCheckboxActivity extends BaseActivity {

    protected EaseContactAdapter contactAdapter;
    private List<EaseUser> contactList;

    @Override
    public int setContentViewResource() {
        return R.layout.em_activity_pick_contact_no_checkbox;
    }

    @Override
    public void initData() {
        contactList = new ArrayList<EaseUser>();
        // get contactlist
        getContactList();
        // set adapter
        contactAdapter = new EaseContactAdapter(this, R.layout.ease_row_contact, contactList);
    }

    @Override
    public void initView() {
        ListView listView = (ListView) findViewById(R.id.list);
        EaseSidebar sidebar = (EaseSidebar) findViewById(R.id.sidebar);
        sidebar.setListView(listView);
        listView.setAdapter(contactAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClick(position);
            }
        });
    }


    protected void onListItemClick(int position) {
        setResult(RESULT_OK, new Intent().putExtra("username", contactAdapter.getItem(position)
                .getUsername()));
        finish();
    }

    public void back(View view) {
        finish();
    }

    private void getContactList() {
        contactList.clear();
        Map<String, EaseUser> users = DemoHelper.getInstance().getContactList();
        for (Entry<String, EaseUser> entry : users.entrySet()) {
            if (!entry.getKey().equals(Constant.NEW_FRIENDS_USERNAME) && !entry.getKey().equals(Constant.GROUP_USERNAME) && !entry.getKey().equals(Constant.CHAT_ROOM) && !entry.getKey().equals(Constant.CHAT_ROBOT))
                contactList.add(entry.getValue());
        }
        // sort
        Collections.sort(contactList, new Comparator<EaseUser>() {

            @Override
            public int compare(EaseUser lhs, EaseUser rhs) {
                if (lhs.getInitialLetter().equals(rhs.getInitialLetter())) {
                    return lhs.getNickname().compareTo(rhs.getNickname());
                } else {
                    if ("#".equals(lhs.getInitialLetter())) {
                        return 1;
                    } else if ("#".equals(rhs.getInitialLetter())) {
                        return -1;
                    }
                    return lhs.getInitialLetter().compareTo(rhs.getInitialLetter());
                }

            }
        });
    }

}
