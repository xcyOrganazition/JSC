package cn.com.jinshangcheng.ui.car;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.CarBean;
import cn.com.jinshangcheng.bean.ViolationBean;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.utils.ArrayUtils;
import cn.com.jinshangcheng.utils.GlideUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * 查询违章
 */
public class ViolationActivity extends BaseActivity {


    @BindView(R.id.tv_carLicense)
    TextView tvCarLicense;
    @BindView(R.id.iv_branchImg)
    ImageView ivBranchImg;
    @BindView(R.id.tv_carBrand)
    TextView tvCarBrand;
    @BindView(R.id.tv_carType)
    TextView tvCarType;
    @BindView(R.id.iv_carImg)
    ImageView ivCarImg;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_violationNum)
    TextView tvViolationNum;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_violation;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        initTopView();
        getViolationData();
    }

    private void initTopView() {
        CarBean bean = MyApplication.getCurrentCarBean();
        tvTip.setFocusable(false);
        tvTip.setVisibility(View.GONE);
        tvCarLicense.setText(bean.getPlatenumber());
        tvCarBrand.setText(bean.getBrandname());
        GlideUtils.loadImage(getApplicationContext(), bean.getBrandpath(), ivBranchImg);
        GlideUtils.loadImage(getApplicationContext(), bean.getBrandpath(), ivCarImg);
        tvCarType.setText(bean.getTypename());
    }

    public void refreshListView(List<ViolationBean.ViolationDetail> violationDetailList) {
        if (!ArrayUtils.hasContent(violationDetailList)) {
            //没有违章数据
            tvViolationNum.setText("0");

        } else {
            //有违章数据
            tvViolationNum.setText(violationDetailList.size());

        }

    }

    public void getViolationData() {
        showLoading();
        RetrofitService.getRetrofit().getViolation(MyApplication.getCarId(),
                MyApplication.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ViolationBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ViolationBean violationBean) {
                        dismissLoading();
                        if ("0".equals(violationBean.code)) {
                            refreshListView(violationBean.getViolationDetailList());
                        } else {
                            showToast(violationBean.message);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                        e.printStackTrace();
                        showToast("查询失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}