package cn.com.jinshangcheng.ui.mine;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.net.RetrofitService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UnbindBoxActivity extends BaseActivity {

    @BindView(R.id.et_boxCode)
    EditText etBoxCode;
    @BindView(R.id.et_carCode)
    EditText etCarCode;
    private String carId;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_unbind_box;
    }

    @Override
    public void initData() {
        carId = getIntent().getStringExtra("carId");
    }

    @Override
    public void initView() {
    }


    @OnClick({R.id.bt_bindBox, R.id.bt_notBindNow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_bindBox:
                if (checkInput()) {
                    unbindBox();
                }
                break;
            case R.id.bt_notBindNow:
                finish();
                break;
        }
    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(etBoxCode.getText().toString())) {
            showToast("请填写盒子识别码");
            return false;
        }
        if (TextUtils.isEmpty(etCarCode.getText().toString())) {
            showToast("请填写车机序列号");
            return false;
        }
        return true;
    }

    public void unbindBox() {
        showLoading();
        RetrofitService.getRetrofit().unbindBox(MyApplication.getUserId(), carId, etBoxCode.getText().toString(),
                etCarCode.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        dismissLoading();
                        showToast(baseBean.message);
                        if ("0".equals(baseBean.code)) {
                            setResult(AddCarActivity.RESULT_CODE);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
