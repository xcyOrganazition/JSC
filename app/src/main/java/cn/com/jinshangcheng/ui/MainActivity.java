package cn.com.jinshangcheng.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.ui.car.CarFragment;
import cn.com.jinshangcheng.ui.mine.CarManageActivity;
import cn.com.jinshangcheng.ui.mine.EditMineActivity;
import cn.com.jinshangcheng.ui.mine.MineFragment;
import cn.com.jinshangcheng.ui.position.PositionFragment;
import cn.com.jinshangcheng.ui.square.SquareFragment;
import cn.com.jinshangcheng.widget.TittleBar;

public class MainActivity extends BaseActivity {


    @BindView(R.id.viewPage)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.tittleBar)
    TittleBar tittleBar;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;

    //tab栏文字
//    private static final String[] TAB_NAMES = new String[]{"爱车", "广场", "位置", "交流", "我的"};
    private static final String[] TAB_NAMES = new String[]{"爱车", "广场", "位置", "我的"};
    //    private static final int MAIN_TEXT_RES[] = {
//            R.drawable.selector_main_car, R.drawable.selector_main_square,
//            R.drawable.selector_main_position, R.drawable.selector_main_comm,
//            R.drawable.selector_main_mine};
    private static final int MAIN_TEXT_RES[] = {
            R.drawable.selector_main_car, R.drawable.selector_main_square,
            R.drawable.selector_main_position,
            R.drawable.selector_main_mine};
    private Fragment[] fragments;

    private long firstTime = 0; //记录首次点击返回键时间
    private EMMessageListener msgListener;//环信消息监听


    @Override
    public int setContentViewResource() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        tvTittle.setText("爱车");
        fragments = new Fragment[TAB_NAMES.length];
        fragments[0] = CarFragment.getInstance();
        fragments[1] = SquareFragment.getInstance();
        fragments[2] = PositionFragment.getInstance();
//        fragments[3] = CommunicateFragment.getInstance();
        fragments[3] = MineFragment.getInstance();

//        AuthorizationInfo authorInfo = AuthUser.getInstance().ResetOpenIdAndOpenCarId();
//        MyApplication.setAuthorInfo(authorInfo);
    }

    @Override
    public void initView() {
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mSectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(TAB_NAMES.length - 1);

        initTabItem();
        tittleBar.hideNavigation();//隐藏返回键
        loginHX("appTest", "123456");//登陆环信
    }

    public void loginHX(String userName, String password) {
        EMClient.getInstance().login(userName, password, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();//加载分组信息
                EMClient.getInstance().chatManager().loadAllConversations();//加载聊天信息
                addOnEMMessageListener();
                Logger.e("登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(int code, String message) {
                Logger.e("登录聊天服务器失败");
            }
        });

    }

    public void addOnEMMessageListener() {
        msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                //收到消息
                for (EMMessage message : messages) {
                    Logger.w("发送人：" + message.getFrom());
                    int startIndex = message.getBody().toString().indexOf("\"");
                    String jsonString = message.getBody().toString().substring(startIndex);
                    Logger.w("json内容：" + jsonString);


                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                //收到已读回执
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
                //收到已送达回执
            }

            @Override
            public void onMessageRecalled(List<EMMessage> messages) {
                //消息被撤回
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    private void initTabItem() {
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < TAB_NAMES.length; i++) {
            TextView tv = new TextView(this);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(12);
            tv.setText(TAB_NAMES[i]);

            tv.setTextColor(ContextCompat.getColor(this, i == 0 ? R.color.themeColor : R.color.textBlack));
            Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), MAIN_TEXT_RES[i]);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv.setCompoundDrawables(null, drawable, null, null);
            tabLayout.getTabAt(i).setCustomView(tv);
        }
//        tabLayout.getTabAt(1).select();
        tabLayout.getTabAt(0).select();
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                TextView textView = (TextView) tab.getCustomView();
                tittleBar.setTittle(TAB_NAMES[tab.getPosition()]);
                textView.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.themeColor));
                if (tab.getPosition() == TAB_NAMES.length - 1) {
                    tittleBar.setAction(R.menu.navigation, new Toolbar.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.navigation_dashboard:
                                    Intent intent = new Intent(MainActivity.this, EditMineActivity.class);
                                    startActivity(intent);
                                    break;

                            }
                            return true;

                        }

                    });
                } else {
                    tittleBar.setNoAction();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView textView = (TextView) tab.getCustomView();
                textView.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.textDarkGary));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return TAB_NAMES.length;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 1500) {
                    Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;
                    return true;
                } else {
                    MainActivity.this.finish();
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除环信消息监听
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CarManageActivity.RESULT_CODE && data != null) {
            if (data.getBooleanExtra("needUpdateCarList", false)) {
                Logger.e("更新CarFragment");
                CarFragment carFragment = (CarFragment) fragments[0];
                carFragment.mPresenter.getCarList();
            }
        }
    }
}
