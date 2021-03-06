package cn.com.jinshangcheng.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import java.util.UUID;

import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.net.RetrofitService;

/**
 * Glide图片渲染工具类
 */
public class GlideUtils {
    public static RequestOptions options = new RequestOptions()
            .centerInside()
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .priority(Priority.NORMAL)
            .signature(new ObjectKey(UUID.randomUUID().toString()))
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
    public static RequestOptions optionsCenter = new RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .priority(Priority.NORMAL)
            .signature(new ObjectKey(UUID.randomUUID().toString()))
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);


    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context.getApplicationContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void loadJSGImage(Context context, String url, ImageView imageView) {
        if (url.contains("../")) {
            url = RetrofitService.HOST + url.replace("../", "/");
        }

        Glide.with(context.getApplicationContext())
                .load(url)
                .apply(optionsCenter)
                .into(imageView);
    }

    public static void loadHeadImage(Context context, String url, ImageView imageView, boolean needAddHost) {
        if (needAddHost) {
            url = RetrofitService.HOST + "/" + url;
        }
        Glide.with(context.getApplicationContext())
                .load(url)
                .apply(optionsCenter)
                .into(imageView);
    }

    public static void loadCircleImage(final Context context, String url, final ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void loadRoundeImage(final Context context, String url, final ImageView imageView) {
        Glide.with(context.getApplicationContext())
                .load(url)
                .into(imageView);
    }
}
