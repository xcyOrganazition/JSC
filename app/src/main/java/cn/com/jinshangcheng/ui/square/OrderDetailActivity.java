package cn.com.jinshangcheng.ui.square;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.Address;
import cn.com.jinshangcheng.bean.Goods;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.ui.mine.AddressManageActivity;
import cn.com.jinshangcheng.ui.mine.EditAddressActivity;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.tv_changeAddress)
    TextView tvChangeAddress;
    @BindView(R.id.tv_newAddress)
    TextView tvNewAddress;
    @BindView(R.id.tv_emptyMsg)
    TextView tvEmptyMsg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.root)
    ConstraintLayout root;
    @BindView(R.id.ll_goodsList)
    LinearLayout llGoodsList;
    @BindView(R.id.tv_totalPrice)
    TextView tvTotalPrice;
    @BindView(R.id.bt_confirm)
    Button btConfirm;

    public static final int REQUEST_CODE = 0x3;
    public Address address = null;
    private List<Goods> goodsList;
    private Map<String, Integer> selectGoodsMap;//选择的商品<GoodsId,GoodsNum>
    private double totalPrice;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initData() {
        goodsList = (List<Goods>) getIntent().getSerializableExtra("goodsList");
        selectGoodsMap = (Map<String, Integer>) getIntent().getSerializableExtra("selectGoodsMap");


    }

    @Override
    public void initView() {
        initGoodsList();//选择商品列表
        getDefaultAddress();//请求默认地址
    }

    private void initGoodsList() {
        for (final String goodsId : selectGoodsMap.keySet()) {
            Goods curGoods = null;
            for (Goods goods : goodsList) {
                if (goods.getGoodsid().equals(goodsId)) {
                    curGoods = goods;
                }
            }
            if (curGoods != null) {
                final View itemView = View.inflate(this, R.layout.item_goods_detail, null);
                TextView tvCurGoodsName = itemView.findViewById(R.id.tv_curGoodsName);
                final TextView tvCurGoodPrice = itemView.findViewById(R.id.tv_curGoodPrice);
                final TextView tvCurGoodsNum = itemView.findViewById(R.id.tv_curGoodsNum);
                itemView.setTag(curGoods);
                tvCurGoodsName.setText(curGoods.getGoodsname());
                tvCurGoodsNum.setText(String.format("%s件", selectGoodsMap.get(goodsId)));
                tvCurGoodPrice.setText(String.format("%s元", curGoods.getPrice() * selectGoodsMap.get(goodsId)));
                totalPrice += curGoods.getPrice() * selectGoodsMap.get(goodsId);
                llGoodsList.addView(itemView);
            }
        }
        tvTotalPrice.setText(String.format("%s 元", totalPrice));
    }

    public void setViewVisible() {
        if (address == null) {
            tvChangeAddress.setVisibility(View.GONE);
            tvEmptyMsg.setVisibility(View.VISIBLE);
            tvNewAddress.setVisibility(View.VISIBLE);
            root.setVisibility(View.GONE);
        } else {
            tvChangeAddress.setVisibility(View.VISIBLE);
            tvEmptyMsg.setVisibility(View.GONE);
            tvNewAddress.setVisibility(View.GONE);
            root.setVisibility(View.VISIBLE);
        }
    }

    public void getDefaultAddress() {
        showLoading();
        RetrofitService.getRetrofit().getDefaultAddress(MyApplication.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Address>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Address defaultAddress) {
                        address = defaultAddress;
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                        address = null;
                        setViewVisible();
                    }

                    @Override
                    public void onComplete() {
                        dismissLoading();
                        setViewVisible();
                    }
                });
    }


    @OnClick({R.id.tv_changeAddress, R.id.tv_newAddress, R.id.bt_confirm})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_changeAddress:
                intent = new Intent(getApplicationContext(), AddressManageActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.tv_newAddress:
                intent = new Intent(getApplicationContext(), EditAddressActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.bt_confirm:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == EditAddressActivity.RESULT_CODE) {
            address = (Address) data.getSerializableExtra("addressBean");
            setViewVisible();
        }
    }
}
