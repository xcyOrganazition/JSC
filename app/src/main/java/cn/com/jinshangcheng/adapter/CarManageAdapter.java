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
import cn.com.jinshangcheng.bean.CarBean;
import cn.com.jinshangcheng.listener.OnItemViewClickListener;
import cn.com.jinshangcheng.utils.GlideUtils;

/**
 * 车辆管理Adapter
 */
public class CarManageAdapter extends SwipeMenuAdapter<CarManageAdapter.DefaultViewHolder> {
    BaseActivity mContext;
    List<CarBean> list;

    private OnItemViewClickListener mOnItemClickListener;

    /**
     * 刷新List
     *
     * @param list
     */
    public void refreshList(List<CarBean> list) {
        this.list = list;
        this.notifyDataSetChanged();

    }

    public void setOnItemClickListener(OnItemViewClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public CarManageAdapter(BaseActivity mContext, List<CarBean> list) {
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
            tvBind.setOnClickListener(this);
            tvStealth.setOnClickListener(this);
            tvUnbind.setOnClickListener(this);
            tvEdit.setOnClickListener(this);
        }

        public void setData(CarBean bean, int position) {
            tvPlate.setText(bean.getPlatenumber());
            tvModel.setText(bean.getBrandname());
            tvTypeName.setText(bean.getTypename());
            GlideUtils.loadImage(mContext, bean.getTypepath(), ivCar);
            llHasBind.setVisibility(bean.getDin() != null ? View.VISIBLE : View.INVISIBLE);//已绑定 显示解绑按钮
            tvStealth.setVisibility(bean.getDin() != null ? View.VISIBLE : View.INVISIBLE);//已绑定 显示隐身管理
            llNotBind.setVisibility(bean.getDin() != null ? View.INVISIBLE : View.VISIBLE);//未绑定 显示绑定按钮

        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onViewClick(getAdapterPosition(), view);
            }
        }
    }
}
