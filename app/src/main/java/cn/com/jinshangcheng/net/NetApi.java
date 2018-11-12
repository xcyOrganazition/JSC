package cn.com.jinshangcheng.net;


import java.util.ArrayList;
import java.util.List;

import cn.com.jinshangcheng.bean.Address;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.CarBean;
import cn.com.jinshangcheng.bean.CarMaintainBean;
import cn.com.jinshangcheng.bean.Goods;
import cn.com.jinshangcheng.bean.LoginBean;
import cn.com.jinshangcheng.bean.PositionBean;
import cn.com.jinshangcheng.bean.mycst.CheckDataBean;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * 自定义接口 提供各种请求方法
 */
public interface NetApi {

    /**
     * 登录
     *
     * @param phoneNumber
     * @param verifyCode
     * @return bean
     */
    @FormUrlEncoded
    @POST("/user/registOrLogin")
    Observable<LoginBean> login(@Field("phoneNumber") String phoneNumber,
                                @Field("verifyCode") String verifyCode);

    /**
     * 获取车辆列表
     *
     * @param userid
     * @return bean
     */
    @FormUrlEncoded
    @POST("/car/getCarList")
    Observable<BaseBean<ArrayList<CarBean>>> getCarList(@Field("userid") String userid);

    /**
     * 获取车辆位置信息
     *
     * @param userid
     * @param carid
     * @return bean
     */
    @FormUrlEncoded
    @POST("/car/location")
    Observable<BaseBean<PositionBean>> getCarPosition(@Field("userid") String userid,
                                                      @Field("carid") String carid);

    /**
     * 获取车的保养保险年审信息
     *
     * @param userid
     * @param carid
     * @return bean
     */
    @FormUrlEncoded
    @POST("/car/getCarMaintainInfo")
    Observable<BaseBean<CarMaintainBean>> getCarMaintainInfo(@Field("userid") String userid,
                                                             @Field("carid") String carid);

    /**
     * 一键检测数据
     *
     * @param carid 车辆Id
     * @return bean
     */
    @FormUrlEncoded
    @POST("/car/getCheckReport")
    Observable<BaseBean<CheckDataBean>> getCheckReport(@Field("carid") String carid, @Field("userid") String userId);

    /**
     * 完善车保险信息
     *
     * @param carid
     * @param userId
     * @param time
     * @return bean
     */
    @FormUrlEncoded
    @POST("/car/perfectCarInsurance")
    Observable<BaseBean> confirmInsurance(@Field("carid") String carid,
                                          @Field("userid") String userId,
                                          @Field("insurancedeadline") String time);

    /**
     * 完善保养信息
     *
     * @param carid
     * @param userId
     * @param lastmaintainmileage 最近保养里程
     * @param maintenanceinterval 保养间隔
     * @param lastMaintainTime    最近保养时间
     * @param totalmileage        行驶总里程
     * @return bean
     */
    @FormUrlEncoded
    @POST("/car/perfectCarMaintain")
    Observable<BaseBean> confirmMaintain(@Field("carid") String carid,
                                         @Field("userid") String userId,
                                         @Field("lastmaintainmileage") String lastmaintainmileage,
                                         @Field("maintenanceinterval") String maintenanceinterval,
                                         @Field("lastMaintainTime") String lastMaintainTime,
                                         @Field("totalmileage") String totalmileage);

    /**
     * 完善车年审信息
     *
     * @param carid
     * @param userId
     * @param time
     * @return bean
     */
    @FormUrlEncoded
    @POST("/car/perfectCarAnnualtriald")
    Observable<BaseBean> confirmAnnual(@Field("carid") String carid,
                                       @Field("userid") String userId,
                                       @Field("annualtrialdeadlineDate") String time);

    /**
     * 获取商品列表
     *
     * @return bean
     */
    @FormUrlEncoded
    @POST("/goods/getGoodsList")
    Observable<List<Goods>> getGoodsList(@Field("userId") String userId);

    /**
     * 获取默认地址
     *
     * @return bean
     */
    @FormUrlEncoded
    @POST("/address/getDefaultAddress")
    Observable<Address> getDefaultAddress(@Field("userid") String userId);

    /**
     * 添加地址
     *
     * @return bean
     */
    @POST("/address/addAddress")
    Observable<BaseBean<Address>> addAddress(@Body Address address);


}
