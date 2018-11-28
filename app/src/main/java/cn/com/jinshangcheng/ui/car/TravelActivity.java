package cn.com.jinshangcheng.ui.car;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.TravelBean;
import cn.com.jinshangcheng.bean.TravelPointBean;
import cn.com.jinshangcheng.net.RetrofitService;
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

    public void drawTravel(ArrayList<TravelPointBean> travelPointList) {
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.ic_red_point);//轨迹点
        List<OverlayOptions> options = new ArrayList<OverlayOptions>();//要绘制的百度坐标点集合
        for (TravelPointBean pointBean : travelPointList) {
            OverlayOptions option = new MarkerOptions()
                    .position(new LatLng(pointBean.latitude, pointBean.longitude))
                    .icon(bitmap);
            options.add(option);
        }
        bdMapView.getMap().addOverlays(options);
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
