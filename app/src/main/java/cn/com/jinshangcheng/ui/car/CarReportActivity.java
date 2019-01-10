package cn.com.jinshangcheng.ui.car;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.adapter.LocusAdapter;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.CarBean;
import cn.com.jinshangcheng.bean.ReportBean;
import cn.com.jinshangcheng.bean.TravelBean;
import cn.com.jinshangcheng.listener.OnItemViewClickListener;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.utils.DateUtils;
import cn.com.jinshangcheng.utils.DensityUtil;
import cn.com.jinshangcheng.utils.GlideUtils;
import cn.com.jinshangcheng.utils.NumberUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 用车报告
 */
public class CarReportActivity extends BaseActivity {

    @BindView(R.id.tv_carLicense)
    TextView tvCarLicense;
    @BindView(R.id.tv_carBrand)
    TextView tvCarBrand;
    @BindView(R.id.tv_carType)
    TextView tvCarType;
    @BindView(R.id.iv_carImg)
    ImageView ivCarImg;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_mileNum)
    TextView tvMileNum;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_mile)
    TextView tvMile;
    @BindView(R.id.tv_oil)
    TextView tvOil;
    @BindView(R.id.tv_ave_fuelNum)
    TextView tvAveFuelNum;
    @BindView(R.id.tv_dccelerateTimes)
    TextView tvDccelerateTimes;
    @BindView(R.id.tv_accelerateTimes)
    TextView tvAccelerateTimes;
    @BindView(R.id.tv_sharpTurnTimes)
    TextView tvSharpTurnTimes;
    @BindView(R.id.tv_hasOverSpeed)
    TextView tvHasOverSpeed;
    @BindView(R.id.tv_maxSpeed)
    TextView tvMaxSpeed;
    @BindView(R.id.tv_averageSpeed)
    TextView tvAverageSpeed;
    @BindView(R.id.recyclerView)
    SwipeMenuRecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    private final int DAY = 1;//日视图
    private final int MONTH = 2;//月视图

    private LocusAdapter adapter;
    private List<TravelBean> travelList;
    private String currentDate;
    private String dayDateCache;//日视图的时间保存
    private int dateType;
    private int page = 1;
    private final int PAGE_SIZE = 5;


    @Override
    public int setContentViewResource() {
        return R.layout.activity_car_report;
    }

    @Override
    public void initData() {
        dateType = DAY;
        page = 1;
        currentDate = DateUtils.getYMDTime(System.currentTimeMillis());
        dayDateCache = currentDate;
        travelList = new ArrayList<>();
        adapter = new LocusAdapter(travelList, this);
        adapter.setOnItemViewClickListener(new OnItemViewClickListener() {
            @Override
            public void onViewClick(int position, View view) {
                Intent intent = new Intent(CarReportActivity.this, TravelActivity.class);
                intent.putExtra("travelBean", travelList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void initView() {
        initTopView();
        tvDate.setText(currentDate);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                if (dateType == DAY) {
                    getDayTravelList();
                } else {
                    getMonthTravelList();
                }
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
        recyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画。
        // 添加滚动监听。
        recyclerView.addOnScrollListener(mOnScrollListener);
        // 设置菜单创建器。
        recyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        recyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        recyclerView.setAdapter(adapter);
        getDateReport();
        getDayTravelList();
    }

    private void initTopView() {
        CarBean bean = MyApplication.getCurrentCarBean();

        tvCarLicense.setText(bean.getPlatenumber());
        tvCarBrand.setText(bean.getBrandname());
//        GlideUtils.loadImage(getApplicationContext(), bean.getBrandpath(), ivBranchImg);
        GlideUtils.loadImage(getApplicationContext(), bean.getBrandpath(), ivCarImg);
        tvCarType.setText(bean.getTypename());
    }


    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {

        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = DensityUtil.dip2px(mContext, 60);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                    .setBackgroundDrawable(R.drawable.selector_delete)
//                    .setImage(R.mipmap.ic_action_delete)
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加右侧的按钮
        }
    };

    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        /**
         * Item的菜单被点击的时候调用。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView#RIGHT_DIRECTION.
         */
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。
            deleteTravel(travelList.get(adapterPosition).travelid, adapterPosition);
        }
    };

    /**
     * 加载更多
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!recyclerView.canScrollVertically(1)) {// 手指不能向上滑动了
                // 判断下是否有数据，如果有数据才去加载更多。
                Logger.w("bottom");
            }
        }
    };

    public void getDayTravelList() {
        if (page == 1) {
            travelList.clear();
            adapter.refreshList(travelList);
        }
        RetrofitService.getRetrofit().getDayTravelList(MyApplication.getCarId(), MyApplication.getUserId(),
                page, PAGE_SIZE, currentDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<TravelBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<TravelBean> travelBeans) {
                        travelList.addAll(travelBeans);
                        adapter.refreshList(travelList);
                        refreshLayout.finishLoadMore();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getMonthTravelList() {
        if (page == 1) {
            travelList.clear();
            adapter.refreshList(travelList);
        }
        RetrofitService.getRetrofit().getMonthTravelList(MyApplication.getCarId(), MyApplication.getUserId(),
                page, PAGE_SIZE, currentDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<TravelBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<TravelBean> travelBeans) {
                        travelList.addAll(travelBeans);
                        adapter.refreshList(travelList);
                        refreshLayout.finishLoadMore();
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

    public void deleteTravel(String travelid, final int position) {

        RetrofitService.getRetrofit().deleteTravel(MyApplication.getCarId(), travelid)
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
                            travelList.remove(position);
                            adapter.refreshList(travelList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast("删除失败，稍后再试");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //获取车辆汇报数据
    public void getDateReport() {
        showLoading();
        io.reactivex.Observable<BaseBean<ReportBean>> observable;
        if (dateType == DAY) {
            observable = RetrofitService.getRetrofit().getCarDateReport(MyApplication.getCarId(), MyApplication.getUserId(),
                    currentDate);
        } else {
            observable = RetrofitService.getRetrofit().getCarMonthReport(MyApplication.getCarId(), MyApplication.getUserId(),
                    currentDate);
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<ReportBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseBean<ReportBean> baseBean) {
                        if (baseBean.code.equals("0") && baseBean.data != null) {
                            refreshReportView(baseBean.data);
                        } else {
                            resetReportView();
                            showToast(baseBean.message);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        resetReportView();
                        dismissLoading();
                    }

                    @Override
                    public void onComplete() {
                        dismissLoading();
                    }
                });
    }

    public void refreshReportView(ReportBean reportBean) {
        tvTotal.setText(NumberUtils.formatDouble(reportBean.fuelcost));//用车费用
        tvMileNum.setText(String.valueOf(NumberUtils.formatDouble(reportBean.duration / 60D)));//用车时间
        tvMile.setText(NumberUtils.formatDouble(reportBean.mileage));//行驶里程
        tvOil.setText(NumberUtils.formatDouble(reportBean.fuel));//燃烧油量
        tvAveFuelNum.setText(NumberUtils.formatDouble(reportBean.averagefuel));//油耗
        tvDccelerateTimes.setText(String.format("急刹车次数：%s次", reportBean.dcceleratetimes));
        tvAccelerateTimes.setText(String.format("急加速次数：%s次", reportBean.acceleratetimes));
        tvSharpTurnTimes.setText(String.format("急转弯次数：%s次", reportBean.sharpturntimes));
        tvHasOverSpeed.setText(String.format("超速次数：%s次", reportBean.hasoverspeed));
        tvMaxSpeed.setText(String.format("最高车速：%skm/h", reportBean.maxspeed));
        tvAverageSpeed.setText(String.format("平均车速：%skm/h", NumberUtils.formatDouble(reportBean.averagespeed)));
    }

    //重置
    public void resetReportView() {
        tvTotal.setText("暂无");//用车费用
        tvMileNum.setText("暂无");//用车时间
        tvMile.setText("暂无");//行驶里程
        tvOil.setText("暂无");//燃烧油量
        tvAveFuelNum.setText("暂无");//平均油耗
        tvDccelerateTimes.setText("急刹车次数：0次");
        tvAccelerateTimes.setText("急加速次数：0次");
        tvSharpTurnTimes.setText("急转弯次数：0次");
        tvHasOverSpeed.setText("超速次数：0次");
        tvMaxSpeed.setText("最高车速：0km/h");
        tvAverageSpeed.setText("平均车速：0km/h");

    }

    @OnClick({R.id.iv_previousDay, R.id.iv_nextDay, R.id.tv_date})
    public void onViewClicked(View view) {
        Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");
        page = 1;//重置分页数据
        switch (view.getId()) {
            case R.id.iv_previousDay:
                try {
                    if (dateType == DAY) {
                        calendar.setTime(sdfDay.parse(currentDate));
                        calendar.add(Calendar.DAY_OF_MONTH, -1);
                        currentDate = sdfDay.format(calendar.getTime());
                        getDayTravelList();
                    } else if (dateType == MONTH) {
                        calendar.setTime(sdfMonth.parse(currentDate));
                        calendar.add(Calendar.MONTH, -1);
                        currentDate = sdfMonth.format(calendar.getTime());
                        getMonthTravelList();
                    }
                    tvDate.setText(currentDate);
                    getDateReport();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.iv_nextDay:
                try {
                    if (dateType == DAY) {
                        calendar.setTime(sdfDay.parse(currentDate));
                        calendar.add(Calendar.DAY_OF_MONTH, +1);
                        currentDate = sdfDay.format(calendar.getTime());
                        getDayTravelList();
                    } else if (dateType == MONTH) {
                        calendar.setTime(sdfMonth.parse(currentDate));
                        calendar.add(Calendar.MONTH, +1);
                        currentDate = sdfMonth.format(calendar.getTime());
                        getMonthTravelList();
                    }
                    tvDate.setText(currentDate);
                    getDateReport();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                break;
            case R.id.tv_date://点击时间弹出时间选择框
                try {
                    calendar.setTime(sdfDay.parse(currentDate));
                    if (dateType == DAY) {
                        new DatePickerDialog(CarReportActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.set(year, month, dayOfMonth);
                                currentDate = sdfDay.format(calendar1.getTime());
                                tvDate.setText(currentDate);
                                getDayTravelList();
                                getDateReport();
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    /**
     * 日月切换是 文案调整
     */
    public void refreshDateText() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");
        try {
            if (dateType == DAY) {
                currentDate = dayDateCache;//去除日视图的时间缓存
//                calendar.setTime(sdfMonth.parse(currentDate));
//                currentDate = sdfDay.format(calendar.getTime());

            } else if (dateType == MONTH) {
                dayDateCache = currentDate;//保存日视图时间
                calendar.setTime(sdfDay.parse(currentDate));
                currentDate = sdfMonth.format(calendar.getTime());
            }
            tvDate.setText(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @OnCheckedChanged(R.id.rb_day)
    public void onDayChecked(boolean checked) {
        if (checked) {
            dateType = DAY;
            page = 1;
            refreshDateText();
            getDateReport();
            getDayTravelList();
        }
    }

    @OnCheckedChanged(R.id.rb_month)
    public void onMonthChecked(boolean checked) {
        if (checked) {
            dateType = MONTH;
            page = 1;
            refreshDateText();
            getDateReport();
            getMonthTravelList();
        }

    }


}
