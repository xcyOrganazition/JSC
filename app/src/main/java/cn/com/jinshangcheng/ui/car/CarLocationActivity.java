package cn.com.jinshangcheng.ui.car;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import butterknife.BindView;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;

public class CarLocationActivity extends BaseActivity {

    @BindView(R.id.mapView)
    MapView bdMapView;

    LatLng carPosition;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_car_loaction;
    }

    @Override
    public void initData() {
        carPosition = getIntent().getParcelableExtra("carPosition");
    }

    @Override
    public void initView() {
        bdMapView.getMap().setMapStatus(MapStatusUpdateFactory.newLatLng(carPosition));
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.main_position_select);//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(carPosition)
                .icon(bitmap);
        bdMapView.getMap().addOverlay(option);//在地图上添加Marker，并显示
        bdMapView.getMap().setMapStatus(MapStatusUpdateFactory.newLatLng(carPosition));
        bdMapView.getMap().setMapStatus(MapStatusUpdateFactory.zoomTo(18));

    }

}
