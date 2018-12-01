package cn.com.jinshangcheng.ui.car;

import java.util.ArrayList;

import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.base.BasePresenterImpl;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.CarBean;
import cn.com.jinshangcheng.bean.CarMaintainBean;
import cn.com.jinshangcheng.bean.PositionBean;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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
        model.loadCarList(new Observer<BaseBean<ArrayList<CarBean>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseBean<ArrayList<CarBean>> arrayListBaseBean) {
                if (arrayListBaseBean.code.equals("0") && null != arrayListBaseBean.data) {
                    MyApplication.setCarId(arrayListBaseBean.data.get(0).getCarid());
                    MyApplication.setCurrentCarBean(arrayListBaseBean.data.get(0));
                    carView.showCarList(arrayListBaseBean.data);
                } else {
                    carView.toastErrorMsg(arrayListBaseBean.message);
                }
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

    @Override
    public void getCarPosition(String carId) {
        model.loadCarPosition(carId, new Observer<BaseBean<PositionBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseBean<PositionBean> baseBean) {
                if (baseBean.code.equals("0") && null != baseBean.data) {
                    carView.refreshPosition(baseBean.data);
                } else {
                    carView.toastErrorMsg(baseBean.message);
                }
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

    @Override
    public void getCarMaintainInfo() {
        model.loadCarMaintainInfo(new Observer<BaseBean<CarMaintainBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseBean<CarMaintainBean> baseBean) {
                if (baseBean.code.equals("0")) {
                    if (null != baseBean.data) {
                        carView.refreshMaintainData(baseBean.data);
                    }
                } else {
                    carView.toastErrorMsg(baseBean.message);
                }
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

    @Override
    public void getCanRoadHelp() {
        carView.showLoading();
        model.loadCanRoadHelp(new Observer<BaseBean<CarMaintainBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseBean<CarMaintainBean> baseBean) {
                if (baseBean.code.equals("0")) {
                    carView.intentToSaveActivity();
                } else {
                    carView.toastErrorMsg(baseBean.message);
                }
            }

            @Override
            public void onError(Throwable e) {
                carView.hideLoading();
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                carView.hideLoading();
            }
        });
    }
}
