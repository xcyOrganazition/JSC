package com.jinshangcheng.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinshangcheng.R;
import com.jinshangcheng.base.BaseActivity;
import com.jinshangcheng.bean.AddressBean;
import com.jinshangcheng.listener.OnItemViewClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 地址管理Adapter
 */
public class AddressAdapter extends SwipeMenuAdapter<AddressAdapter.DefaultViewHolder> {
    BaseActivity mContext;
    List<AddressBean> list;

    private OnItemViewClickListener mOnItemClickListener;

    /**
     * 刷新List
     *
     * @param list
     */
    public void refreshList(List<AddressBean> list) {
        this.list = list;
        this.notifyDataSetChanged();

    }

    public void setOnItemClickListener(OnItemViewClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public AddressAdapter(BaseActivity mContext, List<AddressBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
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

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_edit)
        TextView tvEdit;
        @BindView(R.id.tv_address)
        TextView tvAddress;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setData(AddressBean bean, Context mContext) {
            tvAddress.setText(bean.address);
            tvName.setText(bean.name);
            tvPhone.setText(bean.phone);
            tvEdit.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onViewClick(getAdapterPosition(), view);
            }
        }
    }
}
