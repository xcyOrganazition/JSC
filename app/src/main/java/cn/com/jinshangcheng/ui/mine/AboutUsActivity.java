package cn.com.jinshangcheng.ui.mine;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.widget.UpDateDialog;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.ic_logo)
    ImageView icLogo;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.iv_redPoint)
    ImageView ivRedPoint;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        ivRedPoint.setVisibility(View.INVISIBLE);

    }

    @OnClick(R.id.ll_checkNewVersion)
    public void onViewClicked() {
        UpDateDialog upDateDialog = new UpDateDialog();
        Bundle bundle = new Bundle();
        bundle.putString("updateContent", "修复了某某功能。优化了某某功能。");
        bundle.putBoolean("forceUpdate", true);
        upDateDialog.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        upDateDialog.show(getFragmentManager(), "updateDialog");


//        showToast("已经是最新版本");
    }
}
