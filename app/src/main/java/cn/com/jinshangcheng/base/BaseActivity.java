package cn.com.jinshangcheng.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.jinshangcheng.utils.ActivityUtils;
import cn.com.jinshangcheng.widget.MyDialog;


public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    private Unbinder unbind;
    private MyDialog loading;

    /**
     * 初始化布局
     */
    public abstract int setContentViewResource();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     *
     */
    public abstract void initView();

    /**
     * 获取跟布局
     *
     * @return contentView
     */
    public View getContentView() {
        return View.inflate(this, setContentViewResource(), null);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            StatusBarUtil.StatusBarLightMode(this);
//        }

        setContentView(setContentViewResource());
        unbind = ButterKnife.bind(this);
        ActivityUtils.addActivity(this);
        initData();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        CommonUtils.hideSoftKeyboard(this);
        unbind.unbind();
        if (loading != null) {
            loading.dismiss();
            loading = null;
        }
        ActivityUtils.removeActivity(this);

    }

    //显示loading界面
    public void showLoading() {
        if (loading == null) {
            loading = new MyDialog(this);
        }
        loading.show();

    }

    //隐藏loading界面
    public void dismissLoading() {
        if (loading != null && loading.isShowing()) {
            loading.dismiss();
        }
    }

    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
