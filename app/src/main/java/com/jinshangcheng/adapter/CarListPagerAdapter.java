package com.jinshangcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinshangcheng.R;
import com.jinshangcheng.bean.Car;

import java.util.List;
import java.util.zip.Inflater;

/**
 * 我的汽车列表
 */
public class CarListPagerAdapter extends PagerAdapter {
    private List<Car> carList;
    private Context context;


    public CarListPagerAdapter(List<Car> carList, Context context) {
        super();
        this.carList = carList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return carList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_mycar, null);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


}
