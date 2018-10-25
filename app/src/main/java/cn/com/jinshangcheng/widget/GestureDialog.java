package cn.com.jinshangcheng.widget;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.ui.mine.PrivacyActivity;
import cn.com.jinshangcheng.utils.SharedPreferenceUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 手势密码弹窗
 */
public class GestureDialog extends DialogFragment {

    @BindView(R.id.tv_gestureNote)
    TextView tvGestureNote;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.lock_view)
    PatternLockView lockView;
    Unbinder unbinder;

    public static final int FIRST_INPUT = 1;//首次录入
    public static final int SECOND_INPUT = 2;//二次录入
    public static final int DO_UNLOCK = 3;//解锁模式

    public static final String SP_KEY = "GESTURE";//spKey

    private int curMode;//当前模式
    private String firstPwd = "";//首次录入的密码
    private String secondPwd = "";//二次录入的密码
    private String truePwd = "";//正确的密码

    private PatternLockViewListener mPatternLockViewListener;
    private PrivacyActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = this.getDialog().getWindow();
        //去掉dialog默认的padding
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置dialog的位置在底部
        lp.gravity = Gravity.CENTER;
        //设置dialog的动画
        lp.windowAnimations = android.R.anim.fade_in;
        window.setAttributes(lp);
        window.setBackgroundDrawable(new ColorDrawable());
        final View view = inflater.inflate(R.layout.dialog_gesture, null);
        unbinder = ButterKnife.bind(this, view);
        initLockView();
        activity = (PrivacyActivity) getActivity();

        getPwdAndInitCurMode();
        return view;

    }

    /**
     * 获取密码 和当前状态
     */
    private void getPwdAndInitCurMode() {

        truePwd = SharedPreferenceUtils.getStringSP(SP_KEY);
        if (truePwd.equals("")) {//没有密码 则录入密码
            curMode = FIRST_INPUT;
            tvTip.setText("请开始绘制");
        } else {//已有密码 为解锁模式
            curMode = DO_UNLOCK;
            tvTip.setText("请输入手势密码");
        }
    }

    /**
     * 初始化手势区
     */
    private void initLockView() {
        mPatternLockViewListener = new PatternLockViewListener() {
            @Override
            public void onStarted() {
                Logger.w("Pattern drawing started");
            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {
                Logger.w("Pattern progress: " +
                        PatternLockUtils.patternToString(lockView, progressPattern));
            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                String pwd = PatternLockUtils.patternToString(lockView, pattern);
                Logger.w("Pattern complete: " +
                        pwd);
                setTipText(pwd);


            }

            @Override
            public void onCleared() {
                Logger.w("Pattern has been cleared");
            }
        };
        lockView.addPatternLockListener(mPatternLockViewListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 设置提示文字以及手势区域
     *
     * @param pwd
     */
    private void setTipText(String pwd) {
        lockView.setInputEnabled(false);//禁止手势录入
        if (pwd.length() < 4) {
            tvTip.setText("请至少连接四个点");
            lockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
            autoClear(false);
            return;
        }
        switch (curMode) {
            case FIRST_INPUT:
                firstPwd = pwd;
                tvTip.setText("绘制成功");
                autoClear(true);
                break;
            case SECOND_INPUT:
                secondPwd = pwd;
                if (firstPwd.equals(secondPwd)) {//两次录入一致
                    tvTip.setText("手势密码设置成功");
                    SharedPreferenceUtils.setStringSP(SP_KEY, secondPwd);//保存密码
                    autoClear(true);
                } else {//两次输入不一致
                    tvTip.setText("两次密码不一致，请重新绘制");
                    lockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                    autoClear(false);
                }
                break;
            case DO_UNLOCK:
                if (truePwd.equals(pwd)) {//密码输入正确
                    tvTip.setText("解锁成功");
                    SharedPreferenceUtils.setStringSP(SP_KEY, secondPwd);//保存密码
                    autoClear(true);
                } else {//密码错误
                    tvTip.setText("密码不正确");
                    lockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                    autoClear(false);
                }
                break;

        }
    }

    /**
     * 重置lockView 延迟之后
     *
     * @param isCorrect 是否正确
     */
    public void autoClear(final boolean isCorrect) {
        new CountDownTimer(1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                lockView.clearPattern();
                lockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
                lockView.setInputEnabled(true);//允许手势录入
                switch (curMode) {
                    case FIRST_INPUT:
                        if (!isCorrect) {//绘制错误 重新绘制
                            tvTip.setText("请开始绘制");
                        } else if (isCorrect) { //绘制成功 准备开始二次绘制
                            curMode = SECOND_INPUT;
                            tvTip.setText("请再次输入手势密码");
                        }
                        break;
                    case SECOND_INPUT:
                        if (!isCorrect) {//绘制错误 重新绘制
                            curMode = FIRST_INPUT;
                            tvTip.setText("请开始绘制");
                        } else if (isCorrect) { //两次录一致成功 关闭
                            GestureDialog.this.dismiss();
                        }
                        break;
                    case DO_UNLOCK:
                        if (!isCorrect) {//密码重新绘制
                            tvTip.setText("请开始绘制");
                        } else if (isCorrect) { //密码正确
                            activity.unLockLocal();
                            SharedPreferenceUtils.setStringSP(SP_KEY, "");//清空密码
                            GestureDialog.this.dismiss();
                        }
                        break;

                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}




