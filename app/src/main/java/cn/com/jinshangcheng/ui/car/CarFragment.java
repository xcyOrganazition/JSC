package cn.com.jinshangcheng.ui.car;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.share.LocationShareURLOption;
import com.baidu.mapapi.search.share.OnGetShareUrlResultListener;
import com.baidu.mapapi.search.share.ShareUrlResult;
import com.baidu.mapapi.search.share.ShareUrlSearch;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.adapter.CarListPagerAdapter;
import cn.com.jinshangcheng.base.BaseFragment;
import cn.com.jinshangcheng.bean.Car;
import cn.com.jinshangcheng.utils.DensityUtil;
import platform.cston.httplib.bean.AuthorizationInfo;


/**
 * 汽车还是爱车Fragment
 */
public class CarFragment extends BaseFragment implements CarContract.IView {


    @BindView(R.id.vp_carList)
    ViewPager vpCarList;
    @BindView(R.id.tv_mileNum)
    TextView tvMileNum;
    @BindView(R.id.tv_fuelNum)
    TextView tvFuelNum;
    @BindView(R.id.tv_checkTime)
    TextView tvCheckTime;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.bd_mapView)
    MapView bdMapView;
    @BindView(R.id.tv_insurance)
    TextView tvInsurance;
    @BindView(R.id.tv_maintenance)
    TextView tvMaintenance;
    @BindView(R.id.tv_annual)
    TextView tvAnnual;
    Unbinder unbinder;

    private double lat = 39.963175;
    private double lng = 116.400244;


    private static CarFragment carFragment;
    private Car car;
    private List<Car> carList = new ArrayList<>();

    private CarContract.IPresenter mPresenter;

    public CarFragment() {
        // Required empty public constructor
        mPresenter = new CarPresenter(this);
    }

    public static CarFragment getInstance() {
        if (carFragment == null) {
            synchronized (CarFragment.class) {
                if (carFragment == null) {
                    carFragment = new CarFragment();
                }
            }
        }
        return carFragment;
    }


    @Override
    public View createView(LayoutInflater inflater) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_car, null, false);
    }


    @Override
    public void initData() {
        car = new Car("卡宴", "京A23282", "保时捷", "保时捷系列");
        carList.add(car);
        carList.add(car);

    }

    @Override
    public void initView() {
        bdMapView.showScaleControl(false);//隐藏比例尺
        bdMapView.showZoomControls(false);//隐藏放大缩小按钮
        LatLng point = new LatLng(lat, lng);//构建Marker坐标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_location);//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        bdMapView.getMap().addOverlay(option);

        //解决百度地图与ViewPager滑动冲突
        bdMapView.getChildAt(0).setOnTouchListener(new View.OnTouchListener() {
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
        ShareUrlSearch mShareUrlSearch = ShareUrlSearch.newInstance();
        mShareUrlSearch.setOnGetShareUrlResultListener(new OnGetShareUrlResultListener() {
            @Override
            public void onGetPoiDetailShareUrlResult(ShareUrlResult shareUrlResult) {
                Logger.w("onGetPoiDetailShareUrlResult", shareUrlResult);
            }

            @Override
            public void onGetLocationShareUrlResult(ShareUrlResult shareUrlResult) {
                Logger.w("shareUonGetLocationShareUrlResultrlResult", shareUrlResult);
            }

            @Override
            public void onGetRouteShareUrlResult(ShareUrlResult shareUrlResult) {
                Logger.w("onGetRouteShareUrlResult", shareUrlResult);
            }
        });
        mShareUrlSearch.requestLocationShareUrl(new LocationShareURLOption()
                .location(new LatLng(lat, lng)).snippet("测试分享点")
                .name("分享点"));


        CarListPagerAdapter adapter = new CarListPagerAdapter(carList, getHoldingActivity());
        vpCarList.setAdapter(adapter);
        vpCarList.setPageMargin(DensityUtil.dip2px(getHoldingActivity(), 6));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter.getCarList();

    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    @Override
    public void setPresenter(CarContract.IPresenter presenter) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    public void toastErrorMsg(String errorMsg) {
        Toast.makeText(getHoldingActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 填充车辆ViewPager数据
     */
    @Override
    public void showCarList(ArrayList<AuthorizationInfo.Cars> carList) {

        Logger.w("填充车辆 " + carList);

    }


    @OnClick({R.id.ll_check, R.id.ll_report, R.id.ll_violation, R.id.ll_help, R.id.tv_checkDetail,
            R.id.tv_insurance, R.id.tv_maintenance, R.id.tv_annual})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_check://一键检测:
                break;
            case R.id.ll_report://用车报告
                break;
            case R.id.ll_violation://查询违章
                break;
            case R.id.ll_help://道路救援
                break;
            case R.id.tv_checkDetail://查询检测详情
                break;
            case R.id.tv_insurance://保险信息
                intent = new Intent(getActivity(), InsuranceActivity.class);
                break;
            case R.id.tv_maintenance://保养信息
                intent = new Intent(getActivity(), MaintenanceActivity.class);
                break;
            case R.id.tv_annual://年审信息:
                intent = new Intent(getActivity(), AnnualActivity.class);

                break;
        }
        if (intent != null) {
            getActivity().startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        bdMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        bdMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        bdMapView.onPause();
    }

}

