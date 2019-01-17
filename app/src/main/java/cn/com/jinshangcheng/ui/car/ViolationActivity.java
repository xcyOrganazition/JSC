package cn.com.jinshangcheng.ui.car;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.adapter.ViolationAdapter;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BaseBean;
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
            tvViolationNum.setText(String.valueOf(violationDetailList.size()));
            ViolationAdapter adapter = new ViolationAdapter(violationDetailList, ViolationActivity.this);
            recyclerView.setLayoutManager(new LinearLayoutManager(ViolationActivity.this));
            recyclerView.setAdapter(adapter);
        }

    }

    public void getViolationData() {
        showLoading();
        RetrofitService.getRetrofit().getViolation(MyApplication.getCarId(),
                MyApplication.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<ViolationBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<ViolationBean> violationBean) {
                        dismissLoading();
                        if ("0".equals(violationBean.code)) {
                            refreshListView(violationBean.data.getViolationDetailList());
                        } else if ("1".equals(violationBean.code)) {
                            showToast(violationBean.message);
                            tvViolationNum.setText("0");
                        } else if ("2".equals(violationBean.code)) {
                            showToast(violationBean.message);
                            tvViolationNum.setText("0");
                        } else if ("3".equals(violationBean.code)) {
                            tvViolationNum.setText(violationBean.message);
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


}
