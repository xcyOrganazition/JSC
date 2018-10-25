package cn.com.jinshangcheng.ui.car;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.MapView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.adapter.CarListPagerAdapter;
import cn.com.jinshangcheng.base.BaseFragment;
import cn.com.jinshangcheng.bean.Car;
import cn.com.jinshangcheng.utils.DensityUtil;
import platform.cston.httplib.bean.AuthorizationInfo;


/**
 * 汽车还是爱车Fragment
 */
public class CarFragment extends BaseFragment implements CarContract.IView {


    @BindView(R.id.vp_carList)
    ViewPager vpCarList;
    @BindView(R.id.tv_mileNum)
    TextView tvMileNum;
    @BindView(R.id.tv_fuelNum)
    TextView tvFuelNum;
    @BindView(R.id.tv_checkTime)
    TextView tvCheckTime;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.bd_mapView)
    MapView bdMapView;

    private static CarFragment carFragment;
    private Car car;
    private List<Car> carList = new ArrayList<>();

    private CarContract.IPresenter mPresenter;

    public CarFragment() {
        // Required empty public constructor
        mPresenter = new CarPresenter(this);
    }

    public static CarFragment getInstance() {
        if (carFragment == null) {
            synchronized (CarFragment.class) {
                if (carFragment == null) {
                    carFragment = new CarFragment();
                }
            }
        }
        return carFragment;
    }


    @Override
    public View createView(LayoutInflater inflater) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_car, null, false);
    }


    @Override
    public void initData() {
        car = new Car("卡宴", "京A23282", "保时捷", "保时捷系列");
        carList.add(car);
        carList.add(car);

    }

    @Override
    public void initView() {
        CarListPagerAdapter adapter = new CarListPagerAdapter(carList, getHoldingActivity());
        vpCarList.setAdapter(adapter);
        vpCarList.setPageMargin(DensityUtil.dip2px(getHoldingActivity(), 6));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter.getCarList();

    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    @Override
    public void setPresenter(CarContract.IPresenter presenter) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    public void toastErrorMsg(String errorMsg) {
        Toast.makeText(getHoldingActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 填充车辆ViewPager数据
     */
    @Override
    public void showCarList(ArrayList<AuthorizationInfo.Cars> carList) {

        Logger.w("填充车辆 " + carList);

    }


    @OnClick({R.id.ll_check, R.id.ll_report, R.id.ll_violation, R.id.ll_help, R.id.tv_checkDetail})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_check://一键检测:
                break;
            case R.id.ll_report://用车报告
                break;
            case R.id.ll_violation://查询违章
                break;
            case R.id.ll_help://道路救援
                break;
            case R.id.tv_checkDetail://查询检测详情
                break;
        }
    }
}
