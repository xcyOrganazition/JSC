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
import cn.com.jinshangcheng.bean.Goods;
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
    private GoodsAdapter adapter;
    private TextView tvGoodsNum;
    private double totalPrice;//选择的商品总价格
    private int totalNum;//选择的商品数量

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
                        addGoods(goodsList.get(position));
                        break;
                    default://条目被点击 查看商品详情:
//                        Logger.w("条目点击" + goodsList.get(position).toString());
                        Intent intent = new Intent(getHoldingActivity(), GoodsDetailActivity.class);
                        startActivity(intent);
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
        for (String goodsId : selectGoodsMap.keySet()) {
            for (Goods goods : goodsList) {
                if (goods.getGoodsid().equals(goodsId)) {
                    totalPrice += goods.getPrice() * selectGoodsMap.get(goodsId);
                }
            }
            totalNum += selectGoodsMap.get(goodsId);
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
        refreshTotalPrice();
        getGoodsList();
    }

    public void initShopCartView() {
        tvEmptyMsg.setVisibility(selectGoodsMap.isEmpty() ? View.VISIBLE : View.GONE);
        llGoodsList.setVisibility(selectGoodsMap.isEmpty() ? View.GONE : View.VISIBLE);

        for (final String goodsId : selectGoodsMap.keySet()) {
            Goods curGoods = null;
            for (Goods goods : goodsList) {
                if (goods.getGoodsid().equals(goodsId)) {
                    curGoods = goods;
                }
            }
            if (curGoods != null) {
                final View itemView = View.inflate(getHoldingActivity(), R.layout.item_shop_cart, null);
                TextView tvCurGoodsName = itemView.findViewById(R.id.tv_curGoodsName);
                final TextView tvCurGoodPrice = itemView.findViewById(R.id.tv_curGoodPrice);
                final TextView tvCurGoodsNum = itemView.findViewById(R.id.tv_curGoodsNum);
                ImageView ivDeleteGoods = itemView.findViewById(R.id.iv_deleteGoods);
                ImageView ivAddGoods = itemView.findViewById(R.id.iv_addGoods);
                itemView.setTag(curGoods);
                tvCurGoodsName.setText(curGoods.getGoodsname());
                tvCurGoodsNum.setText(String.valueOf(selectGoodsMap.get(goodsId)));
                tvCurGoodPrice.setText(String.format("%s元", curGoods.getPrice() * selectGoodsMap.get(goodsId)));
                ivDeleteGoods.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Goods goods = (Goods) itemView.getTag();
                        deleteGoods(goods);
                        if (selectGoodsMap.containsKey(goodsId)) {
                            tvCurGoodsNum.setText(String.valueOf(selectGoodsMap.get(goodsId)));
                            tvCurGoodPrice.setText(String.format("%s元", goods.getPrice() * selectGoodsMap.get(goodsId)));
                        } else {
                            llGoodsList.removeView(itemView);
                            tvEmptyMsg.setVisibility(selectGoodsMap.isEmpty() ? View.VISIBLE : View.GONE);
                            llGoodsList.setVisibility(selectGoodsMap.isEmpty() ? View.GONE : View.VISIBLE);
                        }

                    }
                });
                ivAddGoods.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Goods goods = (Goods) itemView.getTag();
                        addGoods(goods);
                        tvCurGoodsNum.setText(String.valueOf(selectGoodsMap.get(goodsId)));
                        tvCurGoodPrice.setText(String.format("%s元", goods.getPrice() * selectGoodsMap.get(goodsId)));

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

//    /**
//     * 请求车型 父列表
//     */
//    private void getCarTypeData() {
//        showLoading();
//        carTypeList.clear();
//        CarBrandInfoSearch.getInstance().GetCarTypeResult(carBrandsBean.brandId, new OnResultListener.CarTypeResultListener() {
//
//            @Override
//            public void onCarTypeResult(CarTypeResult carTypeResult, boolean isError, Throwable throwable) {
//                if (!isError) {
////                    Logger.w("车型   " + carTypeResult.getData().get(0).getCarTypes());
//                    dismissLoading();
//                    for (int i = 0; i < carTypeResult.getData().size(); i++) {
//                        carTypeList.addAll(carTypeResult.getData().get(i).getCarTypes());
//                    }
//                    selectCarTypeWindow = new SelectCarTypeWindow(getContext(), carBrandsBean, carTypeList);
////                    selectCarTypeWindow.showAtLocation(root, Gravity.RIGHT, 0, 0);
//                }
//            }
//        });
//    }


    @OnClick({R.id.tv_goToPay, R.id.iv_shoppingCart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_goToPay://去结算
                if (shopCartView.getVisibility() == View.VISIBLE) {
                    shopCartView.setVisibility(View.GONE);
                }
                if (selectGoodsMap.isEmpty()) {
                    Toast.makeText(getContext(), "请添加商品到购物车", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(getHoldingActivity(), OrderDetailActivity.class);
                intent.putExtra("selectGoodsMap", (Serializable) selectGoodsMap);
                intent.putExtra("goodsList", (Serializable) goodsList);
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

}
