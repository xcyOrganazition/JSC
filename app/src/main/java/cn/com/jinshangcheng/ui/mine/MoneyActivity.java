package cn.com.jinshangcheng.ui.mine;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.adapter.BillAdapter;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BaseListBean;
import cn.com.jinshangcheng.bean.IncomeBean;
import cn.com.jinshangcheng.bean.WithdrawBean;
import cn.com.jinshangcheng.config.ConstParams;
import cn.com.jinshangcheng.net.RetrofitService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的收益
 */
public class MoneyActivity extends BaseActivity {

    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_income)
    TextView tvIncome;
    @BindView(R.id.tv_withdraw)
    TextView tvWithdraw;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private int page;
    private List<WithdrawBean> billList;
    private BillAdapter adapter;
    private int emptyViewHeight;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_money;
    }

    @Override
    public void initData() {
        billList = new ArrayList<>();
        page = 1;
    }

    @Override
    public void initView() {
        refreshLayout.setEnableRefresh(false);//禁止刷新
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);//不满一页禁止加载
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                getBillList();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ViewTreeObserver viewObserver = refreshLayout.getViewTreeObserver();
        viewObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                refreshLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                emptyViewHeight = refreshLayout.getHeight();
                Logger.w("height PX" + emptyViewHeight);
                Logger.w("height DP" + refreshLayout.getMeasuredHeight());
                adapter = new BillAdapter(getApplicationContext(), billList, emptyViewHeight);
                recyclerView.setAdapter(adapter);

            }
        });
        getIncome();
        getBillList();
    }

    @OnClick({R.id.tv_history, R.id.bt_requestDraw})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_history://提现记录
                intent = new Intent(MoneyActivity.this, WithDrawActivity.class);
                break;
            case R.id.bt_requestDraw://申请提现
                intent = new Intent(MoneyActivity.this, ApplyDrawActivity.class);
                break;
        }
        startActivity(intent);
    }


    public void getIncome() {
        RetrofitService.getRetrofit().getDetailCount(MyApplication.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IncomeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(IncomeBean bean) {
                        tvTotal.setText(bean.getTotalIncome());
                        tvIncome.setText(bean.getRemainIncome());
                        tvWithdraw.setText(bean.getAllWithdraw());
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

    //获取账单变更列表
    public void getBillList() {
        RetrofitService.getRetrofit().getBillList(MyApplication.getUserId(), page, ConstParams.PAGE_COUNT)
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
                            billList.addAll(baseBean.getBeanList());
                        }
                        if (null != adapter) {
                            adapter.refreshListData(billList, emptyViewHeight);
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

}
