package cn.com.jinshangcheng.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseFragment;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.UserBean;
import cn.com.jinshangcheng.bean.VersionBean;
import cn.com.jinshangcheng.config.ConstParams;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.utils.GlideUtils;
import cn.com.jinshangcheng.widget.UpDateDialog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * 我的Fragment
 */
public class MineFragment extends BaseFragment {


    private static MineFragment mineFragment;
    @BindView(R.id.iv_headImg)
    ImageView ivHeadImg;
    @BindView(R.id.iv_redPoint)
    ImageView ivRedPoint;
    @BindView(R.id.tv_userName)
    TextView tvUserName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_level)
    TextView tvLevel;

    private boolean needUpdate;//是否需要更新
    private boolean forceUpdate;//是否强制更新

    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment getInstance() {
        if (mineFragment == null) {
            synchronized (MineFragment.class) {
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                }
            }
        }
        return mineFragment;
    }


    @Override
    public View createView(LayoutInflater inflater) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_mine, null, false);
    }


    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        refreshUserData();
        checkNewVersion();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }


    @Override
    public void showLoading() {

    }


    @OnClick(R.id.tv_people)
    public void onViewClicked() {
    }

    public void getUserInfo() {
        RetrofitService.getRetrofit().getUserInfo(MyApplication.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<UserBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<UserBean> baseBean) {
                        if (baseBean != null && baseBean.data != null) {
                            MyApplication.setUserBean(baseBean.data);
                            refreshUserData();
                        }
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

    private void checkNewVersion() {
        RetrofitService.getRetrofit().checkNewVersion(MyApplication.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<VersionBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<VersionBean> baseBean) {
                        if ("0".equals(baseBean.code) && null != baseBean.data) {
                            String newVersionString = baseBean.data.appversion;
                            checkNeedUpdate(newVersionString.split("\\."));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void checkNeedUpdate(String[] newVersionStrs) {
        String appVersion = VersionUtils.getVerName(getActivity());
        String[] curVersionStrs = appVersion.split("\\.");
        if (Integer.parseInt(newVersionStrs[0]) > Integer.parseInt(curVersionStrs[0])) {
            forceUpdate = true;
            needUpdate = true;
        } else if (Integer.parseInt(newVersionStrs[1]) > Integer.parseInt(curVersionStrs[1])) {
            forceUpdate = true;
            needUpdate = true;
        } else if (Integer.parseInt(newVersionStrs[2]) > Integer.parseInt(curVersionStrs[2])) {
            forceUpdate = false;
            needUpdate = true;
        } else {
            forceUpdate = false;
            needUpdate = false;
        }

        if (needUpdate) {
            ivRedPoint.setVisibility(View.VISIBLE);
        }
        if (forceUpdate) {
            UpDateDialog upDateDialog = new UpDateDialog();
            Bundle bundle = new Bundle();
            bundle.putString("updateContent", "优化了部分功能。");
            bundle.putBoolean("forceUpdate", forceUpdate);
            upDateDialog.setArguments(bundle);
            upDateDialog.show(getActivity().getFragmentManager(), "updateDialog");
        }

    }

    public void refreshUserData() {
        UserBean userBean = MyApplication.getUserBean();
        tvUserName.setText(String.format("%s%s", getResources().getString(R.string.userName), TextUtils.isEmpty(userBean.name) ? "" : userBean.name));
//        tvWxName.setText(String.format("%s%s", getResources().getString(R.string.wxName), userBean.weixinname));
        tvPhone.setText(String.format("%s%s", getResources().getString(R.string.phoneNum), userBean.phonenumber));
        tvLevel.setText(String.format("%s%s", getResources().getString(R.string.level), getRankText(userBean.userlevel)));
        String imgUrl = userBean.apppic;
        if (!TextUtils.isEmpty(imgUrl) && imgUrl.contains("\\")) {
            imgUrl = imgUrl.replace("\\", "/");
        }
        GlideUtils.loadHeadImage(getHoldingActivity(), imgUrl, ivHeadImg, true);
    }

    //会员等级
    public String getRankText(int cellvalue) {
        if (cellvalue == 0) {
            return "会员";
        } else if (cellvalue == 1) {
            return "经销商";
        } else if (cellvalue == 2) {
            return "区县代理";
        } else if (cellvalue == 3) {
            return "金牌区县代理";
        } else if (cellvalue == 4) {
            return "市级代理";
        } else if (cellvalue == 5) {
            return "金牌市级代理";
        } else if (cellvalue == 6) {
            return "省级代理";
        } else if (cellvalue == 7) {
            return "金牌省级代理";
        } else {
            return "暂无";
        }
    }

    @OnClick({R.id.iv_headImg, R.id.tv_people, R.id.tv_money, R.id.tv_privacy, R.id.tv_address,
            R.id.tv_car, R.id.tv_notification, R.id.tv_order, R.id.tv_card, R.id.tv_about_us, R.id.tv_callCustomerService})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_headImg:
                break;
            case R.id.tv_people:
                intent = new Intent(getHoldingActivity(), PeopleActivity.class);
                break;
            case R.id.tv_money:
                intent = new Intent(getHoldingActivity(), MoneyActivity.class);
                break;
            case R.id.tv_privacy:
                intent = new Intent(getHoldingActivity(), PrivacyActivity.class);
                break;
            case R.id.tv_address:
                intent = new Intent(getHoldingActivity(), AddressManageActivity.class);
                break;
            case R.id.tv_car:
                intent = new Intent(getHoldingActivity(), CarManageActivity.class);
                startActivityForResult(intent, 0x888);
                return;
            case R.id.tv_notification:
                intent = new Intent(getHoldingActivity(), NotificationManageActivity.class);
                break;
            case R.id.tv_order:
                intent = new Intent(getHoldingActivity(), MyOrderActivity.class);
                break;
            case R.id.tv_card:
                intent = new Intent(getHoldingActivity(), BankCardActivity.class);
                break;
            case R.id.tv_about_us:
                intent = new Intent(getHoldingActivity(), AboutUsActivity.class);
                break;
            case R.id.tv_callCustomerService://联系客服
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ConstParams.CUSTOMER_SERVICE));
                break;
        }
        if (intent != null) {
            getActivity().startActivity(intent);
        }
    }


    public void goToEditMine() {
        Intent intent = new Intent(getActivity(), EditMineActivity.class);
        startActivityForResult(intent, 0x110);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == EditMineActivity.RESULT_CODE) {
//            Logger.e("MineFragment收到了EditMineActivity");
            getUserInfo();
        }
    }
}
