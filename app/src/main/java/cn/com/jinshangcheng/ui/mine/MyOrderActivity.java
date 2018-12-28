package cn.com.jinshangcheng.ui.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.adapter.MyOrderAdapter;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.BaseListBean;
import cn.com.jinshangcheng.bean.OrderBean;
import cn.com.jinshangcheng.config.ConstParams;
import cn.com.jinshangcheng.listener.OnItemViewClickListener;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.utils.ArrayUtils;
import cn.com.jinshangcheng.widget.CheckPaymentDialog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyOrderActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    MyOrderAdapter adapter;
    List<OrderBean> orderList;
    public int page = 1;
    OrderBean selectOrderBean;
    private CheckPaymentDialog dialog;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_my_order;
    }

    @Override
    public void initData() {
        orderList = new ArrayList<>();

        adapter = new MyOrderAdapter(orderList, this);
        adapter.setOnItemViewClickListener(new OnItemViewClickListener() {
            @Override
            public void onViewClick(int position, View view) {
                selectOrderBean = orderList.get(position);
                dialog = new CheckPaymentDialog();
                dialog.show(getFragmentManager(), "");

            }
        });

    }

    @Override
    public void initView() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                getOrderList();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                orderList.clear();
                getOrderList();

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //解决数据加载不完的问题
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        recyclerView.setFocusable(false);
        recyclerView.setAdapter(adapter);
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        refreshLayout.autoRefresh();


    }

    public void getOrderList() {

        RetrofitService.getRetrofit().getMyOrders(MyApplication.getUserId(), page, ConstParams.PAGE_COUNT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseListBean<OrderBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseListBean<OrderBean> orderBeanBaseListBean) {
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                        if (orderBeanBaseListBean.getBeanList() != null) {
                            orderList.addAll(orderBeanBaseListBean.getBeanList());
                            adapter.refresData(orderList);
                        }
                        if (page == 1 && !ArrayUtils.hasContent(orderBeanBaseListBean.getBeanList())) {
                            showToast("暂无订单");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void payByPayCode(String code) {
        RetrofitService.getRetrofit().payByPayCode(MyApplication.getUserId(), selectOrderBean.getOrderid(), code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        showToast(baseBean.message);
                        if ("0".equals(baseBean.code)) {
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        getOrderList();
                    }
                });
    }

}
