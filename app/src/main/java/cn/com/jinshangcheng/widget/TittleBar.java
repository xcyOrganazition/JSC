package cn.com.jinshangcheng.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.jinshangcheng.R;


/**
 * 标题栏 默认隐藏返回键和菜单键
 */
public class TittleBar extends RelativeLayout {
    private Toolbar toolbar;
    private Activity context1;
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
        this(context, attrs);
    }
//    public TittleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr);
//        init(context, attrs);
//    }

    private void init(final Context context, AttributeSet attrs) {
        context1 = (Activity) context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_tittle_bar, this);
        tv_tittle = view.findViewById(R.id.tv_tittle);
        toolbar = view.findViewById(R.id.toolbar);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.tittleBar);
        this.setTittle(ta.getString(R.styleable.tittleBar_centerText));
        if (ta.getBoolean(R.styleable.tittleBar_backVisible, true)) {
            this.showNavigation();
        } else {
            this.hideNavigation();
        }
        ta.recycle();
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
        if (!TextUtils.isEmpty(tittle)) {
            tv_tittle.setText(tittle);
        }
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
