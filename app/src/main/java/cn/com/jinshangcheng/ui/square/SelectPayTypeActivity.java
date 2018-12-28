package cn.com.jinshangcheng.ui.square;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.orhanobut.logger.Logger;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.PayResult;
import cn.com.jinshangcheng.config.ConstParams;

public class SelectPayTypeActivity extends BaseActivity {

    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.cb_aliPay)
    CheckBox cbAliPay;
    @BindView(R.id.cb_wechatPay)
    CheckBox cbWechatPay;

    protected static final int SDK_PAY_FLAG = 66;
    private String orderInfo;
    private String total;
    private String des;


    @Override
    public int setContentViewResource() {
        return R.layout.activity_select_pay_type;
    }

    @Override
    public void initData() {
        //todo 沙箱调试
        if (ConstParams.DEBUG) {
            EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        }
        orderInfo = getIntent().getStringExtra("key");
        des = getIntent().getStringExtra("des");
        total = getIntent().getStringExtra("total");

    }

    @Override
    public void initView() {
        tvDes.setText(des);
        tvPrice.setText(String.format("¥ %s", total));
        cbAliPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbWechatPay.setChecked(false);
                }
            }
        });
        cbWechatPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbAliPay.setChecked(false);
                }
            }
        });

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
                        showToast("支付成功");
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
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

    public void goAliPay() {
        /*
         * 为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */
//        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
//        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

//        String privateKey = RSA2_PRIVATE;
//        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
//        final String orderInfo = orderParam + "&" + sign;
        Logger.w("orderInfo =" + orderInfo);
        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(SelectPayTypeActivity.this);
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
    }


    @OnClick(R.id.bt_confirm)
    public void onViewClicked() {
        if (cbAliPay.isChecked()) {
            goAliPay();
        } else if (cbWechatPay.isChecked()) {

        }

    }
}
