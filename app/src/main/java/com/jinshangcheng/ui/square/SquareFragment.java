package com.jinshangcheng.ui.square;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinshangcheng.R;
import com.jinshangcheng.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 广场模块
 */
public class SquareFragment extends BaseFragment {

    private static SquareFragment fragment;

    public SquareFragment() {
        // Required empty public constructor
    }

    public static SquareFragment getInstance() {
        if (fragment == null) {
            synchronized (SquareFragment.class) {
                if (fragment == null) {
                    fragment = new SquareFragment();
                }
            }
        }
        return fragment;
    }


    @Override
    public View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_square, null, false);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }


    @OnClick({R.id.showLoading, R.id.hideLoading})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.showLoading:
                showLoading();
                break;
            case R.id.hideLoading:
                dismissLoading();
                break;
        }
    }
}
