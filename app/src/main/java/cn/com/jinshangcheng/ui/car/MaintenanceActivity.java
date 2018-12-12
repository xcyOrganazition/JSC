package cn.com.jinshangcheng.ui.car;

import android.app.DatePickerDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

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
 * 保养信息
 */
public class MaintenanceActivity extends BaseActivity {

    @BindView(R.id.et_totalMile)
    EditText etTotalMile;
    @BindView(R.id.et_nearMiles)
    EditText etNearMile;
    @BindView(R.id.et_intervalMiles)
    EditText etIntervalMile;
    @BindView(R.id.tv_nearDate)
    TextView tvNearTime;
    private String time = "";
    private CarMaintainBean.MaintainBean maintainBean;


    @Override
    public int setContentViewResource() {
        return R.layout.activity_maintenance;
    }

    @Override
    public void initData() {
        CarMaintainBean carMaintainBean = (CarMaintainBean) getIntent().getSerializableExtra("maintainBean");
        if (carMaintainBean != null && null != carMaintainBean.getMaintain()) {
            maintainBean = carMaintainBean.getMaintain();
        }
    }

    @Override
    public void initView() {
        if (null != maintainBean) {
            etTotalMile.setText(String.valueOf(maintainBean.getMileage()));//行驶总里程
            etNearMile.setText(String.valueOf(maintainBean.getLastmaintainmileage()));//最近保养历程
            etIntervalMile.setText(String.valueOf(maintainBean.getMaintenanceinterval()));//保养间隔
            tvNearTime.setText(DateUtils.getYMDTime(maintainBean.getLastmaintaintime()));//最近保养时间
            etTotalMile.setSelection(String.valueOf(maintainBean.getMileage()).length());
        }
    }

    @OnClick({R.id.tv_nearDate, R.id.bt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_nearDate:
                DatePickerDialog dialog = new DatePickerDialog(MaintenanceActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                time = year + "-" + (month + 1) + "-" + dayOfMonth;
                                tvNearTime.setText(time);
                            }
                        },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                dialog.show();
                break;
            case R.id.bt_confirm:
                checkInputAndComfirmData();
                break;
        }
    }

    public void checkInputAndComfirmData() {
        if (TextUtils.isEmpty(etTotalMile.getText().toString().trim())) {
            showToast("请填写行驶总里程");
            return;
        }
        if (TextUtils.isEmpty(etNearMile.getText().toString().trim())) {
            showToast("请填写最近保养里程");
            return;
        }
        if (TextUtils.isEmpty(etIntervalMile.getText().toString().trim())) {
            showToast("请填写保养间隔");
            return;
        }
        if (TextUtils.isEmpty(tvNearTime.getText().toString().trim())) {
            showToast("请填写最近保养时间");
            return;
        }
        confirmMaintain();
    }

    //提交保险时间
    public void confirmMaintain() {

        showLoading();
        RetrofitService.getRetrofit().confirmMaintain(
                MyApplication.getCarId(),
                MyApplication.getUserId(),
                etNearMile.getText().toString().trim(),
                etIntervalMile.getText().toString().trim(),
                tvNearTime.getText().toString().trim(),
                etTotalMile.getText().toString().trim()
        )
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
