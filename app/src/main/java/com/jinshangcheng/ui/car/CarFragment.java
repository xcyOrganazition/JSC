package com.jinshangcheng.ui.car;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.jinshangcheng.R;
import com.jinshangcheng.adapter.CarListPagerAdapter;
import com.jinshangcheng.base.BaseFragment;
import com.jinshangcheng.bean.Car;
import com.jinshangcheng.utils.DensityUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;


/**
 * 汽车还是爱车Fragment
 */
public class CarFragment extends BaseFragment implements CarContract.IView {


    @BindView(R.id.vp_carList)
    ViewPager vpCarList;


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

    @Override
    public void showCarList() {

    }

}
