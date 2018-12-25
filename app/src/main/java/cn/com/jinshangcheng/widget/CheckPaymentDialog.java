package cn.com.jinshangcheng.widget;

import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.bean.OrderBean;
import cn.com.jinshangcheng.ui.mine.MyOrderActivity;
import cn.com.jinshangcheng.utils.CommonUtils;
import cn.com.jinshangcheng.utils.DensityUtil;

public class CheckPaymentDialog extends DialogFragment {

    @BindView(R.id.et_verificationCode)
    EditText etVerificationCode;

    Unbinder unbinder;
    private OrderBean orderBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.dialog_checkpayment, null);
        Window window = this.getDialog().getWindow();
        //去掉dialog默认的padding
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = DensityUtil.dip2px(getActivity(), 300);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置dialog的位置在底部
        lp.gravity = Gravity.CENTER;
        //设置dialog的动画
        lp.windowAnimations = android.R.anim.fade_in;
        window.setAttributes(lp);
        window.setBackgroundDrawable(new ColorDrawable());
        unbinder = ButterKnife.bind(this, contentView);
        return contentView;
    }

    public void setOrderBean(OrderBean orderBean) {
        this.orderBean = orderBean;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_confirm)
    public void onViewClicked() {
        CommonUtils.hideSoftKeyboard(getActivity());
        if (TextUtils.isEmpty(etVerificationCode.getText().toString())) {
            Toast.makeText(getActivity(), "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        MyOrderActivity activity = (MyOrderActivity) getActivity();
        activity.payByPayCode(etVerificationCode.getText().toString());
    }


}
