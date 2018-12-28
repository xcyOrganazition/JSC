package cn.com.jinshangcheng.ui.square;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.Address;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.Goods;
import cn.com.jinshangcheng.bean.GoodsItemBean;
import cn.com.jinshangcheng.bean.OrderBean;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.net.RetrofitServiceForWeiPay;
import cn.com.jinshangcheng.ui.mine.AddressManageActivity;
import cn.com.jinshangcheng.ui.mine.EditAddressActivity;
import cn.com.jinshangcheng.ui.mine.MyOrderActivity;
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
    @BindView(R.id.tv_default)
    TextView tvDefault;
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
    private double totalPrice;
    private List<GoodsItemBean> allGoodsItems;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initData() {
        allGoodsItems = (List<GoodsItemBean>) getIntent().getSerializableExtra("allGoodsItems");
    }

    @Override
    public void initView() {
        initGoodsList();//选择商品列表
        getDefaultAddress();//请求默认地址
    }

    private void initGoodsList() {
        for (GoodsItemBean goodsItemBean : allGoodsItems) {
            Goods curGoods = goodsItemBean.goods;
            if (curGoods != null) {
                final View itemView = View.inflate(this, R.layout.item_goods_detail, null);
                TextView tvCurGoodsName = itemView.findViewById(R.id.tv_curGoodsName);
                final TextView tvCurGoodPrice = itemView.findViewById(R.id.tv_curGoodPrice);
                final TextView tvCurGoodsNum = itemView.findViewById(R.id.tv_curGoodsNum);
                itemView.setTag(curGoods);
                tvCurGoodsName.setText(curGoods.getGoodsname());
                tvCurGoodsNum.setText(String.format("%s件", goodsItemBean.quantity));
                tvCurGoodPrice.setText(String.format("%s元", curGoods.getPrice() * goodsItemBean.quantity));
                totalPrice += curGoods.getPrice() * goodsItemBean.quantity;
                llGoodsList.addView(itemView);
            }
        }
        tvTotalPrice.setText(String.format("总计 %s 元", totalPrice));
    }

    public void initAddressView() {
        if (address != null) {
            tvName.setText(address.getReceiver());
            tvAddress.setText(address.getCity() + address.getDetailaddress());
            tvEdit.setVisibility(View.INVISIBLE);
            tvPhone.setText(address.getPhonenumber());
            tvDefault.setVisibility(address.getIsdefault() == 0 ? View.VISIBLE : View.GONE);
        }
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
            initAddressView();
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


    @OnClick({R.id.tv_changeAddress, R.id.tv_newAddress, R.id.bt_confirm, R.id.tv_offLinePay})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_changeAddress:
                intent = new Intent(getApplicationContext(), AddressManageActivity.class);
                intent.putExtra("fromOrder", true);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.tv_newAddress:
                intent = new Intent(getApplicationContext(), EditAddressActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.bt_confirm:

                if (checkInput()) {
                    createOrder(getCartItemIds());
                }
                break;
            case R.id.tv_offLinePay:
                if (checkInput()) {
                    createOffLineOrder(getCartItemIds());
                }

                break;
        }
    }

    private boolean checkInput() {
        if (null == address) {
            showToast("请添加地址");
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == EditAddressActivity.RESULT_CODE) {
            address = (Address) data.getSerializableExtra("addressBean");
            setViewVisible();
        }
        if (resultCode == AddressManageActivity.RESULT_CODE) {
            address = (Address) data.getSerializableExtra("addressBean");
            setViewVisible();
        }
    }

    public void createOrder(String cartitemids) {
        showLoading();
        RetrofitServiceForWeiPay.getRetrofit().createOrder(MyApplication.getUserId(), cartitemids, address.getAddressid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OrderBean baseBean) {
                        dismissLoading();
                        showToast("创建成功");
                        OrderBean orderBean = baseBean;
                        String orderName = orderBean.getOrderitems().get(0).getGoodsname();
                        String orderId = orderBean.getOrderid();
                        String totalMoney = String.valueOf(orderBean.getTotal());
                        String describe = "";

                        if (orderBean.getOrderitems().size() > 1) {
                            orderName += "等";
                        }
                        getALiOrderInfo(orderName, orderId, totalMoney, describe);
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void createOffLineOrder(String cartitemids) {
        showLoading();
        RetrofitService.getRetrofit().createOffLineOrder(MyApplication.getUserId(), cartitemids, address.getAddressid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<OrderBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<OrderBean> baseBean) {
                        dismissLoading();
                        SquareFragment.allGoodsItems.clear();//清空购物车
                        Intent intent1 = new Intent(getApplicationContext(), CheckPaymentActivity.class);
                        intent1.putExtra("orderBean", baseBean.data);
                        startActivity(intent1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getALiOrderInfo(final String subject,
                                String out_trade_no,
                                final String total_amount,
                                String body) {
        RetrofitService.getRetrofit().getALiOrderInfo(subject, out_trade_no, total_amount, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<String> baseBean) {
                        String key = baseBean.data;
//                        Logger.w("秘钥" + key);
                        Intent intent = new Intent(getApplicationContext(), SelectPayTypeActivity.class);
                        intent.putExtra("key", key);
                        intent.putExtra("des", subject);
                        intent.putExtra("total", total_amount);
                        SquareFragment.allGoodsItems.clear();//清空购物车
                        Intent intent2 = new Intent(getApplicationContext(), MyOrderActivity.class);
                        startActivities(new Intent[]{intent2, intent});
                        finish();

                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    public String getCartItemIds() {
        StringBuffer cartItemIds = new StringBuffer("");
        for (int i = 0; i < allGoodsItems.size(); i++) {
            cartItemIds.append(allGoodsItems.get(i).cartitemid);
            if (i != allGoodsItems.size() - 1) {
                cartItemIds.append(",");
            }
        }
        Logger.w("cartItemIds = " + cartItemIds.toString());
        return cartItemIds.toString();
    }
}
