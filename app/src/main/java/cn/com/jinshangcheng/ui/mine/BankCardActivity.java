package cn.com.jinshangcheng.ui.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.adapter.BankCardAdapter;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BankCardBean;
import cn.com.jinshangcheng.listener.OnItemViewClickListener;
import cn.com.jinshangcheng.utils.DensityUtil;
import cn.com.jinshangcheng.widget.ListViewDecoration;

/**
 * 银行卡
 */
public class BankCardActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    SwipeMenuRecyclerView recyclerView;

    private List<BankCardBean> cardBeanList = new ArrayList<>();
    private BankCardAdapter adapter;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_bank_card;
    }

    @Override
    public void initData() {
        cardBeanList.add(new BankCardBean());
        cardBeanList.add(new BankCardBean());
        cardBeanList.add(new BankCardBean());
        cardBeanList.add(new BankCardBean());
        adapter = new BankCardAdapter(this, cardBeanList);

    }

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画。
//        recyclerView.addItemDecoration(new ListViewDecoration());//分割线
        // 添加滚动监听。
        recyclerView.addOnScrollListener(mOnScrollListener);
        // 设置菜单创建器。
        recyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        recyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        adapter.setOnItemClickListener(onItemClickListener);
        recyclerView.setAdapter(adapter);

    }

    //reacyclerView的点击监听
    private OnItemViewClickListener onItemClickListener = new OnItemViewClickListener() {
        @Override
        public void onViewClick(int position, View view) {
            switch (view.getId()) {

                default:
                    Intent intent = new Intent(BankCardActivity.this, NewCardActivity.class);
                    intent.putExtra("card", cardBeanList.get(position));
                    startActivity(intent);
                    break;

            }
        }
    };

    /**
     * 滑动隐藏删除
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

    @OnClick(R.id.bt_newCard)
    public void onViewClicked() {
        Intent intent = new Intent(BankCardActivity.this, NewCardActivity.class);
        startActivity(intent);
    }
}
