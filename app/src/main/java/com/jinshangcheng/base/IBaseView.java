
package com.jinshangcheng.base;

public interface IBaseView<T extends IBasePresenter> {

    void setPresenter(T presenter);

    void showLoading();

    void hideLoading();
}
