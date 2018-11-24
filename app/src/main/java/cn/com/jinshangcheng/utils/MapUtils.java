package cn.com.jinshangcheng.utils;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;

public class MapUtils {

    private static GeoCoder mSearch;

    public static void getAddress(LatLng point, OnGetGeoCoderResultListener onGetGeoCoderResultListener) {
        mSearch = GeoCoder.newInstance();//创建地理编码检索实例；
        mSearch.setOnGetGeoCodeResultListener(onGetGeoCoderResultListener);
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(point));
    }

}
