package com.jinshangcheng.ui.car;

import com.jinshangcheng.MyApplication;
import com.jinshangcheng.net.RetrofitService;
import com.jinshangcheng.utils.Map2JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import platform.cston.httplib.bean.AuthorizationInfo;
import platform.cston.httplib.search.CarInfoSearch;
import platform.cston.httplib.search.OnResultListener;

public class CarModel implements CarContract.IModel {
    @Override
    public ArrayList<AuthorizationInfo.Cars> loadCarList(OnResultListener.OnGetCarListResultListener listener) {
//
//        RetrofitService.getRetrofit().login("userName", "pwd")
//                .subscribeOn(Schedulers.io())//
//                .observeOn(AndroidSchedulers.mainThread())//
//                .subscribe(observer);

        CarInfoSearch.newInstance().GetCarInfoResult(listener);

        if (MyApplication.getAuthorInfo() != null) {

            return MyApplication.getAuthorInfo().getCarsArrayList();
        } else {
            return null;
        }

    }
}
