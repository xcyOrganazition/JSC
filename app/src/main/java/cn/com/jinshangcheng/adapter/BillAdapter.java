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
import cn.com.jinshangcheng.utils.ArrayUtils;
import cn.com.jinshangcheng.utils.DateUtils;

/**
 * 账单变更详情
 */
public class BillAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int emptyViewHeight;
    Context context;

    List<WithdrawBean> list;

    private int viewTypeEmpty = 1;
    private int viewTypeHasContent = 2;


    public BillAdapter(Context context, List<WithdrawBean> list, int emptyViewHeight) {
        this.context = context;
        this.list = list;
        this.emptyViewHeight = emptyViewHeight;

    }

    public void refreshListData(List<WithdrawBean> list, int emptyViewHeight) {
        this.list = list;
        this.emptyViewHeight = emptyViewHeight;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == viewTypeEmpty) {
            View emptyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty_view, parent, false);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    emptyViewHeight);
            emptyView.setLayoutParams(lp);
            return new RecyclerView.ViewHolder(emptyView) {
            };
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_with_draw, parent, false);

            return new Holder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Holder) {
            Holder myHolder = (Holder) holder;
            myHolder.setData(list.get(position), position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (ArrayUtils.hasContent(list)) {
            return viewTypeHasContent;
        } else {
            return viewTypeEmpty;
        }
    }


    @Override
    public int getItemCount() {
        if (ArrayUtils.hasContent(list)) {
            return list.size();
        } else {
            return 1;
        }
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_text)
        TextView ivText;


        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

        public void setData(WithdrawBean bean, int position) {
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
