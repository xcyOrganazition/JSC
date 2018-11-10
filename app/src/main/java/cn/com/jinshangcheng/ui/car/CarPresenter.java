package cn.com.jinshangcheng.ui.car;

import java.util.ArrayList;

import cn.com.jinshangcheng.base.BasePresenterImpl;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.CarBean;
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
                    carView.showCarList(arrayListBaseBean.data);
                } else {
                    carView.toastErrorMsg(arrayListBaseBean.errorMsg);
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
}
