package cn.com.jinshangcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.bean.TravelBean;
import cn.com.jinshangcheng.listener.OnItemViewClickListener;
import cn.com.jinshangcheng.utils.DateUtils;
import cn.com.jinshangcheng.utils.MapUtils;

public class LocusAdapter extends SwipeMenuAdapter<LocusAdapter.Holder> {

    private List<TravelBean> locusList;
    private Context context;
    private OnItemViewClickListener onItemViewClickListener;

    public LocusAdapter(List<TravelBean> locusList, Context context) {
        this.locusList = locusList;
        this.context = context;
    }

    public void refreshList(List<TravelBean> locusList) {
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

    public void setOnItemViewClickListener(OnItemViewClickListener onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tv_locusName)
        TextView tvLocusName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_startAddress)
        TextView tvStartAddress;
        @BindView(R.id.tv_endAddress)
        TextView tvEndAddress;
        OnGetGeoCoderResultListener startAddressListener = new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有找到检索结果
                    tvStartAddress.setText("未知");
                } else {//获取反向地理编码结果
                    tvStartAddress.setText(result.getAddress());
                }
            }
        };
        OnGetGeoCoderResultListener endAddressListener = new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有找到检索结果
                    tvEndAddress.setText("未知");
                } else {//获取反向地理编码结果
                    tvEndAddress.setText(result.getAddress());
                }
            }
        };


        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setData(TravelBean bean) {
            String startTime = DateUtils.getMMddHMTime(bean.startTime);
            String stopTime = DateUtils.getMMddHMTime(bean.stopTime);
            tvTime.setText(startTime + " - " + stopTime);
            MapUtils.getAddress(new LatLng(bean.startLatitude, bean.startLongitude), startAddressListener);
            MapUtils.getAddress(new LatLng(bean.stopLatitude, bean.stopLongitude), endAddressListener);
        }

        @Override
        public void onClick(View v) {
            if (null != onItemViewClickListener) {
                onItemViewClickListener.onViewClick(getAdapterPosition(),v);
            }
        }
    }
}
