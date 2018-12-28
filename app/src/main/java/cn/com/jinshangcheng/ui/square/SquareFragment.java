package cn.com.jinshangcheng.ui.square;


import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.adapter.GoodsAdapter;
import cn.com.jinshangcheng.base.BaseFragment;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.Goods;
import cn.com.jinshangcheng.bean.GoodsItemBean;
import cn.com.jinshangcheng.listener.OnItemViewClickListener;
import cn.com.jinshangcheng.net.RetrofitService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 广场模块 （商城）
 */
public class SquareFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_totalMoney)
    TextView tvTotalMoney;
    @BindView(R.id.parentPanel)
    ConstraintLayout parentPanel;
    @BindView(R.id.tv_emptyMsg)
    TextView tvEmptyMsg;
    @BindView(R.id.ll_goodsList)
    LinearLayout llGoodsList;
    @BindView(R.id.shopCartView)
    LinearLayout shopCartView;


    private static SquareFragment fragment;
    private List<Goods> goodsList;
    private Map<String, Integer> selectGoodsMap;//选择的商品<GoodsId,GoodsNum>
    public static List<GoodsItemBean> allGoodsItems;
    private GoodsAdapter adapter;
    private TextView tvGoodsNum;
    private double totalPrice;//选择的商品总价格
    private int totalNum;//选择的商品数量
    private static final int REQUEST_CODE = 0x668;//选择的商品数量

    public SquareFragment() {
        // Required empty public constructor
    }

    public static SquareFragment getInstance() {
        if (fragment == null) {
            synchronized (SquareFragment.class) {
                if (fragment == null) {
                    fragment = new SquareFragment();
                }
            }
        }
        return fragment;
    }


    @Override
    public View createView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_square, null, false);
        tvGoodsNum = view.findViewById(R.id.tv_goodsNum);
        return view;
    }

    @Override
    public void initData() {
        selectGoodsMap = new HashMap<>();
        goodsList = new ArrayList<>();
        allGoodsItems = new ArrayList<>();
        adapter = new GoodsAdapter(goodsList, getContext());
        //条目点击
        adapter.setOnItemViewClickListener(new OnItemViewClickListener() {
            @Override
            public void onViewClick(int position, View view) {
                if (shopCartView.getVisibility() == View.VISIBLE) {
                    shopCartView.setVisibility(View.GONE);
                    return;
                }
                switch (view.getId()) {
                    case R.id.tv_addGoods://添加商品
//                        Logger.w("添加商品" + goodsList.get(position).toString());
                        for (GoodsItemBean goodsItemBean : allGoodsItems) {
                            if (goodsItemBean.goodsid.equals(goodsList.get(position).getGoodsid())) {
                                updateGoodsNum(goodsItemBean, goodsItemBean.quantity + 1);
                                return;
                            }
                        }
                        addGoodsToCart(goodsList.get(position));
                        break;
                    default://条目被点击 查看商品详情:
//                        Logger.w("条目点击" + goodsList.get(position).toString());
                        Intent intent = new Intent(getHoldingActivity(), GoodsDetailActivity.class);
                        intent.putExtra("goodsBean", goodsList.get(position));
                        intent.putExtra("allGoodsItems", (Serializable) allGoodsItems);
                        startActivityForResult(intent, REQUEST_CODE);
                        break;
                }
            }
        });
    }

    //添加商品
    public void addGoods(Goods goods) {
        String goodsId = goods.getGoodsid();
        int goodsNum = 1;
        if (selectGoodsMap.containsKey(goodsId)) {
            goodsNum = selectGoodsMap.get(goodsId);
            goodsNum++;
        }
        selectGoodsMap.put(goodsId, goodsNum);
        refreshTotalPrice();
    }

    //移除商品
    public void deleteGoods(Goods goods) {
        String goodsId = goods.getGoodsid();
        int goodsNum = 0;
        if (selectGoodsMap.containsKey(goodsId)) {
            goodsNum = selectGoodsMap.get(goodsId);
            goodsNum--;
        }
        selectGoodsMap.put(goodsId, goodsNum);
        if (selectGoodsMap.get(goodsId) == 0) {
            selectGoodsMap.remove(goodsId);
        }
        refreshTotalPrice();
    }

    //刷新总商品价格 和数量
    public void refreshTotalPrice() {
        totalPrice = 0;
        totalNum = 0;
        for (GoodsItemBean goodsItemBean : allGoodsItems) {
            totalPrice += goodsItemBean.goods.getPrice() * goodsItemBean.quantity;
            totalNum += goodsItemBean.quantity;
        }
        tvTotalMoney.setText(String.format("合计：%s 元", totalPrice));
        tvGoodsNum.setText(String.valueOf(totalNum));
        tvGoodsNum.setVisibility(totalNum == 0 ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.performClick();
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_MOVE && shopCartView.getVisibility() == View.VISIBLE) {
                    shopCartView.setVisibility(View.GONE);
                    return true;
                }
                return false;
            }
        });
        getGoodsList();
        getAllGoodsItem();
    }

    public void initShopCartView() {
        tvEmptyMsg.setVisibility(allGoodsItems.isEmpty() ? View.VISIBLE : View.GONE);
        llGoodsList.setVisibility(allGoodsItems.isEmpty() ? View.GONE : View.VISIBLE);
        llGoodsList.removeAllViews();
        for (final GoodsItemBean goodsItemBean : allGoodsItems) {
            Goods curGoods = goodsItemBean.goods;
            if (curGoods != null) {
                final View itemView = View.inflate(getHoldingActivity(), R.layout.item_shop_cart, null);
                TextView tvCurGoodsName = itemView.findViewById(R.id.tv_curGoodsName);
                final TextView tvCurGoodPrice = itemView.findViewById(R.id.tv_curGoodPrice);
                final TextView tvCurGoodsNum = itemView.findViewById(R.id.tv_curGoodsNum);
                ImageView ivDeleteGoods = itemView.findViewById(R.id.iv_deleteGoods);
                ImageView ivAddGoods = itemView.findViewById(R.id.iv_addGoods);
                itemView.setTag(goodsItemBean);
                tvCurGoodsName.setText(curGoods.getGoodsname());
                tvCurGoodsNum.setText(String.valueOf(goodsItemBean.quantity));
                tvCurGoodPrice.setText(String.format("%s元", curGoods.getPrice() * goodsItemBean.quantity));
                ivDeleteGoods.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GoodsItemBean clickGoodsItemBean = (GoodsItemBean) itemView.getTag();
//                        deleteGoods(goods);
                        int endNumber = clickGoodsItemBean.quantity - 1;
                        if (endNumber == 0) {
                            deleteGoodsItem(clickGoodsItemBean.cartitemid);
                        } else {
                            updateGoodsNum(clickGoodsItemBean, endNumber);
                        }
                        if (clickGoodsItemBean.quantity != 0) {
                            tvCurGoodsNum.setText(String.valueOf(clickGoodsItemBean.quantity));
                            tvCurGoodPrice.setText(String.format("%s元", clickGoodsItemBean.goods.getPrice() * clickGoodsItemBean.quantity));
                        }
//                        else {
//                            llGoodsList.removeView(itemView);
//                            tvEmptyMsg.setVisibility(selectGoodsMap.isEmpty() ? View.VISIBLE : View.GONE);
//                            llGoodsList.setVisibility(selectGoodsMap.isEmpty() ? View.GONE : View.VISIBLE);
//                        }
                    }
                });
                ivAddGoods.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GoodsItemBean clickGoodsItemBean = (GoodsItemBean) itemView.getTag();
//                        addGoods(goods);
                        int endNum = clickGoodsItemBean.quantity + 1;
                        updateGoodsNum(clickGoodsItemBean, endNum);
                        tvCurGoodsNum.setText(String.valueOf(clickGoodsItemBean.quantity));
                        tvCurGoodPrice.setText(String.format("%s元", clickGoodsItemBean.goods.getPrice() * clickGoodsItemBean.quantity));

                    }
                });
                llGoodsList.addView(itemView);
            }
        }
    }

    //请求商品列表
    public void getGoodsList() {
        RetrofitService.getRetrofit().getGoodsList(MyApplication.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Goods>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Goods> list) {
                        goodsList.addAll(list);
                        adapter.refreshList(goodsList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @OnClick({R.id.tv_goToPay, R.id.iv_shoppingCart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_goToPay://去结算
                if (shopCartView.getVisibility() == View.VISIBLE) {
                    shopCartView.setVisibility(View.GONE);
                }
                if (allGoodsItems.isEmpty()) {
                    Toast.makeText(getContext(), "请添加商品到购物车", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(getHoldingActivity(), OrderDetailActivity.class);
                intent.putExtra("allGoodsItems", (Serializable) allGoodsItems);
//                intent.putExtra("goodsList", (Serializable) goodsList);
                startActivity(intent);
                break;
            case R.id.iv_shoppingCart://购物车图片:
                llGoodsList.removeAllViews();
                initShopCartView();
                if (shopCartView.getVisibility() == View.VISIBLE) {
                    shopCartView.setVisibility(View.GONE);
                } else {
                    shopCartView.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    /**
     * 购物车添加商品 （首次添加调用）
     */
    public void getAllGoodsItem() {
        RetrofitService.getRetrofit().getAllGoodsItem(MyApplication.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GoodsItemBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<GoodsItemBean> goodsItemBeans) {
                        allGoodsItems = goodsItemBeans;
                        refreshTotalPrice();
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

    /**
     * 购物车添加商品 （首次添加调用）
     *
     * @param goods
     */
    public void addGoodsToCart(Goods goods) {
        RetrofitService.getRetrofit().addGoodsToCart(MyApplication.getUserId(), goods.getGoodsid(), 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GoodsItemBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<GoodsItemBean> goodsItemBeans) {
                        allGoodsItems = goodsItemBeans;
                        refreshTotalPrice();
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

    /**
     * 修改购物车商品数量 （当购物车中有商品时调用）
     */
    public void updateGoodsNum(final GoodsItemBean goodsItemBean, final int num) {
        RetrofitService.getRetrofit().updateGoodsNum(MyApplication.getUserId(), goodsItemBean.cartitemid, num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        if ("0".equals(baseBean.code)) {
                            for (GoodsItemBean goodsItem : allGoodsItems) {
                                if (goodsItem.cartitemid.equals(goodsItemBean.cartitemid)) {
                                    goodsItem.quantity = num;
                                }
                            }
                            initShopCartView();
                            refreshTotalPrice();
                        } else {
                            getHoldingActivity().showToast(baseBean.message);
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

    /**
     * 根据id删除购物车单品 (当选择商品数量只有一个的时候调用)
     *
     * @param cartitemid
     */
    public void deleteGoodsItem(String cartitemid) {
        RetrofitService.getRetrofit().deleteGoodsItem(MyApplication.getUserId(), cartitemid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GoodsItemBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<GoodsItemBean> goodsItemBeans) {
                        allGoodsItems = goodsItemBeans;
                        initShopCartView();
                        refreshTotalPrice();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == GoodsDetailActivity.RESULT_CODE) {
            if (data != null) {
                allGoodsItems = (List<GoodsItemBean>) data.getSerializableExtra("allGoodsItems");
                initShopCartView();
                refreshTotalPrice();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshTotalPrice();
    }
}
