package cn.com.jinshangcheng.ui.car;

import android.app.DatePickerDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.CarMaintainBean;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.utils.DateUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 年审信息
 */
public class AnnualActivity extends BaseActivity {
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_registTime)
    TextView tvRegistTime;

    private String time = "";
    private CarMaintainBean carMaintainBean;
    private String registTime;//注册时间

    @Override
    public int setContentViewResource() {
        return R.layout.activity_annual;
    }

    @Override
    public void initData() {
        carMaintainBean = (CarMaintainBean) getIntent().getSerializableExtra("maintainBean");
        if (carMaintainBean != null) {
            if (carMaintainBean.getMaintain() != null) {
                time = DateUtils.getYMDTime(carMaintainBean.getMaintain().getAnnualtrialdeadline());
            }
            registTime = DateUtils.getYMDTime(carMaintainBean.getCarregistdate());
        }
    }

    @Override
    public void initView() {
        tvTime.setText(time);//保险信息
        tvRegistTime.setText(registTime);//保险信息
    }

    @OnClick({R.id.tv_time, R.id.bt_confirm, R.id.tv_registTime})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_time:
                DatePickerDialog dialog = new DatePickerDialog(AnnualActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                time = year + "-" + (month + 1) + "-" + dayOfMonth;
                                Logger.w("abc     " + time);
                                tvTime.setText(time);
                            }
                        },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                dialog.show();

                break;
            case R.id.tv_registTime:
                DatePickerDialog dialog2 = new DatePickerDialog(AnnualActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                time = year + "-" + (month + 1) + "-" + dayOfMonth;
                                Logger.w("abc     " + time);
                                tvRegistTime.setText(time);
                            }
                        },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                dialog2.show();

                break;
            case R.id.bt_confirm:
                if (TextUtils.isEmpty(time)) {
                    showToast("请选择时间");
                }
                if (TextUtils.isEmpty(registTime)) {
                    showToast("请选择时间");
                } else {
                    confirmInsurance(time, registTime);
                }
                break;
        }
    }

    //提交保险时间
    public void confirmInsurance(String time, String registTime) {
        showLoading();
        RetrofitService.getRetrofit().confirmAnnual(MyApplication.getCarId(), MyApplication.getUserId(), time, registTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        dismissLoading();
                        if (baseBean.code.equals("0")) {//保存成功
                            setResult(CarFragment.RESULT_CODE);
                            finish();
                        }
                        showToast(baseBean.message);
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
}
