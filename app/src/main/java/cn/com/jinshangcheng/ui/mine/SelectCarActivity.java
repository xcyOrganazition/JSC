package cn.com.jinshangcheng.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.jinshangcheng.MyApplication;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.adapter.CarBrandsAdapter;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.bean.CarBrandsBean;
import cn.com.jinshangcheng.listener.OnItemViewClickListener;
import cn.com.jinshangcheng.net.RetrofitService;
import cn.com.jinshangcheng.widget.IndexBar;
import cn.com.jinshangcheng.widget.ListViewDecoration;
import cn.com.jinshangcheng.widget.SelectCarTypeWindow;
import cn.com.jinshangcheng.widget.TittleBar;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import platform.cston.httplib.bean.CarBrandResult;
import platform.cston.httplib.bean.CarModelResult;
import platform.cston.httplib.bean.CarTypeResult;
import platform.cston.httplib.search.CarBrandInfoSearch;
import platform.cston.httplib.search.OnResultListener;

/**
 * 选择车辆 (车款车型)
 */
public class SelectCarActivity extends BaseActivity {

    @BindView(R.id.tittleBar)
    TittleBar tittleBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.indexBar)
    IndexBar indexBar;
    @BindView(R.id.root)
    LinearLayout root;

    private List<CarBrandsBean> brandsList = new ArrayList<>();//车品牌列表
    private List<CarTypeResult.DataEntity.CarTypesEntity> carTypeList = new ArrayList<>();//汽车类型列表

    private CarBrandsAdapter adapter;
    private SelectCarTypeWindow selectCarTypeWindow;
    public static final int RESULT_CODE = 0x124;
    public CarBrandsBean selectCarBrands;
    public CarTypeResult.DataEntity.CarTypesEntity selectCarType;


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
                            LinearLayoutManager mLayoutManager =
                                    (LinearLayoutManager) recyclerView.getLayoutManager();
                            mLayoutManager.scrollToPositionWithOffset(i, 0);
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
                selectCarBrands = brandsList.get(position);
                getCarTypeDataNew(brandsList.get(position));//点击品牌 获取车型
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new ListViewDecoration());//分割线
        recyclerView.setAdapter(adapter);
        geNewCarBrandsList();//请求数据

    }

    /**
     * 获取车品牌列表 (不再使用)
     */
    @Deprecated
    private void geCarBrandsList() {
        showLoading();
        CarBrandInfoSearch.getInstance().GetCarBrandResult(new OnResultListener.CarBrandResultListener() {
            @Override
            public void onCarBrandResult(CarBrandResult carBrandResult, boolean isError, Throwable throwable) {
                if (!isError) {//请求成功 处理数据源
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

    /**
     * 获取车品牌列表（新）
     */
    private void geNewCarBrandsList() {
        showLoading();
        RetrofitService.getOpenCarAPI()
                .getCarBrands(MyApplication.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CarBrandResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CarBrandResult carBrandResult) {
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

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        dismissLoading();
                    }
                });
    }

    /**
     * 请求车型 父列表
     */
    private void getCarTypeDataNew(final CarBrandsBean carBrandsBean) {
        showLoading();
        carTypeList.clear();
        RetrofitService.getOpenCarAPI()
                .getCarTypes(carBrandsBean.brandId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CarTypeResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CarTypeResult carTypeResult) {
                        Logger.w("车型   " + carTypeResult.getData().get(0).getCarTypes());
                        for (int i = 0; i < carTypeResult.getData().size(); i++) {
                            carTypeList.addAll(carTypeResult.getData().get(i).getCarTypes());
                        }
                        selectCarTypeWindow = new SelectCarTypeWindow(SelectCarActivity.this,
                                carBrandsBean, carTypeList,
                                new SelectCarTypeWindow.OnCarTypeSelectListener() {
                                    @Override
                                    public void onCarTypelSelect(CarTypeResult.DataEntity.CarTypesEntity carType) {
                                        selectCarType = carType;
                                    }
                                },
                                new SelectCarTypeWindow.OnCarModelSelectListener() {
                                    @Override
                                    public void onCarModelSelect(CarModelResult.DataEntity carModel) {
                                        Logger.w("选择的车型 = " + carModel);
                                        selectCarTypeWindow.dismiss();
                                        Intent intent = new Intent();
                                        Bundle bundle = new Bundle();
                                        intent.putExtra("selectCarBrands", selectCarBrands);
                                        bundle.putParcelable("selectCarType", selectCarType);
                                        bundle.putParcelable("selectCarModel", carModel);
                                        intent.putExtras(bundle);
                                        setResult(RESULT_CODE, intent);
                                        finish();
                                    }
                                });
//                    selectCarTypeWindow.showAtLocation(tittleBar, Gravity.RIGHT, 0, 0);
                        selectCarTypeWindow.showAsDropDown(tittleBar, 0, 0, Gravity.RIGHT);
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                    }

                    @Override
                    public void onComplete() {
                        dismissLoading();
                    }
                });
    }

    /**
     * 请求车型 父列表 (不再使用)
     */
    @Deprecated
    private void getCarTypeData(final CarBrandsBean carBrandsBean) {
        showLoading();
        carTypeList.clear();
        CarBrandInfoSearch.getInstance().GetCarTypeResult(carBrandsBean.brandId, new OnResultListener.CarTypeResultListener() {
            @Override
            public void onCarTypeResult(CarTypeResult carTypeResult, boolean isError, Throwable throwable) {
                if (!isError) {
                    dismissLoading();
                    for (int i = 0; i < carTypeResult.getData().size(); i++) {
                        carTypeList.addAll(carTypeResult.getData().get(i).getCarTypes());
                    }
                    selectCarTypeWindow = new SelectCarTypeWindow(SelectCarActivity.this,
                            carBrandsBean, carTypeList,
                            new SelectCarTypeWindow.OnCarTypeSelectListener() {
                                @Override
                                public void onCarTypelSelect(CarTypeResult.DataEntity.CarTypesEntity carType) {
                                    selectCarType = carType;
                                }
                            },
                            new SelectCarTypeWindow.OnCarModelSelectListener() {
                                @Override
                                public void onCarModelSelect(CarModelResult.DataEntity carModel) {
                                    selectCarTypeWindow.dismiss();
                                    Intent intent = new Intent();
                                    Bundle bundle = new Bundle();
                                    intent.putExtra("selectCarBrands", selectCarBrands);
                                    bundle.putParcelable("selectCarType", selectCarType);
                                    bundle.putParcelable("selectCarModel", carModel);
                                    intent.putExtras(bundle);
                                    setResult(RESULT_CODE, intent);
                                    finish();
                                }
                            });
//                    selectCarTypeWindow.showAtLocation(tittleBar, Gravity.RIGHT, 0, 0);
                    selectCarTypeWindow.showAsDropDown(tittleBar, 0, 0, Gravity.RIGHT);
                }
            }
        });
    }

}
