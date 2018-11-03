package cn.com.jinshangcheng.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.widget.AddressSelector.WheelHelper;
import cn.com.jinshangcheng.widget.AddressSelector.WheelView;
import cn.com.jinshangcheng.widget.AddressSelector.listener.OnWheelChangedListener;


/**
 * 选择城市popupwindow
 *
 * @author yang
 */
public class CityWheelSelectPopupWindow extends PopupWindow implements
        OnWheelChangedListener {

    private BaseActivity activity;
    private OnClickListener listener;
    private TextView mTv_confirm;
    public WheelHelper wheelHelper;
    private View view;
    public String address;

    public CityWheelSelectPopupWindow(BaseActivity activity, OnClickListener listener) {
        this.activity = activity;
        this.listener = listener;
        initView();
    }


    private void initView() {
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setTouchable(true);
        this.setOutsideTouchable(true);
        this.update();
        ColorDrawable dw = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(dw);

        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.view_city_wheel, null);
        mTv_confirm = (TextView) view.findViewById(R.id.tv_confirm_city_wheel);
        initWheel();
        this.setContentView(view);
        mTv_confirm.setOnClickListener(listener);
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
//            WindowUtil.backgroundAlpha(activity, 0.7f);
            this.showAtLocation(parent, Gravity.BOTTOM
                    | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void dismiss() {
        //WindowUtil.backgroundAlpha(activity, 1.0f);
        super.dismiss();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {

        wheelHelper.onChanged(wheel, oldValue, newValue);
        address = this.wheelHelper.mCurrentProviceName + " " + this.wheelHelper.mCurrentCityName + " "
                + this.wheelHelper.mCurrentDistrictName;

    }

    /**
     * 城市滚轮
     */
    private void initWheel() {
        wheelHelper = new WheelHelper(activity, view);
        wheelHelper.mViewProvince.addChangingListener(this);
        wheelHelper.mViewCity.addChangingListener(this);
        wheelHelper.mViewDistrict.addChangingListener(this);


    }

    public interface WindowOnWheelChangedListener {
        public void WindowonChanged(WheelView wheel, int oldValue, int newValue);
    }

}
