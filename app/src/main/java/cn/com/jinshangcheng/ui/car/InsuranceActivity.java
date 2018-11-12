package cn.com.jinshangcheng.ui.car;

import android.app.DatePickerDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

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
 * 保险信息
 */
public class InsuranceActivity extends BaseActivity {

    @BindView(R.id.tv_deadline)
    TextView tvDeadline;

    String deadLine = "";
    private CarMaintainBean carMaintainBean;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_insurance;
    }

    @Override
    public void initData() {
        carMaintainBean = (CarMaintainBean) getIntent().getSerializableExtra("maintainBean");
        if (carMaintainBean != null) {
            deadLine = DateUtils.getYMDTime(carMaintainBean.getInsurancedeadline());
        }
    }

    @Override
    public void initView() {
        tvDeadline.setText(deadLine);//保险信息
    }

    @OnClick({R.id.tv_deadline, R.id.bt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_deadline:
                DatePickerDialog dialog = new DatePickerDialog(InsuranceActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                deadLine = year + "-" + (month + 1) + "-" + dayOfMonth;
                                Logger.w("abc     " + deadLine);
                                tvDeadline.setText(deadLine);
                            }
                        },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                dialog.show();
                break;
            case R.id.bt_confirm:
                String time = tvDeadline.getText().toString();
                if (TextUtils.isEmpty(time)) {
                    Toast.makeText(InsuranceActivity.this, "请选择时间", Toast.LENGTH_SHORT).show();
                } else {
                    confirmInsurance(time);
                }
                break;

        }
    }

    //提交保险时间
    public void confirmInsurance(String time) {
        showLoading();
        RetrofitService.getRetrofit().confirmInsurance(MyApplication.getCarId(), MyApplication.getUserId(), time)
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
