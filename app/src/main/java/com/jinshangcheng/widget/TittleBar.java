package com.jinshangcheng.widget;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinshangcheng.R;
import com.jinshangcheng.base.BaseActivity;

/**
 * 标题栏 默认隐藏返回键和菜单键
 */
public class TittleBar extends RelativeLayout {
    private Toolbar toolbar;
    private BaseActivity context1;
    private TextView tv_tittle;



    public TittleBar(Context context) {
        super(context);

        init(context, null);
    }

    public TittleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TittleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
//    public TittleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr);
//        init(context, attrs);
//    }

    private void init(final Context context, AttributeSet attrs) {
        context1 = (BaseActivity) context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_tittle_bar, this);
        tv_tittle = view.findViewById(R.id.tv_tittle);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                context1.finish();
            }
        });
    }

    /**
     * 标题栏标题
     *
     * @param tittle 标题内容
     */
    public void setTittle(String tittle) {
        tv_tittle.setText(tittle);
    }

    /**
     * 隐藏返回键
     */
    public void hideNavigation() {
        toolbar.setNavigationIcon(null);
    }

    /**
     * 显示返回键
     */
    public void showNavigation() {
        TypedValue typedValue = new TypedValue();
        context1.getTheme().resolveAttribute(android.R.attr.homeAsUpIndicator, typedValue, true);
        int resId = typedValue.resourceId;
        toolbar.setNavigationIcon(resId);
    }

    /**
     * 设置右上角菜单
     *
     * @param menuResId
     * @param listener
     */
    public void setAction(int menuResId, android.support.v7.widget.Toolbar.OnMenuItemClickListener listener) {
        toolbar.inflateMenu(menuResId);
        toolbar.setOnMenuItemClickListener(listener);
    }

    /**
     * 没有Action菜单
     */
    public void setNoAction() {
        toolbar.getMenu().clear();

    }


}
