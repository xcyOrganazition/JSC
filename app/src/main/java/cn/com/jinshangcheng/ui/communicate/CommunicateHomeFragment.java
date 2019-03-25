package cn.com.jinshangcheng.ui.communicate;


import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;

import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseFragment;

/**
 * 交流模块
 * 会话列表模块
 */
public class CommunicateHomeFragment extends BaseFragment {

    private static CommunicateHomeFragment fragment;
    private MediaPlayer mediaPlayer;
    private ConversationListFragment conversationListFragment;

    public CommunicateHomeFragment() {
        // Required empty public constructor
    }

    public static CommunicateHomeFragment getInstance() {
        if (fragment == null) {
            synchronized (CommunicateHomeFragment.class) {
                if (fragment == null) {
                    fragment = new CommunicateHomeFragment();
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
        conversationListFragment = new ConversationListFragment();
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, conversationListFragment)
                    .show(conversationListFragment)
                    .commit();
        }
    }
}
