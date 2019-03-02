package cn.com.jinshangcheng.ui.communicate;


import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseFragment;

/**
 * 交流模块
 */
public class CommunicateFragment extends BaseFragment {

    private static CommunicateFragment fragment;
    private MediaPlayer mediaPlayer;

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
