package com.jinshangcheng.ui.car;

import com.jinshangcheng.base.IBasePresenter;
import com.jinshangcheng.base.IBaseView;

import java.util.HashMap;

import io.reactivex.Observer;

/**
 * MVP功能整合
 */
public interface CarContract {

    interface IView extends IBaseView<IPresenter> {

        void showCarList();

    }

    interface IPresenter extends IBasePresenter {

        void getCarList();

    }

    interface IModel {
        void loadCarList(HashMap map, Observer observer);
    }

}
