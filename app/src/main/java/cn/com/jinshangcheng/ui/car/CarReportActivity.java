package cn.com.jinshangcheng.ui.car;

import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.adapter.LocusAdapter;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.utils.DensityUtil;

/**
 * 用车报告
 */
public class CarReportActivity extends BaseActivity {

    @BindView(R.id.tv_carLicense)
    EditText tvCarLicense;
    @BindView(R.id.tv_carBrand)
    TextView tvCarBrand;
    @BindView(R.id.tv_carType)
    TextView tvCarType;
    @BindView(R.id.iv_carImg)
    ImageView ivCarImg;
    @BindView(R.id.rg_viewType)
    RadioGroup rgViewType;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_mileNum)
    TextView tvMileNum;
    @BindView(R.id.tv_fuelNum)
    TextView tvFuelNum;
    @BindView(R.id.tv_oil)
    TextView tvOil;
    @BindView(R.id.tv_ave_fuelNum)
    TextView tvAveFuelNum;
    @BindView(R.id.recyclerView)
    com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    com.scwang.smartrefresh.layout.SmartRefreshLayout refreshLayout;


    private LocusAdapter adapter;
    private List locusList;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_car_report;
    }

    @Override
    public void initData() {
        locusList = new ArrayList();
        locusList.add("");
        locusList.add("");

        adapter = new LocusAdapter(locusList, this);
    }

    @Override
    public void initView() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                locusList.add("");
                locusList.add("");
                adapter.refreshList(locusList);
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
        recyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画。
        // 添加滚动监听。
        recyclerView.addOnScrollListener(mOnScrollListener);
        // 设置菜单创建器。
        recyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        recyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
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


    @OnClick({R.id.iv_previousDay, R.id.iv_nextDay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_previousDay:
                break;
            case R.id.iv_nextDay:
                break;
        }
    }
}
