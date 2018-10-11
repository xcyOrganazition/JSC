package com.jinshangcheng.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinshangcheng.R;
import com.jinshangcheng.base.BaseActivity;


public class TittleBar extends RelativeLayout {
    private android.support.v7.widget.Toolbar toolbar;
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

    public void setTittle(String tittle) {
        tv_tittle.setText(tittle);
    }

    public void hideNavigation() {
        toolbar.setNavigationIcon(null);
    }

    public void showNavigation() {
        TypedValue typedValue = new TypedValue();
        context1.getTheme().resolveAttribute(android.R.attr.homeAsUpIndicator, typedValue, true);
        int resId = typedValue.resourceId;
        toolbar.setNavigationIcon(resId);
    }
}
