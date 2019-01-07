package cn.com.jinshangcheng.ui.mine;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.Address;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.FuckingBaseBean;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.utils.CommonUtils;
import cn.com.jinshangcheng.widget.CityWheelSelectPopupWindow;
import cn.com.jinshangcheng.widget.TittleBar;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EditAddressActivity extends BaseActivity {

    @BindView(R.id.tittleBar)
    TittleBar tittleBar;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.cb_defaultAddress)
    CheckBox cbDefaultAddress;

    Address bean;
    public static final int RESULT_CODE = 0x55;
    private CityWheelSelectPopupWindow popupWindow;
    private boolean isNewAddress;
    private String addressId;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_edit_address;
    }

    @Override
    public void initData() {

        bean = (Address) getIntent().getSerializableExtra("address");
    }

    @Override
    public void initView() {
        initBottonDialog();

        //是否为编辑地址
        if (bean != null) {
            isNewAddress = false;
            etName.setText(bean.getReceiver());
            tvAddress.setText(bean.getCity());
            etAddress.setText(bean.getDetailaddress());
            etPhone.setText(bean.getPhonenumber());
            cbDefaultAddress.setChecked(bean.getIsdefault() == 0);
            addressId = bean.getAddressid();
            tittleBar.setTittle("编辑地址");
        } else {
            isNewAddress = true;
            tittleBar.setTittle("新建地址");
        }
    }

    /**
     * 初始化底部位置选择dialog
     */
    private void initBottonDialog() {
        //初始化选择位置dialog
        popupWindow = new CityWheelSelectPopupWindow(this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Logger.w("Address" + popupWindow.address);
                String[] array = popupWindow.address.split(" ");
                if (array.length > 2) {

                    tvAddress.setText(array[0] + " " + array[1]);
                }
                popupWindow.dismiss();
            }
        });
//        if (TextUtils.isEmpty(addressName)) {
//            addressName = tvAddress.getText().toString().trim();
//            popupWindow.wheelHelper.setDefaultAddress(addressName);
//
//        } else {
//            popupWindow.wheelHelper.setDefaultAddress(addressName);
//        }

    }

    public boolean checkInputAndConfirm() {
        if (TextUtils.isEmpty(etName.getText().toString().trim())) {
            showToast("请填写收货人姓名");
            return false;
        } else if (TextUtils.isEmpty(etPhone.getText().toString()) || !CommonUtils.isMobilePhone(etPhone.getText().toString())) {
            showToast("电话输入有误");
            return false;
        } else if (TextUtils.isEmpty(tvAddress.getText().toString().trim())) {
            showToast("请选择收获地址");
            return false;
        } else if (TextUtils.isEmpty(etAddress.getText().toString().trim())) {
            showToast("请填写详细地址");
            return false;
        }
        return true;
    }

    //新增地址
    public void addAddress() {
        showLoading();
        RetrofitService.getRetrofit().addAddress(MyApplication.getUserId(),
                etName.getText().toString(), etPhone.getText().toString(),
                tvAddress.getText().toString(), etAddress.getText().toString(),
                cbDefaultAddress.isChecked() ? 0 : 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FuckingBaseBean<Address>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FuckingBaseBean<Address> bean) {
                        showToast(bean.message);
                        Intent intent = new Intent();
                        intent.putExtra("addressBean", bean.address);
                        setResult(RESULT_CODE, intent);
                        finish();
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

    //修改地址
    private void updateAddress() {
        RetrofitService.getRetrofit().updateAddress(MyApplication.getUserId(),
                addressId,
                etName.getText().toString(), etPhone.getText().toString(),
                tvAddress.getText().toString(), etAddress.getText().toString(),
                cbDefaultAddress.isChecked() ? 0 : 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        showToast(baseBean.message);
                        Intent intent = new Intent();
                        setResult(RESULT_CODE, intent);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick({R.id.bt_newAddress, R.id.tv_address})
    public void onViewClicked(View view) {
        CommonUtils.hideSoftKeyboard(EditAddressActivity.this);

        switch (view.getId()) {
            case R.id.bt_newAddress:
                if (checkInputAndConfirm()) {
                    if (isNewAddress) {//新增地址
                        addAddress();
                    } else {
                        updateAddress();
                    }
                }
                break;
            case R.id.tv_address://选择地址弹窗:
                CommonUtils.hideSoftKeyboard(EditAddressActivity.this);
                int navigationBarHeight = CommonUtils.getNavigationBarHeight(EditAddressActivity.this);
                popupWindow.showAtLocation(root, Gravity.BOTTOM, 0, navigationBarHeight);
                break;
        }

    }

}
