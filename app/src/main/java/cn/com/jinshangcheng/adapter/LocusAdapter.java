package cn.com.jinshangcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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

public class LocusAdapter extends SwipeMenuAdapter<LocusAdapter.Holder> {

    private List locusList;
    private Context context;

    public LocusAdapter(List locusList, Context context) {
        this.locusList = locusList;
        this.context = context;
    }

    public void refreshList(List locusList) {
        this.locusList = locusList;
        this.notifyDataSetChanged();
    }


    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_locus, parent, false);
    }

    @Override
    public Holder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new Holder(realContentView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.setData(locusList.get(position));

    }

    @Override
    public int getItemCount() {
        return locusList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_locusName)
        TextView tvLocusName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_startAddress)
        TextView tvStartAddress;
        @BindView(R.id.tv_endAddress)
        TextView tvEndAddress;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(Object o) {

        }
    }
}
