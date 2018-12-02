package cn.com.jinshangcheng.ui.mine;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.utils.SharedPreferenceUtils;
import cn.com.jinshangcheng.widget.GestureDialog;
import cn.com.jinshangcheng.widget.TittleBar;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static cn.com.jinshangcheng.widget.GestureDialog.SP_KEY;

/**
 * 隐私管理
 */
public class PrivacyActivity extends BaseActivity {

    @BindView(R.id.tittleBar)
    TittleBar tittleBar;
    @BindView(R.id.switch_local)
    Switch switchLocal;

    boolean isProtect;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_pirvacy;
    }

    @Override
    public void initData() {
        //是否轨迹保护 0 不保护，1 保护
//        isProtect = 1 == MyApplication.getUserBean().travelprotect;//根据后台存储的状态判断是否开启保护
        isProtect = !"".equals(SharedPreferenceUtils.getStringSP(SP_KEY));//根据本地是否有密码判断保护状态


    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initView() {
        tittleBar.setTittle("隐私管理");
        switchLocal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        Logger.e("show");
                        GestureDialog dialog = new GestureDialog();
                        dialog.show(getSupportFragmentManager(), "Tag");
                        break;
                }
                return true;
            }
        });
        switchLocal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });

    }


    /**
     * 开启轨迹保护
     */
    public void lockLocal(final String pwd) {
        RetrofitService.getRetrofit().startProtect(MyApplication.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        if ("0".equals(baseBean.code)) {
                            SharedPreferenceUtils.setStringSP(SP_KEY, pwd);//保存密码
                            switchLocal.setChecked(true);
                        }
                        showToast(baseBean.message);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 关闭轨迹保护
     */
    public void unLockLocal() {
        RetrofitService.getRetrofit().stopProtect(MyApplication.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        if ("0".equals(baseBean.code)) {
                            SharedPreferenceUtils.setStringSP(SP_KEY, "");//清空密码
                            switchLocal.setChecked(false);
                        }
                        showToast(baseBean.message);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
