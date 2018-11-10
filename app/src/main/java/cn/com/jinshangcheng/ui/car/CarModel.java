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
}
