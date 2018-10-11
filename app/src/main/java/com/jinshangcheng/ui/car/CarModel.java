package com.jinshangcheng.ui.car;

import com.jinshangcheng.net.RetrofitService;
import com.jinshangcheng.utils.Map2JsonUtils;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CarModel implements CarContract.IModel {
    @Override
    public void loadCarList(HashMap map, Observer observer) {

        RetrofitService.getRetrofit().login("userName", "pwd")
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(observer);

    }
}
