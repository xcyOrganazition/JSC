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
import cn.com.jinshangcheng.bean.Goods;
import cn.com.jinshangcheng.listener.OnItemViewClickListener;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.utils.GlideUtils;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {

    private List<Goods> list;
    private Context context;
    private OnItemViewClickListener onViewItemClickListener;

    public void setOnItemViewClickListener(OnItemViewClickListener onViewItemClickListener) {
        this.onViewItemClickListener = onViewItemClickListener;
    }

    public GoodsAdapter(List<Goods> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void refreshList(List<Goods> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GoodsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods, parent, false);
        return new GoodsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsAdapter.ViewHolder holder, int position) {

        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_goodsImg)
        ImageView ivGoodsImg;
        @BindView(R.id.iv_goodsName)
        TextView ivGoodsName;
        @BindView(R.id.tv_goodsMoney)
        TextView tvGoodsMoney;
        @BindView(R.id.tv_addGoods)
        TextView tvAddGoods;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            tvAddGoods.setOnClickListener(this);

        }

        public void setData(Goods goods) {
            GlideUtils.loadImage(context, RetrofitService.HOST + goods.getImagepath(), ivGoodsImg);
            ivGoodsName.setText(goods.getGoodsname());
            tvGoodsMoney.setText(String.format("¥ %s元", goods.getPrice()));
        }

        @Override
        public void onClick(View v) {
            if (onViewItemClickListener != null) {
                onViewItemClickListener.onViewClick(getAdapterPosition(), v);
            }
        }
    }
}
