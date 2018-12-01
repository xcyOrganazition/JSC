package cn.com.jinshangcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.config.ConstParams;
import cn.com.jinshangcheng.listener.OnItemViewClickListener;

/**
 * 车牌首文字Adapter
 */
public class LicenseAdapter extends RecyclerView.Adapter<LicenseAdapter.Holder> {

    private String[] array = ConstParams.cityArray;
    private Context context;
    private OnItemViewClickListener listener;

    public LicenseAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Holder h = new Holder(LayoutInflater.from(context).inflate(R.layout.item_license,null,false));
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.tvCityName.setText(array[position]);
    }

    @Override
    public int getItemCount() {
        return array.length;
    }

    public void setListener(OnItemViewClickListener listener) {
        this.listener = listener;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvCityName;

        public Holder(View itemView) {
            super(itemView);
            tvCityName = itemView.findViewById(R.id.tv_cityName);
            tvCityName.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onViewClick(getAdapterPosition(),v);
            }
        }
    }
}
