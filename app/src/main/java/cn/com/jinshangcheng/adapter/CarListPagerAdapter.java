package cn.com.jinshangcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.bean.Car;

/**
 * 我的汽车VP列表
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
        TextView tvTip = view.findViewById(R.id.tv_tip);
        tvTip.setFocusable(false);
        tvTip.setVisibility(carList.size() > 0 ? View.VISIBLE : View.GONE);
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

    public void refreshList(List<Car> carList) {
        this.carList = carList;
        this.notifyDataSetChanged();
    }


}
