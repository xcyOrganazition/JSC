package cn.com.jinshangcheng.ui.mine;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.StealthBean;
import cn.com.jinshangcheng.net.RetrofitService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 隐身管理
 */
public class StealthManageActivity extends BaseActivity {

    @BindView(R.id.pickerDay)
    NumberPicker pickerDay;
    @BindView(R.id.pickerHour)
    NumberPicker pickerHour;
    @BindView(R.id.ll_setStartView)
    LinearLayout llSetStartView;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.switch_local)
    Switch switchLocal;
    @BindView(R.id.tv_totalTime)
    TextView tvTotalTime;
    @BindView(R.id.tv_remainTime)
    TextView tvRemainTime;
    @BindView(R.id.ll_setStopView)
    LinearLayout llSetStopView;

//    private String[] dayArray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
//    private String[] hourArray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
//            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
//            "21", "22", "23"};

    private String carId;
    private StealthBean stealthBean;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_stealth_manage;
    }

    @Override
    public void initData() {
        carId = getIntent().getStringExtra("carId");

    }

    @Override
    public void initView() {
        pickerDay.setFocusable(false);
        pickerDay.setMaxValue(30);
        pickerDay.setMinValue(0);
        pickerDay.setWrapSelectorWheel(false);//不循环显示
        pickerDay.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);//不可编辑
        pickerDay.setValue(0);//设置默认的位置

        pickerHour.setFocusable(false);
        pickerHour.setMaxValue(23);
        pickerHour.setMinValue(0);
        pickerHour.setWrapSelectorWheel(false);//不循环显示
        pickerHour.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);//不可编辑
        pickerHour.setValue(0);//设置默认的位置

        getStealthData();
    }

    //设置隐身状态文字
    public void setStealthViewData() {
        long totalTime = stealthBean.endtime - stealthBean.begintime;
        DecimalFormat format = new DecimalFormat("#");
        String totalText = format.format(Math.floor(totalTime / 1000L / 60L / 60L / 24L)) + "天" + totalTime / 1000 / 60 / 60 % 24 + "小时";
        long remainTime = stealthBean.endtime - System.currentTimeMillis();
        String remainText = format.format(Math.floor(remainTime / 1000 / 60 / 60 / 24)) + "天"
                + remainTime / 1000 / 60 / 60 % 24 + "小时"
                +remainTime / 1000 / 60  % 60+"分钟";

        tvStatus.setText(String.format("%s%s", "隐身状态:", "已开启"));
        switchLocal.setChecked(true);
        switchLocal.requestFocus();
        tvTotalTime.setText(String.format("%s%s", "隐身时长:", totalText));
        tvRemainTime.setText(String.format("%s%s", "距离隐身结束还剩:", remainText));
        switchLocal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    stopStealth();
                }
            }
        });
    }

    public void getStealthData() {
        showLoading();
        RetrofitService.getRetrofit().getStealthData(MyApplication.getUserId(), carId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<StealthBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<StealthBean> baseBean) {
                        if (null == baseBean.data) {//没有设置隐身
                            llSetStartView.setVisibility(View.VISIBLE);
                            llSetStopView.setVisibility(View.GONE);
                        } else {//设置了隐身 展示隐身数据
                            stealthBean = baseBean.data;
                            llSetStartView.setVisibility(View.GONE);
                            llSetStopView.setVisibility(View.VISIBLE);
                            setStealthViewData();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                        e.printStackTrace();
                        llSetStartView.setVisibility(View.VISIBLE);
                        llSetStopView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {
                        dismissLoading();
                    }
                });
    }

    public void beginStealth() {
        showLoading();
        RetrofitService.getRetrofit().beginStealth(MyApplication.getUserId(), carId,
                String.valueOf(pickerDay.getValue()),
                String.valueOf(pickerHour.getValue()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        dismissLoading();
                        if ("0".equals(baseBean.code)) {

                        }
                        showToast(baseBean.message);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

//                        getStealthData();
                    }
                });
    }

    public void stopStealth() {
        showLoading();
        RetrofitService.getRetrofit().stopStealth(MyApplication.getUserId(), carId,
                stealthBean.invisibleid + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        dismissLoading();
                        if ("0".equals(baseBean.code)) {
//                            getStealthData();
                            showToast("已取消隐身");
                        }else {
                            showToast(baseBean.message);
                        }
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

    @OnClick(R.id.bt_open)
    public void onViewClicked() {
        beginStealth();
    }

}
