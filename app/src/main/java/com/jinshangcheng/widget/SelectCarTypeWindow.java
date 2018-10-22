package com.jinshangcheng.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;

import com.jinshangcheng.R;
import com.jinshangcheng.adapter.CarTypeAdapter;
import com.jinshangcheng.bean.CarBrandsBean;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import platform.cston.httplib.bean.CarModelResult;
import platform.cston.httplib.bean.CarTypeResult;
import platform.cston.httplib.search.CarBrandInfoSearch;
import platform.cston.httplib.search.OnResultListener;

import static com.jinshangcheng.MyApplication.getContext;

public class SelectCarTypeWindow extends PopupWindow {
    private CarBrandsBean carBrandsBean;
    private Context context;
    private ExpandableListView listView;
    private List<CarTypeResult.DataEntity.CarTypesEntity> carTypeList;//汽车类型
    private List<List<CarModelResult.DataEntity>> carModelListGroup;//车款列表组
    private CarTypeAdapter adapter;


    public SelectCarTypeWindow(Context context, CarBrandsBean carBrandsBean, List<CarTypeResult.DataEntity.CarTypesEntity> carTypeList) {
        this.carBrandsBean = carBrandsBean;
        this.carTypeList = carTypeList;
        this.context = context;
        setAnimationStyle(R.style.AnimationRightFade);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);

        setOutsideTouchable(true);  //默认设置outside点击无响应
        setFocusable(true);
        setWindowBackgroundAlpha(1);
        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_car_type, null);
        setContentView(contentView);
        listView = contentView.findViewById(R.id.listView);
        carModelListGroup = new ArrayList<>();
        for (int i = 0; i < carTypeList.size(); i++) {
            carModelListGroup.add(new ArrayList<CarModelResult.DataEntity>());
        }
        initListView();

    }

    private void initListView() {
        adapter = new CarTypeAdapter(carTypeList, carModelListGroup, context);
        //父条目点击
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (carModelListGroup.isEmpty() || carModelListGroup.get(groupPosition) == null || carModelListGroup.get(groupPosition).size() == 0) {
                    getCarModelData(groupPosition);//没有数据请求车款
                }
                return false;
            }
        });
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Logger.w("选择的车型 = " + carModelListGroup.get(groupPosition).get(childPosition));
                return false;
            }
        });
        listView.setAdapter(adapter);
    }

    /**
     * 请求车型 父列表
     */
    private void getCarTypeData() {
        CarBrandInfoSearch.getInstance().GetCarTypeResult(carBrandsBean.brandId, new OnResultListener.CarTypeResultListener() {
            @Override
            public void onCarTypeResult(CarTypeResult carTypeResult, boolean isError, Throwable throwable) {
                if (!isError) {
//                    Logger.w("车型   " + carTypeResult.getData().get(0).getCarTypes());
                    carTypeList = carTypeResult.getData().get(0).getCarTypes();
                    adapter.refreshList(carTypeList, new ArrayList<CarModelResult.DataEntity>(), 0);

                }
            }
        });
    }

    /**
     * 请求车款 子列表
     */
    private void getCarModelData(final int position) {
        //需要穿TypeId
        CarBrandInfoSearch.getInstance().GetCarModelResult(carTypeList.get(position).typeId, new OnResultListener.CarModelResultListener() {
            @Override
            public void onCarModelResult(CarModelResult carModelResult, boolean isError, Throwable throwable) {
                if (!isError) {
//                    Logger.w("车款   " + carModelResult.getData());
                    List<CarModelResult.DataEntity> curModelList = carModelListGroup.get(position);
                    if (curModelList == null) {
                        curModelList = new ArrayList<>();
                    }
                    if (carModelResult.getData() != null && carModelResult.getData().size() > 0) {
                        curModelList.addAll(carModelResult.getData());
                    }
                    adapter.refreshList(carTypeList, carModelListGroup);
                }
            }
        });
    }


    /**
     * 控制窗口背景的不透明度
     */
    private void setWindowBackgroundAlpha(float alpha) {
        Window window = ((Activity) context).getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = alpha;
        window.setAttributes(layoutParams);
    }


}
