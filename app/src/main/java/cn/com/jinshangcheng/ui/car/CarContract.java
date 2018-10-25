package cn.com.jinshangcheng.ui.car;

import cn.com.jinshangcheng.base.IBasePresenter;
import cn.com.jinshangcheng.base.IBaseView;

import java.util.ArrayList;

import platform.cston.httplib.bean.AuthorizationInfo;
import platform.cston.httplib.search.OnResultListener;

/**
 * MVP功能整合
 */
public interface CarContract {

    interface IView extends IBaseView<IPresenter> {

        void showCarList(ArrayList<AuthorizationInfo.Cars> carList);

    }

    interface IPresenter extends IBasePresenter {

        void getCarList();

    }

    interface IModel {
        ArrayList<AuthorizationInfo.Cars> loadCarList(OnResultListener.OnGetCarListResultListener listener);
    }

}
