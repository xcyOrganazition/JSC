package cn.com.jinshangcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.bean.ViolationBean;

public class ViolationAdapter extends RecyclerView.Adapter<ViolationAdapter.ViolationHolder> {


    private List<ViolationBean.ViolationDetail> violationDetailList;
    private Context context;


    public ViolationAdapter(List<ViolationBean.ViolationDetail> violationDetailList, Context context) {
        this.violationDetailList = violationDetailList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViolationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_voilation, parent, false);
        return new ViolationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViolationHolder holder, int position) {
        holder.setData(violationDetailList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return violationDetailList.size();
    }

    class ViolationHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_handle)
        ImageView ivHandle;
        @BindView(R.id.tv_handle)
        TextView tvHandle;
        @BindView(R.id.tv_point)
        TextView tvPoint;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_des)
        TextView tvDes;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_position)
        TextView tvPosition;

        public ViolationHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(ViolationBean.ViolationDetail bean, int position) {
//            1处理  0未处理
            ivHandle.setImageResource("1".equals(bean.getHandled()) ? R.mipmap.ic_handed : R.mipmap.ic_unhandle);
            tvHandle.setText("1".equals(bean.getHandled()) ? "已处理" : "未处理");
            tvPoint.setText(String.format("扣分：%s", bean.getFen()));
            tvMoney.setText(String.format("罚款：%s", bean.getMoney()));
            tvDes.setText(bean.getAct());
            tvTime.setText(String.format("违章时间：%s", bean.getDate()));
            tvPosition.setText(String.format("违章地点：%s", bean.getArea()));
        }
    }
}
