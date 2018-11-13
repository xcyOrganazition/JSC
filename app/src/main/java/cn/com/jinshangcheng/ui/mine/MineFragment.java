package cn.com.jinshangcheng.ui.mine;

import android.content.Context;
import android.content.Intent;
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
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.utils.GlideUtils;
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
    @BindView(R.id.tv_wxName)
    TextView tvWxName;
    @BindView(R.id.tv_userName)
    TextView tvUserName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_level)
    TextView tvLevel;

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
        getUserInfo();
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

    public void refreshUserData() {
        UserBean userBean = MyApplication.getUserBean();
        tvUserName.setText(String.format("%s%s", getResources().getString(R.string.userName), userBean.name));
        tvWxName.setText(String.format("%s%s", getResources().getString(R.string.wxName), userBean.weixinname));
        tvPhone.setText(String.format("%s%s", getResources().getString(R.string.phoneNum), userBean.phonenumber));
        tvLevel.setText(String.format("%s%s", getResources().getString(R.string.level), userBean.userlevel));
        GlideUtils.loadImage(getHoldingActivity(), userBean.weixinpic, ivHeadImg);
    }

    @OnClick({R.id.iv_headImg, R.id.tv_people, R.id.tv_money, R.id.tv_privacy, R.id.tv_address, R.id.tv_car, R.id.tv_order, R.id.tv_card, R.id.tv_about_us})
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
        }
        if (intent != null) {
            getActivity().startActivity(intent);
        }
    }

}
