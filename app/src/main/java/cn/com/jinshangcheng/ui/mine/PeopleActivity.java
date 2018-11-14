package cn.com.jinshangcheng.ui.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.adapter.PeopleAdapter;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BaseListBean;
import cn.com.jinshangcheng.bean.MyCountBean;
import cn.com.jinshangcheng.bean.MyCustomerBean;
import cn.com.jinshangcheng.config.ConstParams;
import cn.com.jinshangcheng.net.RetrofitService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的人脉
 */
public class PeopleActivity extends BaseActivity {

    private List<MyCustomerBean> customerList;

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
        page = 1;
        customerList = new ArrayList<>();
        adapter = new PeopleAdapter(this, customerList);
    }

    @Override
    public void initView() {
        refreshLayout.setEnableRefresh(false);//禁止刷新
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);//不满一页禁止加载
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                getMyPeopleList();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        getMyCount();
        getMyPeopleList();
    }

    public void getMyCount() {
        RetrofitService.getRetrofit().getMyCount(MyApplication.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyCountBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MyCountBean myCountBean) {
                        if (null != myCountBean) {
                            tvOther.setText(myCountBean.othernum);
                            tvTotal.setText(myCountBean.teamnum);
                            tvZhitui.setText(myCountBean.straightpushnum);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void getMyPeopleList() {
        RetrofitService.getRetrofit().getMyCustomerList(MyApplication.getUserId(), page, ConstParams.PAGE_COUNT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseListBean<MyCustomerBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseListBean<MyCustomerBean> myCustomerBean) {
                        if (null != myCustomerBean) {
                            customerList.addAll(myCustomerBean.getBeanList());
                            adapter.refreshList(customerList);
                            refreshLayout.finishLoadMore();
                            if (myCustomerBean.getBeanList().size() < ConstParams.PAGE_COUNT) {
                                refreshLayout.setNoMoreData(true);
                            }
                        }
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
