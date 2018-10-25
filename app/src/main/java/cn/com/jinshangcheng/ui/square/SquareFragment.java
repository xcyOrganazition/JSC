package cn.com.jinshangcheng.ui.square;


import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseFragment;
import cn.com.jinshangcheng.bean.CarBrandsBean;
import cn.com.jinshangcheng.widget.SelectCarTypeWindow;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import platform.cston.httplib.bean.CarModelResult;
import platform.cston.httplib.bean.CarTypeResult;
import platform.cston.httplib.search.CarBrandInfoSearch;
import platform.cston.httplib.search.OnResultListener;

/**
 * 广场模块
 */
public class SquareFragment extends BaseFragment {

    private static SquareFragment fragment;
    @BindView(R.id.root)
    LinearLayout root;
    Unbinder unbinder;
    private SelectCarTypeWindow selectCarTypeWindow;
    private CarBrandsBean carBrandsBean;

    private List<CarTypeResult.DataEntity.CarTypesEntity> carTypeList = new ArrayList();//汽车类型


    public SquareFragment() {
        // Required empty public constructor
    }

    public static SquareFragment getInstance() {
        if (fragment == null) {
            synchronized (SquareFragment.class) {
                if (fragment == null) {
                    fragment = new SquareFragment();
                }
            }
        }
        return fragment;
    }


    @Override
    public View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_square, null, false);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    /**
     * 请求车型 父列表
     */
    private void getCarTypeData() {
        showLoading();
        carTypeList.clear();
        CarBrandInfoSearch.getInstance().GetCarTypeResult(carBrandsBean.brandId, new OnResultListener.CarTypeResultListener() {

            @Override
            public void onCarTypeResult(CarTypeResult carTypeResult, boolean isError, Throwable throwable) {
                if (!isError) {
//                    Logger.w("车型   " + carTypeResult.getData().get(0).getCarTypes());
                    dismissLoading();
                    for (int i = 0; i < carTypeResult.getData().size(); i++) {
                        carTypeList.addAll(carTypeResult.getData().get(i).getCarTypes());
                    }
                    selectCarTypeWindow = new SelectCarTypeWindow(getContext(), carBrandsBean, carTypeList);
                    selectCarTypeWindow.showAtLocation(root, Gravity.RIGHT, 0, 0);
                }
            }
        });
    }


    @OnClick({R.id.showLoading, R.id.hideLoading})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.showLoading:
                carBrandsBean = new CarBrandsBean("http://file.kartor.cn/resize/image/carBrand/20151126/40.jpg",
                        "保时捷", "33", "B");
                getCarTypeData();

                break;
            case R.id.hideLoading:
                selectCarTypeWindow.dismiss();
                break;
        }
    }

}
