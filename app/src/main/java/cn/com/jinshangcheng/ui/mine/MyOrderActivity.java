package cn.com.jinshangcheng.ui.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.adapter.MyOrderAdapter;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.OrderBean;

public class MyOrderActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    MyOrderAdapter adapter;
    List<OrderBean> orderList;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_my_order;
    }

    @Override
    public void initData() {
        orderList = new ArrayList<>();
        orderList.add(new OrderBean());
        orderList.add(new OrderBean());
        orderList.add(new OrderBean());
        adapter = new MyOrderAdapter(orderList, this);

    }

    @Override
    public void initView() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();

            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //解决数据加载不完的问题
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        recyclerView.setFocusable(false);
        recyclerView.setAdapter(adapter);

    }

}
