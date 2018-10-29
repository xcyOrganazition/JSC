package cn.com.jinshangcheng.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.BankCardBean;
import cn.com.jinshangcheng.listener.OnItemViewClickListener;

public class BankCardAdapter extends SwipeMenuAdapter<BankCardAdapter.DefaultViewHolder> {
    BaseActivity mContext;
    List<BankCardBean> list;

    private OnItemViewClickListener mOnItemClickListener;

    /**
     * 刷新List
     *
     * @param list
     */
    public void refreshList(List<BankCardBean> list) {
        this.list = list;
        this.notifyDataSetChanged();

    }

    public void setOnItemClickListener(OnItemViewClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public BankCardAdapter(BaseActivity mContext, List<BankCardBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bankcard, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }


    @Override
    public void onBindViewHolder(DefaultViewHolder holder, int position) {
        holder.setData(list.get(position), mContext);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_bankName)
        TextView tvBankName;
        @BindView(R.id.tv_cardNum)
        TextView tvCardNum;
        @BindView(R.id.tv_bankAddress)
        TextView tvBankAddress;


        public DefaultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setData(BankCardBean bean, Context mContext) {
//            tvBankAddress.setText(bean.accountbank);
//            tvBankName.setText(bean.accountbank);
//            tvCardNum.setText(bean.accountnum);
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onViewClick(getAdapterPosition(), view);
            }
        }
    }
}
