package cn.com.jinshangcheng.ui.car;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.TravelBean;
import cn.com.jinshangcheng.bean.TravelPointBean;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.utils.MapUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 轨迹详情
 */
public class TravelActivity extends BaseActivity {

    @BindView(R.id.bd_mapView)
    MapView bdMapView;

    private TravelBean travelBean;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_travel;
    }

    @Override
    public void initData() {
        travelBean = (TravelBean) getIntent().getSerializableExtra("travelBean");

    }

    @Override
    public void initView() {
        if (travelBean != null) {
            getPointList();
        }
    }

    /**
     * 绘制轨迹
     *
     * @param travelPointList
     */
    public void drawTravel(ArrayList<TravelPointBean> travelPointList) {
//        List<OverlayOptions> options = new ArrayList<OverlayOptions>();//要绘制的百度坐标点集合
        List<LatLng> points = new ArrayList<LatLng>();//要绘制的百度坐标点集合
        for (TravelPointBean pointBean : travelPointList) {
            LatLng latLng = MapUtils.changeMapTerm(new LatLng(pointBean.latitude, pointBean.longitude));//坐标转换
            points.add(latLng);
        }
        OverlayOptions ooPolyline = new PolylineOptions().width(10)
                .color(getResources().getColor(R.color.themeColor)).points(points);
        bdMapView.getMap().addOverlay(ooPolyline);
        //绘制起始、结束坐标点
        LatLng startPoint = MapUtils.changeMapTerm(new LatLng(travelPointList.get(0).latitude, travelPointList.get(0).longitude));
        LatLng endPoint = MapUtils.changeMapTerm(new LatLng(travelPointList.get(travelPointList.size() - 1).latitude,
                travelPointList.get(travelPointList.size() - 1).longitude));
        BitmapDescriptor startBitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_location);//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option1 = new MarkerOptions()
                .position(startPoint)
                .icon(startBitmap);
        OverlayOptions option2 = new MarkerOptions()
                .position(endPoint)
                .icon(startBitmap);
        bdMapView.getMap().addOverlay(option1);//在地图上绘制起始点
        bdMapView.getMap().addOverlay(option2);//在地图上绘制结束点
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(startPoint);
        builder.include(endPoint);
        LatLngBounds latLngBounds = builder.build();
        bdMapView.getMap().setMapStatus(MapStatusUpdateFactory.newLatLngBounds(latLngBounds));// 设置显示在屏幕中的地图地理范围
//        bdMapView.getMap().setMapStatus(MapStatusUpdateFactory.zoomTo(14));//设置地图缩放等级
    }


    public void getPointList() {
        showLoading();
        RetrofitService.getRetrofit().getTravelPointList(MyApplication.getCarId(), MyApplication.getUserId(),
                travelBean.startTime, travelBean.stopTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<TravelPointBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<TravelPointBean> travelPointList) {
                        drawTravel(travelPointList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                        showToast("请求失败，请重试");

                    }

                    @Override
                    public void onComplete() {
                        dismissLoading();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if (bdMapView != null) {
            bdMapView.onDestroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        if (bdMapView != null) {
            bdMapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        if (bdMapView != null) {
            bdMapView.onPause();
        }
    }


}
