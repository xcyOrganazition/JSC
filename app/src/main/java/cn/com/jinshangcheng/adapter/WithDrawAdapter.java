package cn.com.jinshangcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.bean.WithdrawBean;

public class WithDrawAdapter extends RecyclerView.Adapter<WithDrawAdapter.Holder> {
    Context context;

    List<WithdrawBean> list;


    public WithDrawAdapter(Context context, List<WithdrawBean> list) {
        this.context = context;
        this.list = list;
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
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_text)
        TextView ivText;


        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

        public void setData(WithdrawBean withdrawBean, int position) {
            String test = "2018-10-21：申请提现888元，提现成功。";
            SpannableStringBuilder builder = new SpannableStringBuilder(test);
            ForegroundColorSpan span = new ForegroundColorSpan(context.getResources().getColor(R.color.textGreen));
            builder.setSpan(span, (test.lastIndexOf("，")+1), test.lastIndexOf("。"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ivText.setText(builder);


        }
    }
}
