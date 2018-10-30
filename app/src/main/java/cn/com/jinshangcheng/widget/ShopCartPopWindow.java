package cn.com.jinshangcheng.widget;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import cn.com.jinshangcheng.R;

public class ShopCartPopWindow extends PopupWindow {

    private Context context;

    public ShopCartPopWindow(Context context) {
        super(context);
        this.context = context;

        setAnimationStyle(R.style.AnimationBottom);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);  //默认设置outside点击无响应
        setFocusable(true);
        setBackgroundDrawable(null);
        setWindowBackgroundAlpha(1);
        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_shop_cart, null);
        setContentView(contentView);

    }


    /**
     * 控制窗口背景的不透明度
     */
    private void setWindowBackgroundAlpha(float alpha) {
        Window window = ((Activity) context).getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = alpha;
        window.setAttributes(layoutParams);
    }
}
