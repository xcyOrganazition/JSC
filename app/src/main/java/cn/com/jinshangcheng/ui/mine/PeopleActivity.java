package cn.com.jinshangcheng.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.OnRefreshLoadmoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.adapter.PeopleAdapter;
import cn.com.jinshangcheng.base.BaseActivity;

public class PeopleActivity extends BaseActivity {

    private List list;

    PeopleAdapter adapter;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_zhitui)
    TextView tvZhitui;
    @BindView(R.id.tv_other)
    TextView tvOther;
    @BindView(R.id.recyclerView)
    SwipeMenuRecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int page;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_people;
    }

    @Override
    public void initData() {
        list = new ArrayList();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        adapter = new PeopleAdapter(this, list);
    }

    @Override
    public void initView() {
        refreshLayout.setEnableRefresh(false);//禁止刷新
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);//不满一页禁止加载
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                Logger.w("加载更多");
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }


}
