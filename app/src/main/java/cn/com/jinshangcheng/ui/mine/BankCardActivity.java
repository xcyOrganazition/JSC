package cn.com.jinshangcheng.ui.mine;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.adapter.BankCardAdapter;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BankCardBean;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.listener.OnItemViewClickListener;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.utils.ArrayUtils;
import cn.com.jinshangcheng.utils.DensityUtil;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 银行卡
 */
public class BankCardActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    SwipeMenuRecyclerView recyclerView;

    private List<BankCardBean> cardBeanList = new ArrayList<>();
    private BankCardAdapter adapter;
    public static final int RESULT_CODE = 0x24;
    private boolean isFromSelect;//是否是选择银行卡
    private int REQUEST_CODE = 0x28;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_bank_card;
    }

    @Override
    public void initData() {
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
        isFromSelect = getIntent().getBooleanExtra("isFromSelect", false);
        getCardList();//请求卡列表
    }

    //reacyclerView的点击监听
    private OnItemViewClickListener onItemClickListener = new OnItemViewClickListener() {
        @Override
        public void onViewClick(int position, View view) {
            Intent intent = new Intent();
            switch (view.getId()) {

                default:
                    if (isFromSelect) {//选择银行卡
                        intent.putExtra("selectedCard", cardBeanList.get(position));
                        setResult(RESULT_CODE, intent);
                        finish();
                    } else {
                        //编辑银行卡
                        intent = new Intent(BankCardActivity.this, NewCardActivity.class);
                        intent.putExtra("card", cardBeanList.get(position));
                        intent.putExtra("isFromNew", false);//是否新增银行卡
                        startActivityForResult(intent, REQUEST_CODE);
                    }
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

            SwipeMenuItem setDefaultItem = new SwipeMenuItem(mContext)
                    .setBackgroundDrawable(R.drawable.selector_set_default)
//                    .setImage(R.mipmap.ic_action_delete)
                    .setText("默认")
                    .setTextColor(getResources().getColor(R.color.textBlack))
                    .setWidth(width)
                    .setHeight(height);
            SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                    .setBackgroundDrawable(R.drawable.selector_delete)
//                    .setImage(R.mipmap.ic_action_delete)
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(setDefaultItem);// 添加右侧的设为默认按钮
            swipeRightMenu.addMenuItem(deleteItem);// 添加右侧的删除按钮
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
            if (menuPosition == 0) {//默认按钮点击
                setCardDefault(cardBeanList.get(adapterPosition).accountid);
            } else if (menuPosition == 1) {// 删除按钮被点击。
                deleteCard(cardBeanList.get(adapterPosition).accountid);
            }
        }
    };

    public void getCardList() {
        cardBeanList.clear();
        showLoading();
        RetrofitService.getRetrofit().getCardList(MyApplication.getUserId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<BankCardBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<BankCardBean> bankCardList) {
                        if (ArrayUtils.hasContent(bankCardList)) {
                            cardBeanList.addAll(bankCardList);
                            adapter.refreshList(cardBeanList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        dismissLoading();
                    }
                });
    }

    public void deleteCard(String cardId) {
        RetrofitService.getRetrofit().delCard(MyApplication.getUserId(), cardId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.code.equals("0")) {
                            getCardList();
                            showToast("删除成功");
                        } else {
                            showToast(baseBean.message);
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

    //这是某张卡为默认
    public void setCardDefault(String cardId) {
        RetrofitService.getRetrofit().setCardDefault(MyApplication.getUserId(), cardId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.code.equals("0")) {
                            getCardList();
                            showToast("修改成功");
                        } else {
                            showToast(baseBean.message);
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

    @OnClick(R.id.bt_newCard)
    public void onViewClicked() {
        Intent intent = new Intent(BankCardActivity.this, NewCardActivity.class);
        intent.putExtra("isFromNew", true);//是否新增银行卡
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (NewCardActivity.RESULT_CODE == resultCode) {
            getCardList();//重新请求卡列表
        }
    }
}
