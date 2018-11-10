package cn.com.jinshangcheng.ui.car;

import java.util.ArrayList;

import cn.com.jinshangcheng.base.IBasePresenter;
import cn.com.jinshangcheng.base.IBaseView;
import cn.com.jinshangcheng.bean.CarBean;
import io.reactivex.Observer;

/**
 * MVP功能整合
 */
public interface CarContract {

    interface IView extends IBaseView<IPresenter> {

        void showCarList(ArrayList<CarBean> carList);

    }

    interface IPresenter extends IBasePresenter {

        void getCarList();

    }

    interface IModel {
        void loadCarList(Observer observer);
    }

}
