package cn.com.jinshangcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.bean.Goods;
import cn.com.jinshangcheng.bean.OrderBean;
import cn.com.jinshangcheng.utils.ArrayUtils;
import cn.com.jinshangcheng.utils.DateUtils;
import cn.com.jinshangcheng.utils.NumberUtils;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.Holder> {


    private List<OrderBean> list;
    private Context context;

    public MyOrderAdapter(List<OrderBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void refresData(List<OrderBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
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
        @BindView(R.id.tv_orderNumber)
        TextView tvOrderNumber;
        @BindView(R.id.tv_orderDate)
        TextView tvOrderDate;
        @BindView(R.id.ll_goodsList)
        LinearLayout llGoodsList;
        @BindView(R.id.tv_total)
        TextView tvTotal;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(OrderBean bean, int position) {
            tvOrderNumber.setText(bean.getOrderid());
            tvOrderDate.setText(DateUtils.getYMDTime(bean.getOrdertime()));
            tvTotal.setText(String.format("总计：%s元", NumberUtils.formatDouble(bean.getTotal())));
            llGoodsList.removeAllViews();
            if (ArrayUtils.hasContent(bean.getOrderitems())) {
                for (Goods curGoods : bean.getOrderitems()) {
                    final View itemView = View.inflate(context, R.layout.item_goods_detail, null);
                    TextView tvCurGoodsName = itemView.findViewById(R.id.tv_curGoodsName);
                    final TextView tvCurGoodPrice = itemView.findViewById(R.id.tv_curGoodPrice);
                    final TextView tvCurGoodsNum = itemView.findViewById(R.id.tv_curGoodsNum);
                    tvCurGoodsName.setText(curGoods.getGoodsname());
                    tvCurGoodsNum.setText(String.format("%s件", curGoods.quantity));
                    tvCurGoodPrice.setText(String.format("%s元", curGoods.getPrice()));
                    llGoodsList.addView(itemView);
                }
            }
        }
    }
}
