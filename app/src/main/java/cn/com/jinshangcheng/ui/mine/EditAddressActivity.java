package cn.com.jinshangcheng.ui.mine;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.Address;
import cn.com.jinshangcheng.bean.AddressBean;
import cn.com.jinshangcheng.utils.CommonUtils;
import cn.com.jinshangcheng.widget.CityWheelSelectPopupWindow;
import cn.com.jinshangcheng.widget.TittleBar;

public class EditAddressActivity extends BaseActivity {

    @BindView(R.id.tittleBar)
    TittleBar tittleBar;
    @BindView(R.id.root)
    ConstraintLayout root;
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

    AddressBean bean;
    public static final int RESULT_CODE = 0x22;
    private CityWheelSelectPopupWindow popupWindow;
    private Address address;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_edit_address;
    }

    @Override
    public void initData() {

        bean = (AddressBean) getIntent().getSerializableExtra("address");
    }

    @Override
    public void initView() {
        initBottonDialog();

        //是否为编辑地址
        if (bean != null) {
            etName.setText(bean.name);
            etAddress.setText(bean.address);
            etPhone.setText(bean.phone);
            tittleBar.setTittle("编辑地址");
        } else {
            tittleBar.setTittle("新建地址");
        }
    }

    /**
     * 初始化底部位置选择dialog
     */
    private void initBottonDialog() {
        //初始化选择位置dialog
        String addressName = "";
        popupWindow = new CityWheelSelectPopupWindow(this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvAddress.setText(popupWindow.address);
                Logger.w("Address" + popupWindow.address);
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

    public boolean checkInput() {
        if (TextUtils.isEmpty(etName.getText().toString().trim())) {
            return false;
        } else if (TextUtils.isEmpty(etPhone.getText().toString()) || !CommonUtils.isMobilePhone(etPhone.getText().toString())) {
            return false;
        } else if (TextUtils.isEmpty(tvAddress.getText().toString().trim())) {
            return false;
        } else if (TextUtils.isEmpty(etAddress.getText().toString().trim())) {
            return false;
        }
        return true;
    }

    public void addAddress() {
         address = new Address(etName.getText().toString(),etPhone.getText().toString(),
                tvAddress.getText().toString(),etAddress.getText().toString(),cbDefaultAddress.isSelected()?1:0);
//        RetrofitService.getRetrofit().addAddress(address)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<BaseBean<Address>>() {
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(BaseBean<Address> bean) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }


    @OnClick({R.id.bt_newAddress, R.id.tv_address})
    public void onViewClicked(View view) {
        CommonUtils.hideSoftKeyboard(EditAddressActivity.this);

        switch (view.getId()) {
            case R.id.bt_newAddress:
//                if (!checkInput()) {
//                    Toast.makeText(this, "请填写全部信息", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                addAddress();
                Intent intent = new Intent();
                intent.putExtra("addressBean", address);
                setResult(RESULT_CODE, intent);
                finish();
                break;
            case R.id.tv_address://选择地址弹窗:
                popupWindow.showPopupWindow(root);
                break;
        }

    }

}
