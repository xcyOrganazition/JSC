package cn.com.jinshangcheng.ui.car;

import android.widget.Toast;

import cn.com.jinshangcheng.base.BasePresenterImpl;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import platform.cston.httplib.bean.AuthorizationInfo;
import platform.cston.httplib.bean.CarListResult;
import platform.cston.httplib.search.OnResultListener;

public class CarPresenter extends BasePresenterImpl implements CarContract.IPresenter {

    private CarModel model;
    private CarFragment carView;

    CarPresenter(CarFragment carView) {
        this.model = new CarModel();
        this.carView = carView;
        carView.setPresenter(this);
    }

    @Override
    public void getCarList() {
//        CarInfoSearch.newInstance().GetCarInfoResult(new OnResultListener.OnGetCarListResultListener() {
//            @Override
//            public void onGetCarListResult(CarListResult carListResult, boolean b, Throwable throwable) {
//
//            }
//        });
//        HashMap<String, String> map = new HashMap<>();
//        map.put("workerCode", "admin");
//        map.put("password", "123456");
//        map.put("type", "1");


        ArrayList<AuthorizationInfo.Cars> carList = model.loadCarList(new OnResultListener.OnGetCarListResultListener() {
            @Override
            public void onGetCarListResult(CarListResult carListResult, boolean isError, Throwable throwable) {

                if (!isError) {
                    if (carListResult.getCode().equals("0")) {
                        Logger.w("我的车辆 ：" + carListResult);
                    } else {
                        carView.toastErrorMsg(carListResult.getResult());
                    }
                } else {
                    Logger.e("CarListError   :  " + throwable);
                }

            }
        });
        carView.showCarList(carList);

    }
}
