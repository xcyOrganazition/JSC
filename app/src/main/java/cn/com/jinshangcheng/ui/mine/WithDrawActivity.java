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
import cn.com.jinshangcheng.adapter.WithDrawAdapter;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.WithdrawBean;

/**
 * 提现记录
 */
public class WithDrawActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private List<WithdrawBean> list;
    private WithDrawAdapter adapter;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_with_draw;
    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        list.add(new WithdrawBean());
        list.add(new WithdrawBean());
        list.add(new WithdrawBean());
        adapter = new WithDrawAdapter(this, list);


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
        recyclerView.setAdapter(adapter);

    }

}

