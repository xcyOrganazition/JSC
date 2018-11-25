package cn.com.jinshangcheng.ui.mine;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.AddCarResult;
import cn.com.jinshangcheng.bean.CarBrandsBean;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.ui.login.BindBoxActivity;
import cn.com.jinshangcheng.utils.CommonUtils;
import cn.com.jinshangcheng.utils.DensityUtil;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import platform.cston.httplib.bean.CarModelResult;
import platform.cston.httplib.bean.CarTypeResult;

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
    @BindView(R.id.et_licenseNum)
    EditText etLicenseNum;
    @BindView(R.id.et_vin)
    EditText etVin;
    @BindView(R.id.tv_selectCarType)
    TextView tvSelectCarType;
    @BindView(R.id.tv_carRegistDate)
    TextView tvCarRegistDate;
    @BindView(R.id.et_totalMileage)
    EditText etTotalMileage;
    @BindView(R.id.tv_insuranceDate)
    TextView tvInsuranceDate;
    @BindView(R.id.rg_oilType)
    RadioGroup rgOilType;
    @BindView(R.id.et_emergencyPhonenum)
    EditText etEmergencyPhonenum;

    private final int REQUEST_CODE = 0x123;

    private String[] array = new String[]{"京", "晋", "津", "冀", "鲁", "台", "蒙", "京", "晋", "津",
            "冀", "鲁", "台", "蒙", "京", "晋", "津", "冀", "鲁", "台", "蒙"};
    String selectCity;
    CarBrandsBean selectCarBrands;//车品牌
    CarTypeResult.DataEntity.CarTypesEntity selectCarType;//车型
    CarModelResult.DataEntity selectCarModel;//车款

    @Override
    public int setContentViewResource() {
        return R.layout.activity_add_car;
    }

    @Override
    public void initData() {
        selectCity = array[0];

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
                selectCity = array[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void saveCarData() {
        showLoading();
        RetrofitService.getRetrofit().addCar(MyApplication.getUserId(), getCarTypeCode(), getPlateNumber(),
                etVin.getText().toString(), selectCarBrands.brandName, selectCarBrands.picturePath,
                selectCarType.typeName, selectCarType.picturePath, selectCarModel.modelId, selectCarModel.modelName,
                selectCarModel.picturePath, getGasnoCode(), tvCarRegistDate.getText().toString(),
                etTotalMileage.getText().toString(), tvInsuranceDate.getText().toString(), etEmergencyPhonenum.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddCarResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AddCarResult baseBean) {
                        if ("0".equals(baseBean.code)) {
//                            Intent intent = new Intent(AddCarActivity.this, BindBoxActivity.class);
//                            startActivity(intent);
//                            AddCarActivity.this.finish();
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
                        dismissLoading();
                    }
                });
    }

    public String getPlateNumber() {
        return selectCity + etLicenseNum.getText().toString();
    }

    //获取油号
    public int getGasnoCode() {
        int checkedRadioButtonId = rgOilType.getCheckedRadioButtonId();
        switch (checkedRadioButtonId) {
            case R.id.gasno_0:
                return 0;
            case R.id.gasno_92:
                return 92;
            case R.id.gasno_95:
                return 95;
            case R.id.gasno_98:
                return 98;
            default:
                return 0;
        }

    }

    //获取车型号 （大型车 小型车）
    public int getCarTypeCode() {
        int checkedRadioButtonId = rgCarType.getCheckedRadioButtonId();
        switch (checkedRadioButtonId) {
            case R.id.rb_bigCar:
                return 1;
            case R.id.rb_littleCar:
                return 0;
            default:
                return 0;
        }
    }


    @OnClick({R.id.bt_confirm, R.id.tv_selectCarType, R.id.tv_carRegistDate, R.id.tv_insuranceDate})
    public void onViewClicked(View view) {
        CommonUtils.hideSoftKeyboard(AddCarActivity.this);
        Intent intent;
        switch (view.getId()) {

            case R.id.tv_selectCarType:
                intent = new Intent(AddCarActivity.this, SelectCarActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.bt_confirm:
//                saveCarData();
                intent = new Intent(AddCarActivity.this, BindBoxActivity.class);
                startActivity(intent);
                AddCarActivity.this.finish();
                break;
            case R.id.tv_carRegistDate:
                final DatePickerDialog dialog = new DatePickerDialog(AddCarActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                String deadLine = year + "-" + (month + 1) + "-" + dayOfMonth;
                                Logger.w("abc     " + deadLine);
                                tvCarRegistDate.setText(deadLine);
                            }
                        },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                dialog.show();
                break;
            case R.id.tv_insuranceDate:
                final DatePickerDialog dialog2 = new DatePickerDialog(AddCarActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                String deadLine = year + "-" + (month + 1) + "-" + dayOfMonth;
                                Logger.w("abc     " + deadLine);
                                tvInsuranceDate.setText(deadLine);
                            }
                        },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                dialog2.show();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == SelectCarActivity.RESULT_CODE & data != null) {
            selectCarBrands = (CarBrandsBean) data.getSerializableExtra("selectCarBrands");
            selectCarType = data.getExtras().getParcelable("selectCarType");
            selectCarModel = data.getExtras().getParcelable("selectCarModel");
//            Logger.w("selectCarBrands" + selectCarBrands);
//            Logger.w("selectCarType" + selectCarType);
//            Logger.w("selectCarModel" + selectCarModel);
            if (selectCarModel != null) {
                tvSelectCarType.setText(selectCarModel.modelName);

            }
        }
    }

}
