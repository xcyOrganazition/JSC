package com.jinshangcheng.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jinshangcheng.R;
import com.jinshangcheng.base.BaseFragment;
import com.jinshangcheng.ui.car.CarContract;
import com.jinshangcheng.ui.car.CarPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 我的Fragment
 */
public class MineFragment extends BaseFragment implements CarContract.IView {


    private static MineFragment mineFragment;
    Unbinder unbinder;
    @BindView(R.id.iv_headImg)
    ImageView ivHeadImg;
    Unbinder unbinder1;

    private CarContract.IPresenter mPresenter;

    public MineFragment() {
        // Required empty public constructor
        mPresenter = new CarPresenter(this);
    }

    public static MineFragment getInstance() {
        if (mineFragment == null) {
            synchronized (MineFragment.class) {
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                }
            }
        }
        return mineFragment;
    }


    @Override
    public View createView(LayoutInflater inflater) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_mine, null, false);
    }


    @Override
    public void initData() {

    }

    @Override
    public void initView() {
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


    @OnClick(R.id.tv_people)
    public void onViewClicked() {
    }


    @OnClick({R.id.iv_headImg, R.id.tv_people, R.id.tv_money, R.id.tv_privacy, R.id.tv_address, R.id.tv_car, R.id.tv_order, R.id.tv_card, R.id.tv_about_us})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_headImg:
                break;
            case R.id.tv_people:
                break;
            case R.id.tv_money:
                break;
            case R.id.tv_privacy:
                intent = new Intent(getHoldingActivity(), PrivacyActivity.class);
                break;
            case R.id.tv_address:
                intent = new Intent(getHoldingActivity(), AddressManageActivity.class);
                break;
            case R.id.tv_car:
                break;
            case R.id.tv_order:
                break;
            case R.id.tv_card:
                break;
            case R.id.tv_about_us:
                break;
        }
        if (intent != null) {
            getActivity().startActivity(intent);
        }
    }
}
