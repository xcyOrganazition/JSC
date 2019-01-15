package cn.com.jinshangcheng.utils;

import android.content.Context;
import android.media.MediaPlayer;

public class MediaUtils {
    private static MediaPlayer mediaPlayer;

    public static void playMusic(Context context, int mediaResId) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(context, mediaResId);
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
