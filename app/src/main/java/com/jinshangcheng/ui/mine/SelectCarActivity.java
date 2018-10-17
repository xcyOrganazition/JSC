package com.jinshangcheng.ui.mine;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.github.promeg.pinyinhelper.Pinyin;
import com.jinshangcheng.R;
import com.jinshangcheng.adapter.CarBrandsAdapter;
import com.jinshangcheng.base.BaseActivity;
import com.jinshangcheng.bean.CarBrandsBean;
import com.jinshangcheng.listener.OnItemViewClickListener;
import com.jinshangcheng.widget.IndexBar;
import com.jinshangcheng.widget.ListViewDecoration;
import com.jinshangcheng.widget.TittleBar;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import platform.cston.httplib.bean.CarBrandResult;
import platform.cston.httplib.search.CarBrandInfoSearch;
import platform.cston.httplib.search.OnResultListener;

/**
 * 选择车辆
 */
public class SelectCarActivity extends BaseActivity {

    @BindView(R.id.tittleBar)
    TittleBar tittleBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.indexBar)
    IndexBar indexBar;

    private List<CarBrandsBean> brandsList = new ArrayList<>();
    private CarBrandsAdapter adapter;


    @Override
    public int setContentViewResource() {
        return R.layout.activity_select_car;
    }

    @Override
    public void initData() {
        //获取汽车品牌列表
        brandsList.clear();

    }

    @Override
    public void initView() {
        tittleBar.setTittle("选择车辆");
        final LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        indexBar.setOnLetterChangeListener(new IndexBar.OnLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                for (int i = 0; i < brandsList.size(); i++) {
                    if (TextUtils.equals(brandsList.get(i).nameIndex, letter)) {
                        int firstItem = manager.findFirstVisibleItemPosition();
                        int lastItem = manager.findLastVisibleItemPosition();
                        if (i <= firstItem) {
                            recyclerView.scrollToPosition(i);
                        } else if (i <= lastItem) {
                            int top = recyclerView.getChildAt(i - firstItem).getTop();
                            recyclerView.scrollBy(0, top);
                        } else {
                            recyclerView.scrollToPosition(i);
                        }

                        break;
                    }
                }

            }

            @Override
            public void onReset() {

            }
        });

        adapter = new CarBrandsAdapter(brandsList, this);
        adapter.setOnItemViewClickListener(new OnItemViewClickListener() {
            @Override
            public void onViewClick(int position, View view) {
                Logger.w("item" + brandsList.get(position));
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new ListViewDecoration());//分割线
        recyclerView.setAdapter(adapter);

        geCarBrandsList();//请求数据

    }

    private void geCarBrandsList() {
        showLoading();
        CarBrandInfoSearch.getInstance().GetCarBrandResult(new OnResultListener.CarBrandResultListener() {
            @Override
            public void onCarBrandResult(CarBrandResult carBrandResult, boolean b, Throwable throwable) {
                if (!b) {//请求成功 处理数据源
                    CarBrandsBean carBrandsBean;
                    for (CarBrandResult.DataEntity groupBean : carBrandResult.getData()) {
                        for (CarBrandResult.DataEntity.CarBrandsEntity itemBean :
                                groupBean.getCarBrands()) {
                            carBrandsBean = new CarBrandsBean(itemBean.picturePath, itemBean.brandName, itemBean.brandId, groupBean.nameIndex);
                            brandsList.add(carBrandsBean);
                        }
                    }
                    adapter.refreshList(brandsList);
                    SelectCarActivity.this.dismissLoading();
                }
            }
        });
    }

}
