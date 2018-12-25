package cn.com.jinshangcheng.ui.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.orhanobut.logger.Logger;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseFragment;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.PayResult;
import cn.com.jinshangcheng.bean.UserBean;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.utils.GlideUtils;
import cn.com.jinshangcheng.utils.OrderInfoUtil2_0;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static cn.com.jinshangcheng.config.ConstParams.APPID;
import static cn.com.jinshangcheng.config.ConstParams.RSA2_PRIVATE;


/**
 * 我的Fragment
 */
public class MineFragment extends BaseFragment {


    private static MineFragment mineFragment;
    @BindView(R.id.iv_headImg)
    ImageView ivHeadImg;
    @BindView(R.id.tv_userName)
    TextView tvUserName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    protected static final int SDK_PAY_FLAG = 66;

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
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
    }

    @Override
    public void initView() {
        refreshUserData();
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
        tvUserName.setText(String.format("%s%s", getResources().getString(R.string.userName), TextUtils.isEmpty(userBean.name) ? "" : userBean.name));
//        tvWxName.setText(String.format("%s%s", getResources().getString(R.string.wxName), userBean.weixinname));
        tvPhone.setText(String.format("%s%s", getResources().getString(R.string.phoneNum), userBean.phonenumber));
        tvLevel.setText(String.format("%s%s", getResources().getString(R.string.level), getRankText(userBean.userlevel)));
        GlideUtils.loadHeadImage(getHoldingActivity(), userBean.apppic, ivHeadImg, true);
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
                startActivityForResult(intent, 0x888);
                return;
            case R.id.tv_order:
                intent = new Intent(getHoldingActivity(), MyOrderActivity.class);
                break;
            case R.id.tv_card:
                intent = new Intent(getHoldingActivity(), BankCardActivity.class);
                break;
            case R.id.tv_about_us:
//                intent = new Intent(getHoldingActivity(), AboutUsActivity.class);
                /*
                 * 为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
                 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
                 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
                 *
                 * orderInfo 的获取必须来自服务端；
                 */
                boolean rsa2 = (RSA2_PRIVATE.length() > 0);
                Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
                String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

                String privateKey = RSA2_PRIVATE;
                String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
                final String orderInfo = orderParam + "&" + sign;
                Logger.w("orderInfo =" + orderInfo);
                final Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(getHoldingActivity());
                        Map<String, String> result = alipay.payV2(orderInfo, true);


                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();

                break;
        }
        if (intent != null) {
            getActivity().startActivity(intent);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Logger.w("payResult" + payResult.toString());
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Log.e("支付失败", "payResult");
                        Logger.w("支付失败" + payResult.toString());
                    }
                    break;
                }

                default:
                    break;
            }
        }

        ;
    };


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
