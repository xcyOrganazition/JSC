package cn.com.jinshangcheng.ui.mine;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.adapter.CarManageAdapter;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.CarBean;
import cn.com.jinshangcheng.listener.OnItemViewClickListener;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.ui.login.BindBoxActivity;
import cn.com.jinshangcheng.utils.DensityUtil;
import cn.com.jinshangcheng.widget.ListViewDecoration;
import cn.com.jinshangcheng.widget.TittleBar;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 车辆管理
 */
public class CarManageActivity extends BaseActivity {

    @BindView(R.id.tittleBar)
    TittleBar tittleBar;
    @BindView(R.id.recyclerView)
    SwipeMenuRecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private CarManageAdapter adapter;
    private ArrayList<CarBean> carList;

    //reacyclerView的点击监听
    private OnItemViewClickListener onItemClickListener = new OnItemViewClickListener() {
        Intent intent = null;

        @Override
        public void onViewClick(int position, View view) {
            switch (view.getId()) {

                case R.id.tv_bind://立即绑定
                    intent = new Intent(CarManageActivity.this, BindBoxActivity.class);
                    intent.putExtra("carId", carList.get(position).getCarid());
                    startActivity(intent);
                    break;
                case R.id.tv_unbind://解除绑定
                    break;
                case R.id.tv_stealth://隐身管理
                    intent = new Intent(CarManageActivity.this, StealthManageActivity.class);
                    intent.putExtra("carId", carList.get(position).getCarid());
                    startActivity(intent);
                    break;
                default://点击条目 编辑车辆
                    intent = new Intent(CarManageActivity.this, AddCarActivity.class);
                    startActivity(intent);
            }
        }
    };


    @Override
    public int setContentViewResource() {
        return R.layout.activity_car_manage;
    }

    @Override
    public void initData() {
        carList = new ArrayList<>();
        adapter = new CarManageAdapter(this, carList);
    }

    @Override
    public void initView() {
        tittleBar.showNavigation();
        tittleBar.setTittle("车辆管理");
//        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);//不满一页不允许加载更多
        refreshLayout.setEnableLoadMore(false);//不允许加载更多
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loadCarList();
            }
        });
        initRecyclerView();
        refreshLayout.autoRefresh();

    }

    /**
     * 配置Recycler相关
     */
    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画。
        recyclerView.addItemDecoration(new ListViewDecoration());//分割线
        // 添加滚动监听。
        recyclerView.addOnScrollListener(mOnScrollListener);
        // 设置菜单创建器。
        recyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        recyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        adapter.setOnItemClickListener(onItemClickListener);
        recyclerView.setAdapter(adapter);
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
            if (menuPosition == 0) {// 删除按钮被点击。
            }
        }
    };

    //请求车辆列表
    private void loadCarList() {
        RetrofitService.getRetrofit().getCarList(MyApplication.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<ArrayList<CarBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<ArrayList<CarBean>> arrayListBaseBean) {
                        if (arrayListBaseBean.code.equals("0") && null != arrayListBaseBean.data) {
                            carList = arrayListBaseBean.data;
                            adapter.refreshList(carList);
                        } else {
                            showToast(arrayListBaseBean.errorMsg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        refreshLayout.finishRefresh();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        refreshLayout.finishRefresh();
                    }
                });
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!recyclerView.canScrollVertically(1)) {// 手指不能向上滑动了
            }
        }
    };

    //添加新车辆
    @OnClick(R.id.bt_newCar)
    public void onViewClicked() {
        Intent intent = new Intent(CarManageActivity.this, AddCarActivity.class);
//        intent.putExtra("address", data.get(position));
        startActivity(intent);
    }
}