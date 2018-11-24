package cn.com.jinshangcheng.ui.mine;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.utils.GlideUtils;
import cn.com.jinshangcheng.utils.PictureSelectorUtils;

/**
 * 编辑个人信息
 */
public class EditMineActivity extends BaseActivity {

    @BindView(R.id.iv_headImg)
    ImageView ivHeadImg;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_nickName)
    EditText etNickName;
    @BindView(R.id.rb_male)
    RadioButton rbMale;
    @BindView(R.id.rb_female)
    RadioButton rbFemale;
    @BindView(R.id.rg_sex)
    RadioGroup rgSex;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.bt_confirm)
    Button btConfirm;


    private String selectImgPath;//选择的图片路径

    @Override
    public int setContentViewResource() {
        return R.layout.activity_edit_mine;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }


    @OnClick({R.id.iv_headImg, R.id.tv_city, R.id.bt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_headImg:
                PictureSelectorUtils.getPictures(EditMineActivity.this, 1, true, false);
                break;
            case R.id.tv_city:
                break;
            case R.id.bt_confirm:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (PictureConfig.CHOOSE_REQUEST == requestCode && RESULT_OK == resultCode) {
            // 图片选择结果回调
            List<LocalMedia> localMedias = PictureSelector.obtainMultipleResult(data);
            // 例如 LocalMedia 里面返回三种path
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
            // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
            for (LocalMedia localMedia : localMedias) {
                if (localMedia.isCompressed()) {
                    selectImgPath = localMedia.getCompressPath();
                } else {
                    selectImgPath = localMedia.getPath();
                }
                Logger.w("imagePath" + selectImgPath);
                GlideUtils.loadImage(getApplicationContext(), selectImgPath, ivHeadImg);
            }
        }
    }
}
