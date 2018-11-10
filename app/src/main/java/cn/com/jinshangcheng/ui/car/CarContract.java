package cn.com.jinshangcheng.ui.car;

import java.util.ArrayList;

import cn.com.jinshangcheng.base.IBasePresenter;
import cn.com.jinshangcheng.base.IBaseView;
import cn.com.jinshangcheng.bean.CarBean;
import cn.com.jinshangcheng.bean.CarMaintainBean;
import cn.com.jinshangcheng.bean.PositionBean;
import io.reactivex.Observer;

/**
 * MVP功能整合
 */
public interface CarContract {

    interface IView extends IBaseView<IPresenter> {

        void showCarList(ArrayList<CarBean> carList);

        void refreshPosition(PositionBean positionBean);

        void refreshMaintainData(CarMaintainBean carMaintainBean);


    }

    interface IPresenter extends IBasePresenter {

        void getCarList();

        void getCarPosition(String carId);

        void getCarMaintainInfo();

    }

    interface IModel {
        void loadCarList(Observer observer);

        void loadCarPosition(String car, Observer observer);

        void loadCarMaintainInfo(Observer observer);

    }

}
