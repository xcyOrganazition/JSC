package cn.com.jinshangcheng.utils;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.utils.CoordinateConverter;

public class MapUtils {

    private static GeoCoder mSearch;

    /**
     * 坐标转地址文字
     *
     * @param point
     * @param onGetGeoCoderResultListener
     */
    public static void getAddress(LatLng point, OnGetGeoCoderResultListener onGetGeoCoderResultListener) {
        mSearch = GeoCoder.newInstance();//创建地理编码检索实例；
        mSearch.setOnGetGeoCodeResultListener(onGetGeoCoderResultListener);
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(point));
    }

    //通用坐标转换百度坐标方法
    public static LatLng changeMapTerm(LatLng sourceLatLng) {
        // 将GPS设备采集的原始GPS坐标转换成百度坐标
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        // sourceLatLng待转换坐标
        converter.coord(sourceLatLng);
        return converter.convert();
    }
}
