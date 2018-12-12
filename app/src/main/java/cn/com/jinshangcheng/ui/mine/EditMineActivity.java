package cn.com.jinshangcheng.ui.mine;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.UserBean;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.utils.CommonUtils;
import cn.com.jinshangcheng.utils.GlideUtils;
import cn.com.jinshangcheng.utils.PictureSelectorUtils;
import cn.com.jinshangcheng.widget.CityWheelSelectPopupWindow;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 编辑个人信息
 */
public class EditMineActivity extends BaseActivity {

    @BindView(R.id.root)
    LinearLayout root;
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
    TextView etPhone;
    @BindView(R.id.bt_confirm)
    Button btConfirm;


    private String selectImgPath = "";//选择的图片路径
    private int sex;//0男  1女
    private String city = "";//市
    private String province = "";//省
    private CityWheelSelectPopupWindow popupWindow;
    private UserBean userBean;
    public static final int RESULT_CODE = 0x6868;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_edit_mine;
    }

    @Override
    public void initData() {
        userBean = MyApplication.getUserBean();
        if (!TextUtils.isEmpty(userBean.province) && !TextUtils.isEmpty(userBean.city)) {
            city = userBean.province + " " + userBean.city;
        }
        sex = userBean.sex;
    }

    @Override
    public void initView() {
        GlideUtils.loadHeadImage(getApplicationContext(), userBean.apppic, ivHeadImg, true);
        etName.setText(userBean.name);
        etNickName.setText(userBean.weixinname);
        etPhone.setText(userBean.phonenumber);
        tvCity.setText(city);
        rbFemale.setChecked(userBean.sex == 1);
        rbMale.setChecked(userBean.sex == 0);

        initBottonDialog();
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_male) {
                    sex = 0;
                } else if (checkedId == R.id.rb_female) {
                    sex = 1;
                }
            }
        });
    }

    /**
     * 初始化底部位置选择dialog
     */
    private void initBottonDialog() {
        //初始化选择位置dialog
        popupWindow = new CityWheelSelectPopupWindow(this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Logger.w("Address" + popupWindow.address);
                String[] array = popupWindow.address.split(" ");
                if (array.length > 2) {
                    province = array[0];
                    city = array[1];
                    tvCity.setText(province + " " + city);
                }
                popupWindow.dismiss();
            }
        });
//        if (TextUtils.isEmpty(addressName)) {
//            addressName = tvAddress.getText().toString().trim();
//            popupWindow.wheelHelper.setDefaultAddress(addressName);
//
//        } else {
//            popupWindow.wheelHelper.setDefaultAddress(addressName);
//        }

    }

    @OnClick({R.id.iv_headImg, R.id.tv_city, R.id.bt_confirm, R.id.tv_changePhone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_headImg:
                PictureSelectorUtils.getPictures(EditMineActivity.this, 1, true, false);
                break;
            case R.id.tv_changePhone:

                break;
            case R.id.tv_city:
                CommonUtils.hideSoftKeyboard(EditMineActivity.this);
                int navigationBarHeight = CommonUtils.getNavigationBarHeight(EditMineActivity.this);
                popupWindow.showAtLocation(root, Gravity.BOTTOM, 0, navigationBarHeight);
                break;
            case R.id.bt_confirm:
                if (TextUtils.isEmpty(etName.getText().toString())) {
                    showToast("请输入姓名");
                    return;
                }
                if (TextUtils.isEmpty(etPhone.getText().toString())) {
                    showToast("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(selectImgPath)) {
                    upLoadInfo();//只需要保存用户信息
                } else {
                    upLoadInfoAndImg();//同时保存头像和用户信息
                }

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
                GlideUtils.loadHeadImage(getApplicationContext(), selectImgPath, ivHeadImg, false);
            }
        }
    }

    @Override
    protected void onDestroy() {
        CommonUtils.hideSoftKeyboard(this);
        super.onDestroy();
    }

    public void uploadHead() {
        if (selectImgPath == null) {
            return;
        }
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), new File(selectImgPath));
        //构建body
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("userid", MyApplication.getUserId())
                .addFormDataPart("file", "file", requestFile)
                .build();

        RetrofitService.getRetrofit().uploadHeadImg(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
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

    public void upLoadInfo() {
        showLoading();
        RetrofitService.getRetrofit().updateUserInfo(MyApplication.getUserId(), etName.getText().toString(), etPhone.getText().toString()
                , province, city, sex, etNickName.getText().toString())
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
                            setResult(RESULT_CODE);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        dismissLoading();
                        showToast("保存失败，请重试");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void upLoadInfoAndImg() {// 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), new File(selectImgPath));
        //构建body
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("userid", MyApplication.getUserId())
                .addFormDataPart("file", "file", requestFile)
                .build();

        Observable<BaseBean> headImgObservable = RetrofitService.getRetrofit().uploadHeadImg(requestBody);
        Observable<BaseBean> userInfoObservable = RetrofitService.getRetrofit().updateUserInfo(MyApplication.getUserId(), etName.getText().toString(), etPhone.getText().toString()
                , province, city, sex, etNickName.getText().toString());
        showLoading();
        Observable.zip(headImgObservable, userInfoObservable, new BiFunction<BaseBean, BaseBean, Boolean>() {
            @Override
            public Boolean apply(BaseBean baseBeanHead, BaseBean baseBeanUserInfo) throws Exception {

                return "0".equals(baseBeanHead.code) && "0".equals(baseBeanUserInfo.code);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        dismissLoading();
                        showToast("保存成功");
                        if (aBoolean) {
                            setResult(RESULT_CODE);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        dismissLoading();
                        showToast("保存失败，请重试");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}

