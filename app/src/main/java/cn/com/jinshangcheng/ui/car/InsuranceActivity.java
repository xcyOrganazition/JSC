package cn.com.jinshangcheng.ui.car;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.orhanobut.logger.Logger;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;

/**
 * 保险信息
 */
public class InsuranceActivity extends BaseActivity {

    @BindView(R.id.tv_deadline)
    TextView tvDeadline;

    String deadLine = "";

    @Override
    public int setContentViewResource() {
        return R.layout.activity_insurance;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.tv_deadline, R.id.bt_confirm})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.tv_deadline:
                DatePickerDialog dialog = new DatePickerDialog(InsuranceActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                break;
        }
    }
}
