package cn.com.jinshangcheng.ui.mine;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.AddCarResult;
import cn.com.jinshangcheng.bean.CarBean;
import cn.com.jinshangcheng.bean.CarBrandsBean;
import cn.com.jinshangcheng.config.ConstParams;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.ui.login.BindBoxActivity;
import cn.com.jinshangcheng.utils.CommonUtils;
import cn.com.jinshangcheng.utils.DateUtils;
import cn.com.jinshangcheng.widget.LicenseDialog;
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
    TextView spinner;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.et_licenseNum)
    EditText etLicenseNum;
    @BindView(R.id.et_vin)
    EditText etVin;
    @BindView(R.id.et_ein)
    EditText etEin;
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
    @BindView(R.id.gasno_92)
    RadioButton gasno92;
    @BindView(R.id.gasno_95)
    RadioButton gasno95;
    @BindView(R.id.gasno_98)
    RadioButton gasno98;
    @BindView(R.id.gasno_0)
    RadioButton gasno0;

    private final int REQUEST_CODE = 0x123;


    String selectCity;
    CarBrandsBean selectCarBrands;//车品牌
    CarTypeResult.DataEntity.CarTypesEntity selectCarType;//车型
    CarModelResult.DataEntity selectCarModel;//车款

    private LicenseDialog licenseDialog;//车牌选择Dialog
    private boolean isUpDateCar;
    private CarBean carBean;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_add_car;
    }

    @Override
    public void initData() {
        selectCity = ConstParams.cityArray[0];
        isUpDateCar = getIntent().getBooleanExtra("fromCarManage", false);
        if (isUpDateCar) {
            carBean = (CarBean) getIntent().getSerializableExtra("carBean");

        }
    }

    @Override
    public void initView() {
        spinner.setText(selectCity);
        if (isUpDateCar) {
            initCarData();
        }
    }

    //填充车辆数据
    public void initCarData() {
        if (carBean != null) {
            if ("0".equals(carBean.getCartype())) {
                rbLittleCar.setChecked(true);
            } else {
                rbBigCar.setChecked(true);
            }
            spinner.setText(String.valueOf(carBean.getPlatenumber().charAt(0)));
            selectCity = String.valueOf(carBean.getPlatenumber().charAt(0));
            etLicenseNum.setText(carBean.getPlatenumber().substring(1));
            etVin.setText(carBean.getVin());
            etEin.setText(carBean.getEin());
            tvSelectCarType.setText(carBean.getModel());
            selectCarBrands = new CarBrandsBean();
            selectCarBrands.brandName = carBean.getBrandname();
            selectCarBrands.picturePath = carBean.getBrandpath();
            selectCarType = new CarTypeResult.DataEntity.CarTypesEntity();
            selectCarType.typeName = carBean.getTypename();
            selectCarType.picturePath = carBean.getTypepath();
            selectCarModel = new CarModelResult.DataEntity();
            selectCarModel.modelName = carBean.getModel();
            selectCarModel.picturePath = carBean.getModelpath();
            tvCarRegistDate.setText(DateUtils.getYMDTime(carBean.getRegistdate()));
            etTotalMileage.setText(String.valueOf(carBean.getTotalmileage()));
            tvInsuranceDate.setText(DateUtils.getYMDTime(carBean.getInsurancedeadline()));
            switch (carBean.getGasno()) {
                case 0:
                    gasno0.setChecked(true);
                    break;
                case 92:
                    gasno92.setChecked(true);
                    break;
                case 95:
                    gasno95.setChecked(true);
                    break;
                case 98:
                    gasno98.setChecked(true);
                    break;
                default:
                    gasno92.setChecked(true);
                    break;
            }

//            etEmergencyPhonenum.setText(carBean.get);
        }
    }

    public void saveCarData() {
        showLoading();
        RetrofitService.getRetrofit().addCar(MyApplication.getUserId(), getCarTypeCode(), getPlateNumber(),
                etVin.getText().toString(), etEin.getText().toString(), selectCarBrands.brandName, selectCarBrands.picturePath,
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
                            Intent intent = new Intent(AddCarActivity.this, BindBoxActivity.class);
                            startActivity(intent);
                            AddCarActivity.this.finish();
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

    //获取车型号 小型车0、大型车1
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

    //校验数据是否全部填写
    public boolean cheackData() {

        return true;
    }


    @OnClick({R.id.bt_confirm, R.id.tv_selectCarType, R.id.tv_carRegistDate, R.id.tv_insuranceDate, R.id.spinner})
    public void onViewClicked(View view) {
        CommonUtils.hideSoftKeyboard(AddCarActivity.this);
        Intent intent;
        switch (view.getId()) {

            case R.id.tv_selectCarType:
                intent = new Intent(AddCarActivity.this, SelectCarActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.bt_confirm:
                if (cheackData()) {
                    saveCarData();
                }
//                intent = new Intent(AddCarActivity.this, BindBoxActivity.class);
//                startActivity(intent);
//                AddCarActivity.this.finish();
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
            case R.id.spinner:
                if (licenseDialog == null) {
                    licenseDialog = new LicenseDialog();
                }
                licenseDialog.show(getSupportFragmentManager(), "license");
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

    public void selectCity(String cityName) {
        selectCity = cityName;
        spinner.setText(selectCity);
        licenseDialog.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
