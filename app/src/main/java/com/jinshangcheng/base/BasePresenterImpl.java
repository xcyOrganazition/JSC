package com.jinshangcheng.base;

import java.lang.ref.SoftReference;

public class BasePresenterImpl implements IBasePresenter {
    protected SoftReference<IBaseView> mViewRef;

    @Override
    public void attachView(IBaseView view) {
        mViewRef = new SoftReference<>(view);
    }

    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
