package cn.com.jinshangcheng.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.Car;
import cn.com.jinshangcheng.listener.OnItemViewClickListener;

/**
 * 车辆管理Adapter
 */
public class CarManageAdapter extends SwipeMenuAdapter<CarManageAdapter.DefaultViewHolder> {
    BaseActivity mContext;
    List<Car> list;

    private OnItemViewClickListener mOnItemClickListener;

    /**
     * 刷新List
     *
     * @param list
     */
    public void refreshList(List<Car> list) {
        this.list = list;
        this.notifyDataSetChanged();

    }

    public void setOnItemClickListener(OnItemViewClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public CarManageAdapter(BaseActivity mContext, List<Car> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car_manage, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }


    @Override
    public void onBindViewHolder(DefaultViewHolder holder, int position) {
        holder.setData(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_car)
        ImageView ivCar;
        @BindView(R.id.tv_plate)
        TextView tvPlate;
        @BindView(R.id.tv_model)
        TextView tvModel;
        @BindView(R.id.tv_typeName)
        TextView tvTypeName;
        @BindView(R.id.tv_edit)
        TextView tvEdit;
        @BindView(R.id.tv_bind)
        TextView tvBind;
        @BindView(R.id.ll_notBind)
        LinearLayout llNotBind;
        @BindView(R.id.tv_unbind)
        TextView tvUnbind;
        @BindView(R.id.ll_hasBind)
        LinearLayout llHasBind;
        @BindView(R.id.tv_stealth)
        TextView tvStealth;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        public void setData(Car bean, int position) {
                llHasBind.setVisibility(position % 2 == 0 ? View.VISIBLE : View.INVISIBLE);
                llNotBind.setVisibility(position % 2 == 0 ? View.INVISIBLE : View.VISIBLE);
                tvBind.setOnClickListener(this);
                tvStealth.setOnClickListener(this);
                tvUnbind.setOnClickListener(this);
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
