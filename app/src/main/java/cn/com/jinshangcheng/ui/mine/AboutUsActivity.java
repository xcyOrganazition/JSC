package cn.com.jinshangcheng.ui.mine;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.VersionBean;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.widget.UpDateDialog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.ic_logo)
    ImageView icLogo;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.iv_redPoint)
    ImageView ivRedPoint;

    private boolean needUpdate;//是否需要更新
    private boolean forceUpdate;//是否强制更新

    @Override
    public int setContentViewResource() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        tvVersion.setText(String.format("当前版本：v %s", getVerName()));
        ivRedPoint.setVisibility(View.INVISIBLE);
        checkNewVersion();
    }

    @OnClick(R.id.ll_checkNewVersion)
    public void onViewClicked() {
        if (needUpdate) {
            UpDateDialog upDateDialog = new UpDateDialog();
            Bundle bundle = new Bundle();
            bundle.putString("updateContent", "优化了部分功能。");
            bundle.putBoolean("forceUpdate", forceUpdate);
            upDateDialog.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            upDateDialog.show(getFragmentManager(), "updateDialog");
        } else {
            showToast("已经是最新版本");
        }
    }

    private void checkNewVersion() {
        RetrofitService.getRetrofit().checkNewVersion(MyApplication.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<VersionBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<VersionBean> baseBean) {
                        if ("0".equals(baseBean.code)) {
                            String newVersionString = baseBean.data.appversion;

                            checkNeedUpdate(newVersionString.split("\\."));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void checkNeedUpdate(String[] newVersionStrs) {
        String appVersion = getVerName();
        String[] curVersionStrs = appVersion.split("\\.");
        if (Integer.parseInt(newVersionStrs[0]) > Integer.parseInt(curVersionStrs[0])) {
            forceUpdate = true;
            needUpdate = true;
        } else if (Integer.parseInt(newVersionStrs[1]) > Integer.parseInt(curVersionStrs[1])) {
            forceUpdate = true;
            needUpdate = true;
        } else if (Integer.parseInt(newVersionStrs[2]) > Integer.parseInt(curVersionStrs[2])) {
            forceUpdate = false;
            needUpdate = true;
        } else {
            forceUpdate = false;
            needUpdate = false;
        }
        forceUpdate = true;
        needUpdate = true;
    }


    /**
     * 获取版本号名称
     *
     * @return
     */
    public String getVerName() {
        String verName = "";
        try {
            verName = this.getPackageManager().
                    getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

}
