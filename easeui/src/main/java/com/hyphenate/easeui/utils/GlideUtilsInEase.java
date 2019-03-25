package com.hyphenate.easeui.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import java.util.UUID;

/**
 * Glide图片渲染工具类
 */
public class GlideUtilsInEase {


    public static void loadEaseImage(Activity context, int resourceId, int placeHolderResource, ImageView imageView) {
        RequestOptions currentOptions = new RequestOptions()
                .centerCrop()
                .placeholder(placeHolderResource)
                .priority(Priority.NORMAL)
                .signature(new ObjectKey(UUID.randomUUID().toString()))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(context.getApplicationContext())
                .load(resourceId)
                .apply(currentOptions)
                .into(imageView);

    }

    public static void loadEaseImage(Context context, String resourcepath, int placeHolderResource, ImageView imageView) {
        RequestOptions currentOptions = new RequestOptions()
                .centerCrop()
                .placeholder(placeHolderResource)
                .priority(Priority.NORMAL)
                .signature(new ObjectKey(UUID.randomUUID().toString()))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(context.getApplicationContext())
                .load(resourcepath)
                .apply(currentOptions)
                .into(imageView);

    }


    public static void loadRoundeImage(final Context context, String url, final ImageView imageView) {
        Glide.with(context.getApplicationContext())
                .load(url)
                .into(imageView);
    }
}
