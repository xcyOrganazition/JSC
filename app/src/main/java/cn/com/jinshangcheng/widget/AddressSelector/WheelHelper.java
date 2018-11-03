package cn.com.jinshangcheng.widget.AddressSelector;

import android.content.res.AssetManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;

public class WheelHelper {

    /**
     * 所有省
     */
    public String[] mProvinceDatas;
    /**
     * 省市
     */
    public List<ProvinceModel> mProvinceList = new ArrayList<ProvinceModel>();
    public List<CityModel> mCityList = new ArrayList<CityModel>();
    ;
    /**
     * key - 省 value - 市
     */
    public Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    public Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    public String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    public String mCurrentCityName;
    /**
     * 当前区的名称
     */
    public String mCurrentDistrictName = "";

    /**
     * 当前区的邮政编码
     */
    public String mCurrentZipCode = "";
    public WheelView mViewProvince;
    public WheelView mViewCity;
    public WheelView mViewDistrict;
    public Button mBtnConfirm;
    private BaseActivity activity;
    public LinearLayout mWheelArea;
    private View view;
    private Map<String, Object> map;
    private String province;
    private String city;
    private String county;
    private List<CityModel> cityList;
    private List<DistrictModel> districtList;
    private List<ProvinceModel> provinceList;

    public WheelHelper(BaseActivity activity, View view) {
        super();
        this.activity = activity;
        this.view = view;

        setUpViews();
        setUpData();
    }

    private void setUpViews() {
        mWheelArea = (LinearLayout) view.findViewById(R.id.ll_wheel_area);
        mViewProvince = (WheelView) view.findViewById(R.id.id_province_wheel);
        mViewCity = (WheelView) view.findViewById(R.id.id_city_wheel);
        mViewDistrict = (WheelView) view.findViewById(R.id.id_district_wheel);

    }

    private void setUpData() {
        initProvinceDatas();
        if (mWheelArea != null) {
            mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(activity, mProvinceDatas));
            // 设置可见条目数量
            mViewProvince.setVisibleItems(7);
            mViewCity.setVisibleItems(7);
            mViewDistrict.setVisibleItems(7);
            mViewProvince.setBackgroundColor(activity.getResources().getColor(R.color.white));
            mViewCity.setBackgroundColor(activity.getResources().getColor(R.color.white));
            mViewDistrict.setBackgroundColor(activity.getResources().getColor(R.color.white));
            updateCities();
            // updateAreas();
        }
    }

    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(activity, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        System.out.println(mCitisDatasMap.get(mCurrentProviceName));
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);
        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(activity, areas));
        mViewDistrict.setCurrentItem(0);
        mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
        mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
    }

    /**
     * 向服务器上传的编号
     *
     * @return
     */
    public String[] getCode() {
        String str[] = new String[3];
        for (ProvinceModel model : mProvinceList) {
            if (model.getName().equals(mCurrentProviceName)) {
                str[0] = model.getId();
                break;
            }
        }
        for (CityModel model : mCityList) {
            if (model.getName().equals(mCurrentCityName)) {
                str[1] = model.getId();
                break;
            }
        }
        str[2] = mCurrentZipCode;
        return str;
    }

    /**
     * 根据编号查找省市区
     */
    public String[] getAddress(String divisionF, String divisionS, String divisionT) {
        System.out.println(divisionF + ";" + divisionS + ";" + divisionT);
        String str[] = new String[3];
        for (int i = 0; i < mProvinceList.size(); i++) {
            if (mProvinceList.get(i).getId().equals(divisionF)) {
                str[0] = mProvinceList.get(i).getName();
                List<CityModel> cityList = mProvinceList.get(i).getCityList();
                for (int j = 0; j < cityList.size(); j++) {
                    if (cityList.get(j).getId().equals(divisionS)) {
                        str[1] = cityList.get(j).getName();
                        List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                        for (int m = 0; m < districtList.size(); m++) {
                            if (districtList.get(m).getId().equals(divisionT)) {
                                str[2] = districtList.get(m).getName();
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < str.length; i++) {
            if (str[i] == null) {
                str[i] = "";
            }
        }
        return str;

    }

    /**
     * 设置默认的省市县
     *
     * @param address
     */
    public void setDefaultAddress(String address) {
        if (address.contains(" ") && !TextUtils.isEmpty(address)) {
            String[] addr = address.split(" ");
            if (addr.length <= 2) {
                return;
            }
            System.out.println(addr.length + ":" + addr[0] + "-----" + addr[1] + "-----" + addr[2]);
            province = addr[0];
            city = addr[1];
            county = addr[2];
            if (provinceList != null && !provinceList.isEmpty()) {
                for (int i = 0; i < provinceList.size(); i++) {
                    if (province != null && province.equals(provinceList.get(i).getName())) {
                        mCurrentProviceName = provinceList.get(i).getName();
                        cityList = provinceList.get(i).getCityList();
                        mViewProvince.setCurrentItem(i);
                        System.out.println("province=" + province + ";mCurrentProviceName=" + mCurrentProviceName);
                        break;
                    } else {
                        mCurrentProviceName = provinceList.get(0).getName();
                        cityList = provinceList.get(0).getCityList();
                    }
                }
            }
            System.out.println("province=" + province + ";mCurrentProviceName=" + mCurrentProviceName);

            if (cityList != null && !cityList.isEmpty()) {
                for (int i = 0; i < cityList.size(); i++) {
                    if (city != null && city.equals(cityList.get(i).getName())) {
                        mCurrentCityName = cityList.get(i).getName();
                        districtList = cityList.get(i).getDistrictList();
                        mViewCity.setCurrentItem(i);
                        System.out.println("city=" + city + ";mCurrentCityName=" + mCurrentCityName);
                        break;
                    } else {
                        mCurrentCityName = cityList.get(0).getName();
                        districtList = cityList.get(0).getDistrictList();
                    }
                }
            }
            System.out.println("city=" + city + ";mCurrentCityName=" + mCurrentCityName);

            if (districtList != null && !districtList.isEmpty()) {
                for (int i = 0; i < districtList.size(); i++) {
                    if (county != null && county.equals(districtList.get(i).getName())) {
                        mCurrentDistrictName = districtList.get(i).getName();
                        mViewDistrict.setCurrentItem(i);
                        System.out.println("county=" + county + ";mCurrentDistrictName=" + mCurrentDistrictName);
                        break;
                    } else {
                        mCurrentDistrictName = districtList.get(0).getName();
                    }

                }
            }
            System.out.println("county=" + county + ";mCurrentDistrictName=" + mCurrentDistrictName);
        }

    }

    public void showSelectedResult() {
        Toast.makeText(activity, "当前选中:" + mCurrentProviceName + "," + mCurrentCityName + "," + mCurrentDistrictName + "," + mCurrentZipCode,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * 解析省市区的XML数据
     */

    protected void initProvinceDatas() {
        provinceList = null;
        AssetManager asset = activity.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            handler.setWheelHelper(this);
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            // */ 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                cityList = provinceList.get(0).getCityList();
            }
            if (cityList != null && !cityList.isEmpty()) {
                mCurrentCityName = cityList.get(0).getName();
                districtList = cityList.get(0).getDistrictList();
            }
            if (districtList != null && !districtList.isEmpty()) {
                mCurrentDistrictName = districtList.get(0).getName();
            }
            System.out.println(mCurrentDistrictName);
            mCurrentZipCode = districtList.get(0).getId();
            // */
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getId(), districtList.get(k).getName());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getId());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

}
