package cn.com.jinshangcheng.ui.car;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.TroubleBean;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.utils.ArrayUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 故障详情
 */
public class TroubleDetailActivity extends BaseActivity {

    @BindView(R.id.ll_container)
    LinearLayout llContainer;
    private ArrayList<TroubleBean> troubleList;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_trouble_detail;
    }

    @Override
    public void initData() {
        troubleList = new ArrayList<>();
    }

    @Override
    public void initView() {
        getTroubleData();
    }

    private void getTroubleData() {
        showLoading();
        RetrofitService.getRetrofit().getCarTroubleInfo(MyApplication.getUserId(), MyApplication.getCarId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<List<TroubleBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<List<TroubleBean>> baseBean) {
                        if ("0".equals(baseBean.code)) {
                            troubleList = (ArrayList<TroubleBean>) baseBean.data;
                            if (ArrayUtils.hasContent(troubleList)) {
                                refreshListView();
                            } else {
                                showToast("无故障信息");
                            }
                        } else {
                            showToast(baseBean.message);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        dismissLoading();
                    }
                });
    }

    private void refreshListView() {
        llContainer.removeAllViews();
        for (TroubleBean troubleBean : troubleList) {
            View content = getLayoutInflater().inflate(R.layout.layout_trouble, null);
            TextView tvDtcName = content.findViewById(R.id.tv_dtcName);
            TextView tvDtc = content.findViewById(R.id.tv_dtc);
            TextView tvCauseAnalysis = content.findViewById(R.id.tv_causeAnalysis);
            TextView tvConsequences = content.findViewById(R.id.tv_consequences);
            TextView tvSuggestion = content.findViewById(R.id.tv_suggestion);
            tvDtcName.setText(String.format("故障名称：%s", troubleBean.dtcName));
            tvDtc.setText(String.format("故障码：%s", troubleBean.dtc));
            tvCauseAnalysis.setText(String.format("故障产生原因：\n%s", troubleBean.causeAnalysis));
            tvConsequences.setText(String.format("故障可能后果：\n%s", troubleBean.consequences));
            tvSuggestion.setText(String.format("专家建议：\n%s", troubleBean.suggestion));
            llContainer.addView(content);
        }
    }
}
