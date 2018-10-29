package cn.com.jinshangcheng.ui.square;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.adapter.GoodsAdapter;
import cn.com.jinshangcheng.base.BaseFragment;
import cn.com.jinshangcheng.bean.Goods;
import cn.com.jinshangcheng.listener.OnItemViewClickListener;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.utils.ArrayUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import platform.cston.httplib.bean.CarTypeResult;

/**
 * 广场模块
 */
public class SquareFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_totalMoney)
    TextView tvTotalMoney;


    private static SquareFragment fragment;

    Unbinder unbinder;

    private List<CarTypeResult.DataEntity.CarTypesEntity> carTypeList = new ArrayList();//汽车类型

    private List<Goods> goodsList;
    private List<Goods> selectGoodsList;//选择的商品
    private GoodsAdapter adapter;
    private double totalPrice;//总商品价格
    private TextView tvGoodsNum;

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
        goodsList = new ArrayList<>();
        selectGoodsList = new ArrayList<>();
        adapter = new GoodsAdapter(goodsList, getContext());
        //条目点击
        adapter.setOnItemViewClickListener(new OnItemViewClickListener() {
            @Override
            public void onViewClick(int position, View view) {
                switch (view.getId()) {
                    case R.id.tv_addGoods://添加商品
                        Logger.w("添加商品" + goodsList.get(position).toString());
                        selectGoodsList.add(goodsList.get(position));
                        refreshTotalPrice();
                        break;
                    default://条目被点击 查看商品详情:
                        Logger.w("条目点击" + goodsList.get(position).toString());
                        break;
                }
            }
        });
    }

    //刷新总商品价格
    public void refreshTotalPrice() {
        totalPrice = 0;
        for (Goods goods : selectGoodsList) {
            totalPrice += goods.getPrice();
        }
        tvTotalMoney.setText(String.format("合计：%s 元", totalPrice));
        tvGoodsNum.setText(String.valueOf(selectGoodsList.size()));
        tvGoodsNum.setVisibility(ArrayUtils.hasContent(selectGoodsList) ? View.VISIBLE : View.INVISIBLE);


    }

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        refreshTotalPrice();
        getGoodsList();

    }

    //请求商品列表
    public void getGoodsList() {
        String userId = "12";
        RetrofitService.getRetrofit().getGoodsList(userId)
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
                break;
            case R.id.iv_shoppingCart://购物车图片:
                break;
        }
    }
}
