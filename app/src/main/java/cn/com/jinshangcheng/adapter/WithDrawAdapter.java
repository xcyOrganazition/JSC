package cn.com.jinshangcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.bean.WithdrawBean;
import cn.com.jinshangcheng.utils.DateUtils;

public class WithDrawAdapter extends RecyclerView.Adapter<WithDrawAdapter.Holder> {
    Context context;

    List<WithdrawBean> list;


    public WithDrawAdapter(Context context, List<WithdrawBean> list) {
        this.context = context;
        this.list = list;
    }

    public void refreshAdapter(List<WithdrawBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_with_draw, parent, false);

        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.setData(list.get(position), position);
    }

    @Override
    public int getItemCount() {
//        return 10;
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_text)
        TextView ivText;


        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(WithdrawBean bean, int position) {
//            String test = "2018-10-21：申请提现888元，提现成功。";
//            SpannableStringBuilder builder = new SpannableStringBuilder(test);
//            ForegroundColorSpan span = new ForegroundColorSpan(context.getResources().getColor(R.color.textGreen));
//            builder.setSpan(span, (test.lastIndexOf("，") + 1), test.lastIndexOf("。"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ivText.setText(builder);

            String time = DateUtils.getYMDTime(bean.getRegisttime());
            double money = Math.abs(bean.getDealbalance());
            String type = "";
            switch (bean.getDealtype()) {// 0直推奖 1级差奖 2.已申请提现 3.已打款 4已退款
                case 0:
                    type = String.format("%s：直推返现%s元。", time, money);
                    break;
                case 1:
                    type = String.format("%s：级差奖%s元。", time, money);
                    break;
                case 2:
                    type = String.format("%s：申请提现%s元，待处理。", time, money);
                    break;
                case 3:
                    type = String.format("%s：申请提现%s元，已打款。", time, money);
                    break;
                case 4:
                    type = String.format("%s：退款%s元。", time, money);
                    break;
            }
            ivText.setText(type);

        }
    }
}
