package cn.com.jinshangcheng.ui.mine;

import android.view.View;
import android.widget.Switch;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.DemoHelper;
import cn.com.jinshangcheng.DemoModel;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.widget.TittleBar;

/**
 * 通知管理
 */
public class NotificationManageActivity extends BaseActivity {

    @BindView(R.id.tittleBar)
    TittleBar tittleBar;
    @BindView(R.id.switch_sound)
    Switch soundSwitch;
    @BindView(R.id.switch_vibrate)
    Switch vibrateSwitch;
    private DemoModel settingsModel;
    private EMOptions chatOptions;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_notification_manage;
    }

    @Override
    public void initData() {
        settingsModel = DemoHelper.getInstance().getModel();
        chatOptions = EMClient.getInstance().getOptions();
    }

    @Override
    public void initView() {
        tittleBar.showNavigation();
        tittleBar.setTittle("通知管理");

        // sound notification is switched on or not?
        soundSwitch.setChecked(settingsModel.getSettingMsgSound());

        // vibrate notification is switched on or not?
        vibrateSwitch.setChecked(settingsModel.getSettingMsgVibrate());
    }

    @OnClick({R.id.switch_sound, R.id.switch_vibrate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.switch_sound:
                settingsModel.setSettingMsgSound(soundSwitch.isChecked());
                break;
            case R.id.switch_vibrate:
                settingsModel.setSettingMsgVibrate(vibrateSwitch.isChecked());
                break;
        }
    }
}
