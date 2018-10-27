package cn.com.jinshangcheng.ui.car;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;

/**
 * 保养信息
 */
public class MaintenanceActivity extends BaseActivity {
    @BindView(R.id.et_totalMile)
    EditText etTotalMile;
    @BindView(R.id.et_nearMile)
    EditText etNearMile;
    @BindView(R.id.et_intervalMile)
    EditText etIntervalMile;
    @BindView(R.id.tv_nearTime)
    TextView tvNearTime;

    private String time = "";

    @Override
    public int setContentViewResource() {
        return R.layout.activity_maintenance;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.tv_nearTime, R.id.bt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_nearTime:
                DatePickerDialog dialog = new DatePickerDialog(MaintenanceActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                time = year + "-" + (month + 1) + "-" + dayOfMonth;
                                Logger.w("abc     " + time);
                                tvNearTime.setText(time);
                            }
                        },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                dialog.show();

                break;
            case R.id.bt_confirm:
                break;
        }
    }
}
