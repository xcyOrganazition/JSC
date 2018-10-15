package com.jinshangcheng.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.jinshangcheng.R;

/**
 * 自定义 下拉刷新头部
 */
public class MaterialRefreshHeader extends com.scwang.smartrefresh.header.MaterialHeader {
    public MaterialRefreshHeader(Context context) {
        super(context);
    }

    public MaterialRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeColors(getResources().getColor(R.color.themeColor));
    }

    public MaterialRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setColorSchemeColors(getResources().getColor(R.color.themeColor));
    }

    
}

