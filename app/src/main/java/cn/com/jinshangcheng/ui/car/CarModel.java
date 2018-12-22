package cn.com.jinshangcheng.ui.car;

import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.CarMaintainBean;
import cn.com.jinshangcheng.bean.mycst.CheckDataBean;
import cn.com.jinshangcheng.net.RetrofitService;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
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
//        RetrofitService.getRetrofit().getCarMaintainInfo(MyApplication.getUserId(), MyApplication.getCarId())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);

        Observable<BaseBean<CarMaintainBean>> carMaintainInfo = RetrofitService.getRetrofit()
                .getCarMaintainInfo(MyApplication.getUserId(), MyApplication.getCarId());
        Observable<BaseBean<CheckDataBean>> realTimeCheckReport = RetrofitService.getRetrofit()
                .getRealTimeCheckReport(MyApplication.getUserId(), MyApplication.getCarId());
        Observable.zip(carMaintainInfo, realTimeCheckReport, new BiFunction<BaseBean<CarMaintainBean>, BaseBean<CheckDataBean>, BaseBean<CarMaintainBean>>() {
            @Override
            public BaseBean<CarMaintainBean> apply(BaseBean<CarMaintainBean> baseBean, BaseBean<CheckDataBean> reportBean) throws Exception {
                if (baseBean.code.equals("0") && reportBean.code.equals("0")) {
                    if (null != baseBean.data && null != reportBean.data) {
                        baseBean.data.setBoxmile(reportBean.data.mileage);
                    }
                }
                return baseBean;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取车辆实时检测信息 用于计算保养数据 其实只有mailage有用
     *
     * @param observer
     */
    @Override
    public void getRealTimeCheckReport(Observer observer) {
//        RetrofitService.getRetrofit().getRealTimeCheckReport(MyApplication.getUserId(), MyApplication.getCarId())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
    }

    @Override
    public void loadCanRoadHelp(Observer observer) {
        RetrofitService.getRetrofit().getCanRoadHelp(MyApplication.getCurrentCarBean().getPlatenumber(), MyApplication.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
