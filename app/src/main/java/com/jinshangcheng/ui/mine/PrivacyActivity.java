package com.jinshangcheng.ui.mine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.jinshangcheng.R;
import com.jinshangcheng.base.BaseActivity;
import com.jinshangcheng.widget.GestureDialog;
import com.jinshangcheng.widget.TittleBar;
import com.orhanobut.logger.Logger;

import butterknife.BindView;

/**
 * 隐私管理
 */
public class PrivacyActivity extends BaseActivity {

    @BindView(R.id.tittleBar)
    TittleBar tittleBar;
    @BindView(R.id.switch_local)
    Switch switchLocal;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_pirvacy;
    }

    @Override
    public void initData() {

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
     * 关闭轨迹保护
     */
    public void unLockLocal() {
        Logger.w("unLock");
    }


}
