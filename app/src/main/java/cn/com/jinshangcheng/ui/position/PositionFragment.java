package cn.com.jinshangcheng.ui.position;


import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import com.baidu.mapapi.map.MapView;

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

    }

    @Override
    public void initView() {
        mapView.showScaleControl(false);//隐藏比例尺
        mapView.showZoomControls(false);//隐藏放大缩小按钮
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
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        if (mapView != null) {
            mapView.onPause();
        }
    }
}
