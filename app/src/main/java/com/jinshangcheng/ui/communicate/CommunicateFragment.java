package com.jinshangcheng.ui.communicate;


import android.view.LayoutInflater;
import android.view.View;

import com.jinshangcheng.R;
import com.jinshangcheng.base.BaseFragment;

/**
 * 交流模块
 */
public class CommunicateFragment extends BaseFragment {

    private static CommunicateFragment fragment;

    public CommunicateFragment() {
        // Required empty public constructor
    }

    public static CommunicateFragment getInstance() {
        if (fragment == null) {
            synchronized (CommunicateFragment.class) {
                if (fragment == null) {
                    fragment = new CommunicateFragment();
                }
            }
        }
        return fragment;
    }


    @Override
    public View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_communicate, null, false);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

}
