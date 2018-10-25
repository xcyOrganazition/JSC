package cn.com.jinshangcheng.ui.mine;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.AddressBean;
import cn.com.jinshangcheng.widget.TittleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditAddressActivity extends BaseActivity {

    @BindView(R.id.tittleBar)
    TittleBar tittleBar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.cb_defaultAddress)
    CheckBox cbDefaultAddress;

    AddressBean bean;

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
        if (bean != null) {
            etName.setText(bean.name);
            etAddress.setText(bean.address);
            etPhone.setText(bean.phone);
            tittleBar.setTittle("编辑地址");
        } else {
            tittleBar.setTittle("新建地址");
        }

    }


    @OnClick(R.id.bt_newAddress)
    public void onViewClicked() {
    }

}
