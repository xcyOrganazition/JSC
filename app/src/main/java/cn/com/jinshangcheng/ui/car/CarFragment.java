package cn.com.jinshangcheng.ui.car;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.share.LocationShareURLOption;
import com.baidu.mapapi.search.share.OnGetShareUrlResultListener;
import com.baidu.mapapi.search.share.ShareUrlResult;
import com.baidu.mapapi.search.share.ShareUrlSearch;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.adapter.CarListPagerAdapter;
import cn.com.jinshangcheng.base.BaseFragment;
import cn.com.jinshangcheng.bean.CarBean;
import cn.com.jinshangcheng.bean.CarMaintainBean;
import cn.com.jinshangcheng.bean.PositionBean;
import cn.com.jinshangcheng.extra.explain.activity.CarDetectionActivity;
import cn.com.jinshangcheng.utils.ArrayUtils;
import cn.com.jinshangcheng.utils.DateUtils;
import cn.com.jinshangcheng.utils.DensityUtil;


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
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_emptyView)
    TextView tvEmptyView;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    Unbinder unbinder;

    private double lat = 39.963175;
    private double lng = 116.400244;


    private static CarFragment carFragment;
    private static int REQUEST_CODE = 0x11;
    public static int RESULT_CODE = 0x12;
    private List<CarBean> carList = new ArrayList<>();

    private CarPresenter mPresenter;
    private CarListPagerAdapter adapter;
    private GeoCoder mSearch;//地理反编码
    private CarMaintainBean carMaintainBean;//车辆三审信息
    private ViewPager.OnPageChangeListener pageChangedListener;

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


    }

    @Override
    public void initView() {
        bdMapView.showScaleControl(false);//隐藏比例尺
        bdMapView.showZoomControls(false);//隐藏放大缩小按钮
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

            }

            @Override
            public void onGetLocationShareUrlResult(ShareUrlResult shareUrlResult) {

            }

            @Override
            public void onGetRouteShareUrlResult(ShareUrlResult shareUrlResult) {

            }
        });
        mShareUrlSearch.requestLocationShareUrl(new LocationShareURLOption()
                .location(new LatLng(lat, lng)).snippet("测试分享点")
                .name("分享点"));
        //初始化头部我的车辆VP
        adapter = new CarListPagerAdapter(carList, getHoldingActivity());
        vpCarList.setAdapter(adapter);
        vpCarList.setPageMargin(DensityUtil.dip2px(getHoldingActivity(), 6));
        pageChangedListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (carList.size() > position) {
                    MyApplication.setCarId(carList.get(position).getCarid());
                    MyApplication.setCurrentCarBean(carList.get(position));
                    getOtherData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        vpCarList.addOnPageChangeListener(pageChangedListener);

        mPresenter.getCarList();//请求车辆数据
    }


    /**
     * 获取其他数据（位置、油耗、保险、年审等）
     */
    public void getOtherData() {
        tvMileNum.setText(MyApplication.getCurrentCarBean().getMileage());//总里程
        tvFuelNum.setText(MyApplication.getCurrentCarBean().getFuel());//油耗
        mPresenter.getCarPosition(MyApplication.getCarId());//位置
        mPresenter.getCarMaintainInfo();//年审、保险、保养
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
    public void showCarList(ArrayList<CarBean> carLists) {
        this.carList = carLists;
        if (ArrayUtils.hasContent(carList)) {//有车辆数据
            tvEmptyView.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            adapter.refreshList(carList);
            getOtherData();//请求车辆的其他数据
        } else {//无车辆数据
            tvEmptyView.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }
    }

    //刷新车辆位置数据
    @Override
    public void refreshPosition(PositionBean positionBean) {
        LatLng point = new LatLng(positionBean.getLatitude(), positionBean.getLongitude());//构建Marker坐标
        bdMapView.getMap().setMapStatus(MapStatusUpdateFactory.newLatLng(point));
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_location);//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        bdMapView.getMap().addOverlay(option);//在地图上添加Marker，并显示

        mSearch = GeoCoder.newInstance();//创建地理编码检索实例；
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {

            public void onGetGeoCodeResult(GeoCodeResult result) {
//                Logger.w("GetGeoCodeResult:" + result);
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有检索到结果
                } else {  //获取地理编码结果
                }
            }

            @Override//经纬度转地址
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
//                Logger.w("ReverseGeoCodeResult:" + result);
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有找到检索结果
                    tvLocation.setText("未知");
                } else {//获取反向地理编码结果
                    tvLocation.setText(result.getAddress());
                }
            }
        };
        mSearch.setOnGetGeoCodeResultListener(listener);
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(point));
    }

    //刷新保养保险年审信息
    @Override
    public void refreshMaintainData(CarMaintainBean maintainBean) {
        this.carMaintainBean = maintainBean;
        tvInsurance.setText(DateUtils.getYMDTime(carMaintainBean.getInsurancedeadline()));//保险信息
        if (null != carMaintainBean.getMaintain()) {//保养信息
            tvMaintenance.setText(DateUtils.getYMDTime(carMaintainBean.getMaintain().getLastmaintaintime()));
        } else {//没有保养数据
            tvMaintenance.setText("");
        }
        tvAnnual.setText(DateUtils.getYMDTime(carMaintainBean.getAnnualtrialdeadline()));//年审信息
    }


    @OnClick({R.id.ll_check, R.id.ll_report, R.id.ll_violation, R.id.ll_help, R.id.tv_checkDetail,
            R.id.tv_insurance, R.id.tv_maintenance, R.id.tv_annual, R.id.iv_shareLocation})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_check://一键检测:
                String carId = MyApplication.getCarId();
                intent = new Intent(getActivity(), CarDetectionActivity.class);
                intent.putExtra("OPENCARID", carId);
                break;
            case R.id.ll_report://用车报告
                intent = new Intent(getActivity(), CarReportActivity.class);
                break;
            case R.id.ll_violation://查询违章
                break;
            case R.id.ll_help://道路救援
                break;
            case R.id.tv_checkDetail://查询检测详情
                break;
            case R.id.tv_insurance://保险信息
                intent = new Intent(getActivity(), InsuranceActivity.class);
                intent.putExtra("maintainBean", carMaintainBean);
                startActivityForResult(intent, REQUEST_CODE);
                return;
            case R.id.tv_maintenance://保养信息
                intent = new Intent(getActivity(), MaintenanceActivity.class);
                intent.putExtra("maintainBean", carMaintainBean);
                startActivityForResult(intent, REQUEST_CODE);
                return;
            case R.id.tv_annual://年审信息:
                intent = new Intent(getActivity(), AnnualActivity.class);
                intent.putExtra("maintainBean", carMaintainBean);
                startActivityForResult(intent, REQUEST_CODE);
            case R.id.iv_shareLocation://分享:

                return;
        }
        if (intent != null) {
            getActivity().startActivity(intent);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //修改过三审数据  需要重新请求
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
            mPresenter.getCarMaintainInfo();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if (bdMapView != null) {
            bdMapView.onDestroy();
        }
        if (mSearch != null) {
            mSearch.destroy();
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

