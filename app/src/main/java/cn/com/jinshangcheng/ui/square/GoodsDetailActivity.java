package cn.com.jinshangcheng.ui.square;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.Goods;
import cn.com.jinshangcheng.bean.GoodsItemBean;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.utils.GlideUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 商品详情
 */
public class GoodsDetailActivity extends BaseActivity {
    @BindView(R.id.iv_goodsImg)
    ImageView ivGoodsImg;
    @BindView(R.id.iv_goodsNameContent)
    TextView ivGoodsNameContent;
    @BindView(R.id.iv_goodsDes)
    TextView ivGoodsDes;
    @BindView(R.id.iv_goodsName)
    TextView ivGoodsName;
    @BindView(R.id.tv_goodsMoney)
    TextView tvGoodsMoney;

    private Goods goodsBean;
    private List<GoodsItemBean> allGoodsItems;
    public static final int RESULT_CODE = 0x50;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void initData() {
//        SquareFragment fragment = (SquareFragment) getFragmentManager().findFragmentById(R.id.);
        goodsBean = (Goods) getIntent().getSerializableExtra("goodsBean");
        allGoodsItems = (List<GoodsItemBean>) getIntent().getSerializableExtra("allGoodsItems");
    }

    @Override
    public void initView() {
        if (goodsBean != null) {
            GlideUtils.loadJSGImage(getApplicationContext(), goodsBean.getImagepath(), ivGoodsImg);
            ivGoodsName.setText(goodsBean.getGoodsname());
            tvGoodsMoney.setText(String.format("¥ %s元", goodsBean.getPrice()));
            ivGoodsNameContent.setText(goodsBean.getGoodsname());
            ivGoodsDes.setText(goodsBean.getTextdetail());
        }
    }


    @OnClick(R.id.tv_addGoods)
    public void onViewClicked() {
        for (GoodsItemBean goodsItemBean : allGoodsItems) {
            if (goodsItemBean.goodsid.equals(goodsBean.getGoodsid())) {
                updateGoodsNum(goodsItemBean, goodsItemBean.quantity + 1);
                return;
            }
        }
        addGoodsToCart(goodsBean);
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
                        showToast("已添加到购物车");
                        allGoodsItems = goodsItemBeans;
                        Intent intent = new Intent();
                        intent.putExtra("allGoodsItems", (Serializable) allGoodsItems);
                        setResult(RESULT_CODE, intent);
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
                            showToast("已添加到购物车");
                            for (GoodsItemBean goodsItem : allGoodsItems) {
                                if (goodsItem.cartitemid.equals(goodsItemBean.cartitemid)) {
                                    goodsItem.quantity = num;
                                }
                            }
                            Intent intent = new Intent();
                            intent.putExtra("allGoodsItems", (Serializable) allGoodsItems);
                            setResult(RESULT_CODE, intent);
                        } else {
                            showToast(baseBean.message);
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
}
