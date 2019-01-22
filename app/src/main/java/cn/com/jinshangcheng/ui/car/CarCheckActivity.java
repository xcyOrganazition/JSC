package cn.com.jinshangcheng.ui.car;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
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
import cn.com.jinshangcheng.bean.CarMaintainBean;
import cn.com.jinshangcheng.bean.mycst.CheckDataBean;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.utils.DateUtils;
import cn.com.jinshangcheng.utils.GlideUtils;
import cn.com.jinshangcheng.utils.NumberUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//一键检测
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
    private CarMaintainBean carMaintainBean;//检测数据
    private int littleProblemNumber;//小问题数量
    private CheckDataBean lastCheckData;//上次的检测数据
    private Thread delayThread;
    private boolean activityIsAlive = true;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_car_check_test;
    }

    @Override
    public void initData() {
        littleProblemNumber = 0;
        mOpenCarId = getIntent().getStringExtra("OPENCARID");
        carMaintainBean = (CarMaintainBean) getIntent().getSerializableExtra("carMaintainBean");
        lastCheckData = getIntent().getParcelableExtra("lastCheckData");
        if (null == mOpenCarId) {
//            showToast("未获取到车辆Id");
        }

        delayThread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1500);//休眠1.5秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (activityIsAlive) {
                    requestData();
                }
            }
        };
    }

    @Override
    public void initView() {
        initCarView();//绘制车辆View
        rotationAnim = ObjectAnimator.ofFloat(ivCircle, "rotation", 0f, 1800f);
        rotationAnim.setDuration(5000);
//        rotationAnim.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        rotationAnim.start();

        delayThread.start();
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

        int oldBgColor = getResources().getColor(R.color.themeColor);
        int colorBg = getResources().getColor(getBgColor());
        //背景色渐变动画
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(parentLayout, "backgroundColor", oldBgColor, colorBg);
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

    public void setCenterTest() {
        String centerText = "";
        if (checkData == null) {
            centerText = "未获取到检测数据";//接口有误 蓝色
        } else if (checkData.malfunctionnum > 0) {
            centerText = "车辆存在一些比较严重的问题";
        } else if (checkData.malfunctionnum == 0 && littleProblemNumber > 0) {
            centerText = "目前车辆存在一些问题，但不严重";
        } else if (checkData.malfunctionnum == 0 && littleProblemNumber == 0) {
            centerText = "车辆状况良好";
        }


        tvResultText.setText(centerText);
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
        if (lastCheckData != null) {
            checkData = lastCheckData;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    refreshCheckData();
                }
            });
            return;
        }
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
                            showToast("请求失败 请重试");
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
        refreshMaintainData();
        setBgColor();//设置背景颜色
        setCenterTest();//设置背景颜色

        tvBattery.setText(String.valueOf(checkData.batteryvoltage));
        tvMalfunction.setText(checkData.malfunctionnum == 0 ? "无" : "查看故障详情");
        tvMalfunction.setTextColor(checkData.malfunctionnum == 0 ? getResources().getColor(R.color.textGreen)
                : getResources().getColor(R.color.text_red));


    }

    //刷新保养保险年审信息
    public void refreshMaintainData() {
        if (carMaintainBean == null) {
            littleProblemNumber++;
            tvInsurance.setText("请完善信息");
            tvMaintenance.setText("请完善信息");
            tvAnnual.setText("请完善信息");
            return;
        }
        //保险数据
        if (carMaintainBean.getInsurancedeadline() != 0) {
            tvInsurance.setText(DateUtils.getYMDTime(carMaintainBean.getInsurancedeadline()) + "到期");//保险信息
            long remainTime = carMaintainBean.getInsurancedeadline() - System.currentTimeMillis();
            long aMonth = 60000L * 60L * 24L * 30L;
            if (remainTime < 0) {//已经超过过期时间 红色显示
                littleProblemNumber++;
                tvInsurance.setTextColor(getResources().getColor(R.color.text_red));
            } else if (remainTime < aMonth) {//不足一个月 橙色显示
                littleProblemNumber++;
                tvInsurance.setTextColor(getResources().getColor(R.color.textOrange));
            } else if (remainTime >= aMonth) {//超过一个月 正常显示
                tvInsurance.setTextColor(getResources().getColor(R.color.textGary));
            }
        } else {//没有保险数据
            tvInsurance.setText("");
        }
        //保养数据
        if (null != carMaintainBean.getMaintain()) {//保养信息
            CarMaintainBean.MaintainBean maintainobj = carMaintainBean.getMaintain();
            double remain = maintainobj.getLastmaintainmileage() + maintainobj.getMaintenanceinterval() - Double.parseDouble(carMaintainBean.getTotalmileage());
            remain = remain - (carMaintainBean.getBoxmile() - Double.parseDouble(maintainobj.getMileage()));
            remain = Math.round(remain / 100) * 100;
            if (remain < 500 && remain > 0) {
                littleProblemNumber++;
                tvMaintenance.setTextColor(getResources().getColor(R.color.textOrange));
                tvMaintenance.setText(String.format("距离下次保养剩余%s公里", NumberUtils.formatDouble(remain)));
            } else if (remain < 0) {
                littleProblemNumber++;
                tvMaintenance.setTextColor(getResources().getColor(R.color.text_red));
                tvMaintenance.setText("请及时保养车辆，更新保养信息");
            } else {
                tvMaintenance.setTextColor(getResources().getColor(R.color.textGary));
                tvMaintenance.setText(String.format("距离下次保养剩余%s公里", NumberUtils.formatDouble(remain)));
            }
        } else {//没有保养数据
            tvMaintenance.setText("");
        }
        //年审数据
        long remainTime = System.currentTimeMillis() - carMaintainBean.getCarregistdate();
        long sixYear = 6L * 365L * 24L * 60L * 60000L;
        if (carMaintainBean.getCarregistdate() == 0) {
            littleProblemNumber++;
            tvAnnual.setText("");
        } else if (remainTime < sixYear) {//小于六年 每隔2年更换年检
            long nextTime = carMaintainBean.getAnnualtrialdeadline() + 2L * 365L * 24L * 60L * 60000L;
            tvAnnual.setText("下次更换年检：" + DateUtils.getYMDTime(nextTime));
        } else {//大于六年 每隔1年年审
            long nextTime = carMaintainBean.getAnnualtrialdeadline() + 365L * 24L * 60L * 60000L;
            tvAnnual.setText("下次年审：" + DateUtils.getYMDTime(nextTime));
        }
    }


    @OnClick({R.id.tv_checkDetail, R.id.tv_malfunction})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_checkDetail:
                if (checkData == null) {
                    showToast("暂无检测数据");
                    return;
                }
                intent = new Intent(CarCheckActivity.this, CheckDetailActivity.class);
                intent.putExtra("checkedBean", checkData);
                startActivity(intent);
                break;
            case R.id.tv_malfunction://故障详情
                if (checkData != null && checkData.malfunctionnum != 0) {
                    intent = new Intent(CarCheckActivity.this, TroubleDetailActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityIsAlive = false;
    }

    public int getBgColor() {
        if (checkData == null) {
            return R.color.themeColor;//接口有误 蓝色
        }
        if (checkData.malfunctionnum > 0) {
            return R.color.red_normal;//有故障 红色
        }
        if (checkData.malfunctionnum == 0 && littleProblemNumber > 0) {
            return R.color.textOrange;//有小问题  橙色
        }
        if (checkData.malfunctionnum == 0 && littleProblemNumber == 0) {
            return R.color.backLightGreen;//没有任何问题  绿色
        }
        return R.color.themeColor;
    }
}
