package cn.com.jinshangcheng.ui.car;

import android.widget.TextView;

import butterknife.BindView;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.mycst.CheckDataBean;
import cn.com.jinshangcheng.utils.NumberUtils;

public class CheckDetailActivity extends BaseActivity {

    @BindView(R.id.batteryvoltage)
    TextView batteryvoltage;
    @BindView(R.id.malfunctionnum)
    TextView malfunctionnum;
    @BindView(R.id.troublemileage)
    TextView troublemileage;
    @BindView(R.id.perresidualfue)
    TextView perresidualfue;
    @BindView(R.id.rpm)
    TextView rpm;
    @BindView(R.id.speed)
    TextView speed;
    @BindView(R.id.onflowct)
    TextView onflowct;
    @BindView(R.id.coolantct)
    TextView coolantct;
    @BindView(R.id.environmentct)
    TextView environmentct;
    @BindView(R.id.airpressure)
    TextView airpressure;
    @BindView(R.id.fuelpressure)
    TextView fuelpressure;
    @BindView(R.id.airflow)
    TextView airflow;
    @BindView(R.id.tvp)
    TextView tvp;
    @BindView(R.id.pedalposition)
    TextView pedalposition;
    @BindView(R.id.engineRuntime)
    TextView engineRuntime;
    @BindView(R.id.enginePayload)
    TextView enginePayload;
    @BindView(R.id.lfueltrim)
    TextView lfueltrim;
    @BindView(R.id.ciaa)
    TextView ciaa;

    private CheckDataBean checkData;//检测数据


    @Override
    public int setContentViewResource() {
        return R.layout.activity_check_detail;
    }

    @Override
    public void initData() {

        checkData = getIntent().getParcelableExtra("checkedBean");

    }

    @Override
    public void initView() {
        if (checkData == null) {
            return;
        }

        batteryvoltage.setText(NumberUtils.formatCheckData(checkData.batteryvoltage));
        malfunctionnum.setText(NumberUtils.formatCheckData(checkData.malfunctionnum));
        troublemileage.setText(NumberUtils.formatCheckData(checkData.troublemileage));
        perresidualfue.setText(NumberUtils.formatCheckData(checkData.perresidualfue));
        rpm.setText(NumberUtils.formatCheckData(checkData.rpm));
        speed.setText(NumberUtils.formatCheckData(checkData.speed));
        onflowct.setText(NumberUtils.formatCheckData(checkData.onflowct));
        coolantct.setText(NumberUtils.formatCheckData(checkData.coolantct));
        environmentct.setText(NumberUtils.formatCheckData(checkData.environmentct));
        airpressure.setText(NumberUtils.formatCheckData(checkData.airpressure));
        fuelpressure.setText(NumberUtils.formatCheckData(checkData.fuelpressure));
        airflow.setText(NumberUtils.formatCheckData(checkData.airflow));
        tvp.setText(NumberUtils.formatCheckData(checkData.tvp));
        pedalposition.setText(NumberUtils.formatCheckData(checkData.pedalposition));
        engineRuntime.setText(NumberUtils.formatCheckData(checkData.engineruntime));
        enginePayload.setText(NumberUtils.formatCheckData(checkData.enginepayload));
        lfueltrim.setText(NumberUtils.formatCheckData(checkData.lfueltrim));
        ciaa.setText(NumberUtils.formatCheckData(checkData.ciaa));

    }


}
