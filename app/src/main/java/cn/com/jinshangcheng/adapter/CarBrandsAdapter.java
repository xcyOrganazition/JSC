package cn.com.jinshangcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.CarBrandsBean;
import cn.com.jinshangcheng.listener.OnItemViewClickListener;
import cn.com.jinshangcheng.utils.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 汽车品牌列表
 */
public class CarBrandsAdapter extends RecyclerView.Adapter<CarBrandsAdapter.ViewHolder> {

    private List<CarBrandsBean> list;
    private BaseActivity mContext;
    private OnItemViewClickListener onItemViewClickListener;


    public CarBrandsAdapter(List<CarBrandsBean> list,
                            BaseActivity mContext) {
        super();
        this.list = list;
        this.mContext = mContext;
    }

    public void setOnItemViewClickListener(OnItemViewClickListener onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }


    public void refreshList(List<CarBrandsBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_car_brand, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position > 0) {
            holder.setData(list.get(position), list.get(position - 1), mContext);
        } else {
            holder.setData(list.get(position), null, mContext);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_letter)
        TextView tvLetter;
        @BindView(R.id.iv_branch)
        ImageView ivBranch;
        @BindView(R.id.tv_branchName)
        TextView tvBranchName;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        private void setData(CarBrandsBean bean, CarBrandsBean lastBean, Context mContext) {
            tvBranchName.setText(bean.brandName);
            GlideUtils.loadImage(mContext, bean.picturePath, ivBranch);
            tvLetter.setText(bean.nameIndex);
            if (lastBean != null && lastBean.nameIndex.equals(bean.nameIndex)) {
                tvLetter.setVisibility(View.GONE);
            } else {
                tvLetter.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onClick(View view) {
            if (onItemViewClickListener != null) {
                onItemViewClickListener.onViewClick(getAdapterPosition(), view);
            }
        }
    }
}
