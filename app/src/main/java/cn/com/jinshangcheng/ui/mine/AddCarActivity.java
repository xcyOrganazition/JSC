package cn.com.jinshangcheng.ui.mine;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.ui.login.BindBoxActivity;
import cn.com.jinshangcheng.utils.CommonUtils;
import cn.com.jinshangcheng.utils.DensityUtil;

//添加车辆 编辑车辆
public class AddCarActivity extends BaseActivity {


    @BindView(R.id.rb_littleCar)
    RadioButton rbLittleCar;
    @BindView(R.id.rb_bigCar)
    RadioButton rbBigCar;
    @BindView(R.id.rg_carType)
    RadioGroup rgCarType;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.bt_confirm)
    Button btConfirm;

    private String[] array = new String[]{"京", "晋", "津", "冀", "鲁", "台", "蒙", "京", "晋", "津",
            "冀", "鲁", "台", "蒙", "京", "晋", "津", "冀", "鲁", "台", "蒙"};

    @Override
    public int setContentViewResource() {
        return R.layout.activity_add_car;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        spinner.setDropDownWidth(DensityUtil.dip2px(this, 50)); //下拉宽度
//        spinner.setDropDownHorizontalOffset(100); //下拉的横向偏移
        spinner.setDropDownVerticalOffset(DensityUtil.dip2px(this, 25)); //下拉的纵向偏移
        final ArrayAdapter spinnerAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.spinner_item, array);
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @OnClick({R.id.bt_confirm,R.id.tv_selectCarType})
    public void onViewClicked(View view) {
        CommonUtils.hideSoftKeyboard(AddCarActivity.this);

        Intent intent;
        switch (view.getId()) {

            case R.id.tv_selectCarType:
                 intent= new Intent(AddCarActivity.this, SelectCarActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_confirm:
                 intent = new Intent(AddCarActivity.this, BindBoxActivity.class);
                startActivity(intent);
                AddCarActivity.this.finish();

                break;
        }
    }
}
