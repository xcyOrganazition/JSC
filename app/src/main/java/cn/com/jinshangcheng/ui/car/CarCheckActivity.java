package cn.com.jinshangcheng.ui.car;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.CarBean;
import cn.com.jinshangcheng.bean.mycst.CheckDataBean;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.utils.GlideUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CarCheckActivity extends BaseActivity {


    @BindView(R.id.tv_carLicense)
    TextView tvCarLicense;
    @BindView(R.id.tv_carBrand)
    TextView tvCarBrand;
    @BindView(R.id.tv_carType)
    TextView tvCarType;
    @BindView(R.id.iv_carImg)
    ImageView ivCarImg;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.iv_circle)
    ImageView ivCircle;
    @BindView(R.id.tv_checkDetail)
    TextView tvCheckDetail;
    @BindView(R.id.tv_maintenance)
    TextView tvMaintenance;
    @BindView(R.id.tv_insurance)
    TextView tvInsurance;
    @BindView(R.id.tv_annual)
    TextView tvAnnual;
    @BindView(R.id.tv_battery)
    TextView tvBattery;
    @BindView(R.id.tv_malfunction)
    TextView tvMalfunction;
    @BindView(R.id.tv_resultText)
    TextView tvResultText;
    @BindView(R.id.parentLayout)
    ConstraintLayout parentLayout;
    private String mOpenCarId;
    private ObjectAnimator rotationAnim;//旋转动画
    private CheckDataBean checkData;//检测数据

    @Override
    public int setContentViewResource() {
        return R.layout.activity_car_check_test;
    }

    @Override
    public void initData() {
        mOpenCarId = getIntent().getStringExtra("OPENCARID");
        if (null == mOpenCarId) {
            showToast("未获取到车辆Id");
            return;
        }
    }

    @Override
    public void initView() {
        initCarView();//绘制车辆View
        rotationAnim = ObjectAnimator.ofFloat(ivCircle, "rotation", 0f, 3600f);
        rotationAnim.setDuration(5000);
//        rotationAnim.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        rotationAnim.start();

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);//休眠3秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                requestData();
            }
        }.start();
    }

    private void initCarView() {
        CarBean bean = MyApplication.getCurrentCarBean();
        tvCarLicense.setText(bean.getPlatenumber());
        tvCarBrand.setText(bean.getBrandname());
//        GlideUtils.loadImage(getApplicationContext(), bean.getBrandpath(), ivBranchImg);
        GlideUtils.loadImage(getApplicationContext(), bean.getBrandpath(), ivCarImg);
        tvCarType.setText(bean.getTypename());
    }

    public void setBgColor() {

        int colorA = getResources().getColor(R.color.textGreen);
        int colorB = getResources().getColor(R.color.textOrange);
        int colorC = getResources().getColor(R.color.text_red);
        //背景色渐变动画
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(parentLayout, "backgroundColor", colorA, colorC);
        objectAnimator.setDuration(2000);
        objectAnimator.setEvaluator(new ArgbEvaluator());
        objectAnimator.start();

        tvResultText.setText("车辆状况良好");
        AnimatorSet zoomAnim = new AnimatorSet();//文字缩放动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(tvResultText, "scaleX", 0, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(tvResultText, "scaleY", 0, 1f);

        zoomAnim.setDuration(1200);
        zoomAnim.setInterpolator(new DecelerateInterpolator());
        zoomAnim.play(scaleX).with(scaleY);//两个动画同时开始
        zoomAnim.start();
        rotationAnim.end();//终止旋转动画
        ivCircle.setVisibility(View.INVISIBLE);
    }

    //数据请求操作
    private void requestData() {

        RetrofitService.getRetrofit().getCheckReport(mOpenCarId, MyApplication.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<CheckDataBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<CheckDataBean> checkDataBeanBaseBean) {
                        if (checkDataBeanBaseBean.code.equals("0")) {

                            checkData = checkDataBeanBaseBean.data;
                            refreshCheckData();
                        } else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast("未检测到数据项,请稍后再试");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void refreshCheckData() {
        setBgColor();
        tvBattery.setText(String.valueOf(checkData.batteryvoltage));
        tvMalfunction.setText(checkData.malfunctionnum == 0 ? "无" : "查看故障详情");
        tvMalfunction.setTextColor(checkData.malfunctionnum == 0 ? getResources().getColor(R.color.textGreen)
                : getResources().getColor(R.color.text_red));


    }


    @OnClick({R.id.tv_checkDetail, R.id.tv_malfunction})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_checkDetail:
                break;
            case R.id.tv_malfunction:
                if (checkData != null && checkData.malfunctionnum != 0) {

                }
                break;
        }
    }
}
