package cn.com.jinshangcheng.ui.car;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;

/**
 * 年审信息
 */
public class AnnualActivity extends BaseActivity {
    @BindView(R.id.tv_time)
    TextView tvTime;

    private String time = "";

    @Override
    public int setContentViewResource() {
        return R.layout.activity_annual;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.tv_time, R.id.bt_confirm})
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
            case R.id.bt_confirm:
                break;
        }
    }
}
