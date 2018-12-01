package cn.com.jinshangcheng.ui.car;

import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.net.RetrofitService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CarModel implements CarContract.IModel {
    @Override
    public void loadCarList(Observer observer) {

        RetrofitService.getRetrofit().getCarList(MyApplication.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void loadCarPosition(String carId, Observer observer) {
        RetrofitService.getRetrofit().getCarPosition(MyApplication.getUserId(), carId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void loadCarMaintainInfo(Observer observer) {
        RetrofitService.getRetrofit().getCarMaintainInfo(MyApplication.getUserId(), MyApplication.getCarId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void loadCanRoadHelp(Observer observer) {
        RetrofitService.getRetrofit().getCanRoadHelp(MyApplication.getCurrentCarBean().getPlatenumber(), MyApplication.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
