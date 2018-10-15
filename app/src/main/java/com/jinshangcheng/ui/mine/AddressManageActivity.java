package com.jinshangcheng.ui.mine;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.jinshangcheng.R;
import com.jinshangcheng.adapter.AddressAdapter;
import com.jinshangcheng.base.BaseActivity;
import com.jinshangcheng.bean.AddressBean;
import com.jinshangcheng.listener.OnItemViewClickListener;
import com.jinshangcheng.utils.DensityUtil;
import com.jinshangcheng.widget.ListViewDecoration;
import com.jinshangcheng.widget.TittleBar;
import com.orhanobut.logger.Logger;
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
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 地址管理
 */
public class AddressManageActivity extends BaseActivity {

    @BindView(R.id.tittleBar)
    TittleBar tittleBar;
    @BindView(R.id.recyclerView)
    SwipeMenuRecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private AddressAdapter adapter;
    private ArrayList<AddressBean> data;

    //reacyclerView的点击监听
    private OnItemViewClickListener onItemClickListener = new OnItemViewClickListener() {
        @Override
        public void onViewClick(int position, View view) {
            switch (view.getId()) {
                case R.id.tv_edit:
                    Logger.w("编辑");
                    break;
                default:
                    Intent intent = new Intent(AddressManageActivity.this, EditAddressActivity.class);
                    intent.putExtra("address", data.get(position));
                    startActivity(intent);
                    break;

            }
        }
    };

    @Override
    public int setContentViewResource() {
        return R.layout.activity_address_manage;
    }

    @Override
    public void initData() {
        data = new ArrayList<>();
        data.add(new AddressBean("家", "132123123123", "北京市海淀区18号楼101", "123", true));
        data.add(new AddressBean("张飞", "132123123123", "北京市海淀区18号楼101", "123", false));
        data.add(new AddressBean("赵云", "132123123123", "北京市海淀区18号楼101", "123", false));
        data.add(new AddressBean("李云龙", "132123123123", "北京市海淀区18号楼101", "123", false));
        adapter = new AddressAdapter(this, data);
    }

    @Override
    public void initView() {
        tittleBar.showNavigation();
        tittleBar.setTittle("地址管理");
//        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);//不满一页不允许加载更多
        refreshLayout.setEnableLoadMore(false);//不允许加载更多
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Logger.w("refresh");
            }
        });

        initRecyclerView();


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

    //添加新地址
    @OnClick(R.id.bt_newAddress)
    public void onViewClicked() {
        Intent intent = new Intent(AddressManageActivity.this, EditAddressActivity.class);
//        intent.putExtra("address", data.get(position));
        startActivity(intent);
    }
}
