package com.jinshangcheng.ui.car;

import com.jinshangcheng.base.BasePresenterImpl;
import com.jinshangcheng.bean.LoginBean;
import com.orhanobut.logger.Logger;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import platform.cston.httplib.bean.CarListResult;
import platform.cston.httplib.search.CarInfoSearch;
import platform.cston.httplib.search.OnResultListener;

public class CarPresenter extends BasePresenterImpl implements CarContract.IPresenter {

    private CarModel model;
    private CarContract.IView carView;

    public CarPresenter(CarContract.IView carView) {
        this.model = new CarModel();
        this.carView = carView;
        carView.setPresenter(this);
    }

    @Override
    public void getCarList() {
        CarInfoSearch.newInstance().GetCarInfoResult(new OnResultListener.OnGetCarListResultListener() {
            @Override
            public void onGetCarListResult(CarListResult carListResult, boolean b, Throwable throwable) {

            }
        });
        HashMap<String, String> map = new HashMap<>();
        map.put("workerCode", "admin");
        map.put("password", "123456");
        map.put("type", "1");
        model.loadCarList(map, new Observer<LoginBean>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(LoginBean loginBean) {

                Logger.d(loginBean);

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
