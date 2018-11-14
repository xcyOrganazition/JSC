package cn.com.jinshangcheng.ui.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.adapter.WithDrawAdapter;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BaseListBean;
import cn.com.jinshangcheng.bean.WithdrawBean;
import cn.com.jinshangcheng.config.ConstParams;
import cn.com.jinshangcheng.net.RetrofitService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
    private int page;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_with_draw;
    }

    @Override
    public void initData() {
        page = 1;
        list = new ArrayList<>();
        adapter = new WithDrawAdapter(this, list);
    }

    @Override
    public void initView() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                getWithdrawList();

            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                list.clear();
                page = 1;
                getWithdrawList();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        refreshLayout.autoRefresh();

    }

    //获取账单变更列表
    public void getWithdrawList() {
        RetrofitService.getRetrofit().getWithdrawList(MyApplication.getUserId(), page, ConstParams.PAGE_COUNT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseListBean<WithdrawBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseListBean<WithdrawBean> baseBean) {
                        refreshLayout.finishLoadMore();
                        if (null != baseBean) {
                            list.addAll(baseBean.getBeanList());
                        }
                        adapter.refreshAdapter(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}

