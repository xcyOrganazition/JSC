package cn.com.jinshangcheng.ui.car;

import java.util.ArrayList;

import cn.com.jinshangcheng.base.IBasePresenter;
import cn.com.jinshangcheng.base.IBaseView;
import cn.com.jinshangcheng.bean.CarBean;
import cn.com.jinshangcheng.bean.CarMaintainBean;
import cn.com.jinshangcheng.bean.PositionBean;
import cn.com.jinshangcheng.bean.mycst.CheckDataBean;
import io.reactivex.Observer;

/**
 * MVP功能整合
 */
public interface CarContract {

    interface IView extends IBaseView<IPresenter> {

        void showCarList(ArrayList<CarBean> carList);

        void refreshPosition(PositionBean positionBean);

        void refreshMaintainData(CarMaintainBean carMaintainBean);

        void intentToSaveActivity();

        void refreshLastCheckData(CheckDataBean checkDataBean);


    }

    interface IPresenter extends IBasePresenter {

        void getCarList();

        void getCarPosition(String carId);

        void getCheckReportLast(String carId);

        void getCarMaintainInfo();

        void getCanRoadHelp();

    }

    interface IModel {
        void loadCarList(Observer observer);

        void loadCarPosition(String car, Observer observer);

        void loadCarMaintainInfo(Observer observer);

        void getCheckReportLast(Observer observer);

        void loadCanRoadHelp(Observer observer);

    }

}
