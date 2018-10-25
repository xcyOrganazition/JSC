package cn.com.jinshangcheng.base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment {

    /**
     * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
     */
    private boolean hasCreateView;

    /**
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     */
    private boolean isFragmentVisible;

    /**
     * onCreateView()里返回的view,在onCreateView里必须对该变量进行初始化
     */
    public View view;
    public Context context;
    public Bundle savedInstanceState;
    private BaseActivity mActivity;
    Unbinder unbinder;

    // 初始化数据
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = (Context) getActivity();
        this.savedInstanceState = savedInstanceState;
        hasCreateView = false;
        isFragmentVisible = false;
        initData();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    // 加载布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = createView(inflater);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    // 填充数据
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
//        Log.e("onHiddenChanged", "onHiddenChanged: " + hidden);
    }

    protected void showLoading() {
        if (mActivity != null)
            mActivity.showLoading();
    }

    protected void dismissLoading() {
        if (mActivity != null)
            mActivity.dismissLoading();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!hasCreateView && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
        if (view == null) {
            return;
        }
        hasCreateView = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }

    /**
     * 获取Fragment的可见状态
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
    }

    //获取宿主Activity
    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }


    /**
     * 加载布局
     *
     * @return 布局
     */
    public abstract View createView(LayoutInflater inflater);

    /**
     * 填充数据
     */
    public abstract void initData();


    /**
     * 初始化其他组件
     */
    public abstract void initView();


}
