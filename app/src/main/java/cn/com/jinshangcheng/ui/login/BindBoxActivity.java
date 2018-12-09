package cn.com.jinshangcheng.ui.login;

import android.content.Intent;
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
import cn.com.jinshangcheng.ui.MainActivity;
import cn.com.jinshangcheng.ui.mine.AddCarActivity;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 绑定盒子
 */
public class BindBoxActivity extends BaseActivity {

    @BindView(R.id.et_boxCode)
    EditText etBoxCode;
    @BindView(R.id.et_carCode)
    EditText etCarCode;
    private boolean isFromCarManage;
    private String carId;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_bind_box;
    }

    @Override
    public void initData() {
        isFromCarManage = getIntent().getBooleanExtra("isFromCarManage", false);
        carId = getIntent().getStringExtra("carId");
    }

    @Override
    public void initView() {
//        setResult(AddCarActivity.RESULT_CODE);
    }


    @OnClick({R.id.bt_bindBox, R.id.bt_notBindNow, R.id.tv_buy})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.bt_bindBox:
                if (checkInput()) {
                    bindBox();
                }
                break;
            case R.id.bt_notBindNow:
                setResult(AddCarActivity.RESULT_CODE);
                this.finish();
                return;
            case R.id.tv_buy:
                intent = new Intent(BindBoxActivity.this, MainActivity.class);
                startActivity(intent);
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

    public void bindBox() {
        showLoading();
        RetrofitService.getRetrofit().bindBox(MyApplication.getUserId(), carId, etBoxCode.getText().toString(),
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
                            if (isFromCarManage) {
                                setResult(AddCarActivity.RESULT_CODE);
                                finish();
                            } else {
//                                Intent intent = new Intent(BindBoxActivity.this, MainActivity.class);
//                                startActivity(intent);
                            }
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
