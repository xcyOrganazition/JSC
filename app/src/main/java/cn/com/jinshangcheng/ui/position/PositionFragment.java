package cn.com.jinshangcheng.ui.position;


import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.Unbinder;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseFragment;

/**
 * 位置模块
 */
public class PositionFragment extends BaseFragment {
    private static PositionFragment positionFragment;
    @BindView(R.id.mapView)
    MapView mapView;
    Unbinder unbinder;
    private LocationClient locationClient;

    public PositionFragment() {
        // Required empty public constructor
    }

    public static PositionFragment getInstance() {
        if (positionFragment == null) {
            synchronized (PositionFragment.class) {
                if (positionFragment == null) {
                    positionFragment = new PositionFragment();
                }
            }
        }
        return positionFragment;
    }


    @Override
    public View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_position, null, false);

    }

    @Override
    public void initData() {

        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        locationClient = new LocationClient(getActivity().getApplicationContext());
        //声明LocationClient类实例并配置定位参数
        LocationClientOption locationOption = new LocationClientOption();
        MyLocationListener myLocationListener = new MyLocationListener();
        //注册监听函数
        locationClient.registerLocationListener(myLocationListener);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("bd09ll");
        //可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(10000);//每10s更新一次定位信息
        //可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true);
        //可选，设置是否需要地址描述
        locationOption.setIsNeedLocationDescribe(true);
        //可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(false);
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.setLocationNotify(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        locationOption.setIgnoreKillProcess(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        locationOption.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        locationOption.setIsNeedLocationPoiList(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        locationOption.SetIgnoreCacheException(false);
        //可选，默认false，设置是否开启Gps定位
        locationOption.setOpenGps(true);
        //可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        locationOption.setIsNeedAltitude(false);
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        locationOption.setOpenAutoNotifyMode();
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
        locationOption.setOpenAutoNotifyMode(10000, 1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
        locationClient.setLocOption(locationOption);

    }

    @Override
    public void initView() {
        mapView.showScaleControl(false);//隐藏比例尺
        mapView.showZoomControls(false);//隐藏放大缩小按钮
        mapView.getMap().setMyLocationEnabled(true);
        //定位相关配置
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.ic_location);//标记图标
        int accuracyCircleFillColor = 0xFFFFFFFF;//自定义精度圈填充颜色
        int accuracyCircleStrokeColor = 0xFFFFFFFF;//自定义精度圈边框颜色
        mapView.getMap().setMyLocationConfiguration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING, true, mCurrentMarker,
                accuracyCircleFillColor, accuracyCircleStrokeColor));
//        LatLng point = new LatLng(lat, lng);//构建Marker坐标
//        BitmapDescriptor bitmap = BitmapDescriptorFactory
//                .fromResource(R.mipmap.ic_location);//构建MarkerOption，用于在地图上添加Marker
//        OverlayOptions option = new MarkerOptions()
//                .position(point)
//                .icon(bitmap);
//        //在地图上添加Marker，并显示
//        mapView.getMap().addOverlay(option);

        //解决百度地图与ViewPager滑动冲突
        mapView.getChildAt(0).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });


    }

    public void checkLoactionPermission() {
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        } else {
            locationClient.start();//开始定位
        }
    }

    //定位权限的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200://刚才的识别码
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//用户同意权限,执行我们的操作
                    //开始定位
                    locationClient.start();
                } else {//用户拒绝之后,当然我们也可以弹出一个窗口,直接跳转到系统设置页面
                    getHoldingActivity().showToast("未开启定位权限,请手动到设置去开启权限");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if (mapView != null) {
            mapView.onDestroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        if (mapView != null) {
            mapView.onResume();
        }
        checkLoactionPermission();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        if (mapView != null) {
            mapView.onPause();
        }
    }

    /**
     * 实现定位回调
     */
    class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            //获取纬度信息
            double latitude = location.getLatitude();
            //获取经度信息
            double longitude = location.getLongitude();

            Logger.w("经度" + longitude);
            Logger.w("纬度" + latitude);
            //获取定位精度，默认值为0.0f
            float radius = location.getRadius();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            String coorType = location.getCoorType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            int errorCode = location.getLocType();

            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(0).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mapView.getMap().setMyLocationData(locData);//设置定位信息
        }
    }

    //规划导航路线
    public void planRoad(){
//        BaiduNaviManagerFactory.getBaiduNaviManager().init(activity, mSDCardPath, APP_FOLDER_NAME,
//                new IBaiduNaviManager.INaviInitListener());
//        BNRoutePlanNode sNode = new BNRoutePlanNode(116.30142, 40.05087, "百度大厦", "百度大厦", coType);
//        BNRoutePlanNode eNode = new BNRoutePlanNode(116.39750, 39.90882, "北京天安门", "北京天安门", coType);
    }
}

