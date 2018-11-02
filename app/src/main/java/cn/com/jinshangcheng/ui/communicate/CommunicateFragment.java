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
        Button bt = getView().findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mediaPlayer.prepareAsync();
                playMusic();
            }
        });
    }

    public void playMusic() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.reset();
            mediaPlayer = MediaPlayer.create(getHoldingActivity(), R.raw.car_start);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }
            });
        }
    }
}
