package com.jinshangcheng.ui.position;


import android.view.LayoutInflater;
import android.view.View;

import com.jinshangcheng.R;
import com.jinshangcheng.base.BaseFragment;

/**
 * 位置模块
 */
public class PositionFragment extends BaseFragment {
    private static PositionFragment positionFragment;

    public PositionFragment() {
        // Required empty public constructor
    }

    public static PositionFragment getInstance() {
        if (positionFragment == null) {
            synchronized (PositionFragment.class) {
                if (positionFragment == null) {
                    positionFragment = new PositionFragment();
                }
            }
        }
        return positionFragment;
    }


    @Override
    public View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_position, null, false);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

}
