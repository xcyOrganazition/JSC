package com.jinshangcheng.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jinshangcheng.R;
import com.jinshangcheng.base.BaseActivity;
import com.jinshangcheng.bean.Car;
import com.jinshangcheng.ui.car.CarFragment;
import com.jinshangcheng.ui.communicate.CommunicateFragment;
import com.jinshangcheng.ui.mine.MineFragment;
import com.jinshangcheng.ui.position.PositionFragment;
import com.jinshangcheng.ui.square.SquareFragment;
import com.jinshangcheng.utils.DensityUtil;
import com.jinshangcheng.widget.TittleBar;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.logging.HttpLoggingInterceptor;
import platform.cston.httplib.bean.AuthorizationInfo;
import platform.cston.httplib.search.AuthUser;

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
    private static final String[] TAB_NAMES = new String[]{"爱车", "广场", "位置", "交流", "我的"};
    private static final int MAIN_TEXT_RES[] = {
            R.drawable.selector_main_one, R.drawable.selector_main_one,
            R.drawable.selector_main_one, R.drawable.selector_main_one,
            R.drawable.selector_main_one};
    private Fragment[] fragments;

    private long firstTime = 0; //记录首次点击返回键时间


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
        fragments[3] = CommunicateFragment.getInstance();
        fragments[4] = MineFragment.getInstance();

        AuthorizationInfo authorInfo = AuthUser.getInstance().ResetOpenIdAndOpenCarId();
        Logger.w("authorInfo" + authorInfo.openId);
        Logger.w("carList" + authorInfo.getCarsArrayList());
    }

    @Override
    public void initView() {
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mSectionsPagerAdapter);

        initTabItem();
        tittleBar.hideNavigation();//隐藏返回键

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
                Car car = new Car("卡宴", "京A23282", "保时捷", "保时捷系列");
                Car car2 = new Car("卡宴2", "京A23282", "保时捷2", "保时捷系列2");
                List<Car> carList = new ArrayList<>();

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


}
