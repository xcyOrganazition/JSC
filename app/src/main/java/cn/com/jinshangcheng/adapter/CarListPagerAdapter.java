package cn.com.jinshangcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.bean.CarBean;
import cn.com.jinshangcheng.utils.GlideUtils;

/**
 * 我的汽车VP列表
 */
public class CarListPagerAdapter extends PagerAdapter {
    @BindView(R.id.tv_carLicense)
    TextView tvCarLicense;
    @BindView(R.id.tv_carBrand)
    TextView tvCarBrand;
    @BindView(R.id.tv_carType)
    TextView tvCarType;
    @BindView(R.id.iv_carImg)
    ImageView ivCarImg;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.iv_branchImg)
    ImageView ivBranchImg;
    private List<CarBean> carList;
    private Context context;


    public CarListPagerAdapter(List<CarBean> carList, Context context) {
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
        ButterKnife.bind(this, view);
        tvTip.setFocusable(false);
        tvTip.setVisibility(carList.size() > 1 ? View.VISIBLE : View.GONE);
        CarBean bean = carList.get(position);
        tvCarLicense.setText(bean.getPlatenumber());
        tvCarBrand.setText(bean.getBrandname());
        GlideUtils.loadImage(context, bean.getBrandpath(), ivBranchImg);
        GlideUtils.loadImage(context, bean.getBrandpath(), ivCarImg);
        tvCarType.setText(bean.getTypename());
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

    public void refreshList(List<CarBean> carList) {
        this.carList = carList;
        this.notifyDataSetChanged();
    }


}
