package cn.com.jinshangcheng.net;


import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinshangcheng.bean.AddCarResult;
import cn.com.jinshangcheng.bean.Address;
import cn.com.jinshangcheng.bean.BankCardBean;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.BaseListBean;
import cn.com.jinshangcheng.bean.CarBean;
import cn.com.jinshangcheng.bean.CarMaintainBean;
import cn.com.jinshangcheng.bean.Goods;
import cn.com.jinshangcheng.bean.GoodsItemBean;
import cn.com.jinshangcheng.bean.IncomeBean;
import cn.com.jinshangcheng.bean.LoginBean;
import cn.com.jinshangcheng.bean.MyCountBean;
import cn.com.jinshangcheng.bean.MyCustomerBean;
import cn.com.jinshangcheng.bean.OrderBean;
import cn.com.jinshangcheng.bean.PositionBean;
import cn.com.jinshangcheng.bean.ReportBean;
import cn.com.jinshangcheng.bean.StealthBean;
import cn.com.jinshangcheng.bean.TravelBean;
import cn.com.jinshangcheng.bean.TravelPointBean;
import cn.com.jinshangcheng.bean.UserBean;
import cn.com.jinshangcheng.bean.ViolationBean;
import cn.com.jinshangcheng.bean.WithdrawBean;
import cn.com.jinshangcheng.bean.mycst.CheckDataBean;
import io.reactivex.Observable;
import retrofit2.Call;
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
     * @return bean
     */
    @FormUrlEncoded
    @POST("/user/registOrLogin")
    Observable<LoginBean> login(@Field("phoneNumber") String phoneNumber);

    /**
     * 获取验证码
     *
     * @param phoneNumber
     * @return bean
     */
    @FormUrlEncoded
    @POST("/user/getVerifyCode")
    Call<JsonObject> getVerifyCode(@Field("phoneNumber") String phoneNumber);

    /**
     * 设置推荐人
     *
     * @param userid
     * @param parentPhoneNum
     * @return bean
     */
    @FormUrlEncoded
    @POST("/user/updateUserParentId")
    Call<JsonObject> upDateParentId(@Field("userid") String userid,
                                    @Field("parentPhoneNum") String parentPhoneNum);

    /**
     * 获取用户信息
     *
     * @param userid
     * @return bean
     */
    @FormUrlEncoded
    @POST("/user/getUserInfo")
    Observable<BaseBean<UserBean>> getUserInfo(@Field("userid") String userid);

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
     * 道路救援验证接口 是否允许打开道路救援
     *
     * @param platenumber
     * @return bean
     */
    @FormUrlEncoded
    @POST("/car/roadHelp")
    Observable<BaseBean> getCanRoadHelp(@Field("platenumber") String platenumber, @Field("userid") String userId);


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
     * @param carregistDate
     * @return bean
     */
    @FormUrlEncoded
    @POST("/car/perfectCarAnnualtriald")
    Observable<BaseBean> confirmAnnual(@Field("carid") String carid,
                                       @Field("userid") String userId,
                                       @Field("annualtrialdeadlineDate") String time,
                                       @Field("carregistDate") String carregistDate);

    /**
     * 完善车年审信息
     *
     * @param carid
     * @param userId
     * @return bean
     */
    @FormUrlEncoded
    @POST("/car/getBreakRule")
    Observable<ViolationBean> getViolation(@Field("carid") String carid,
                                           @Field("userid") String userId);

    /**
     * 获取用车报告 日报告
     *
     * @param carid
     * @param userId
     * @param time
     * @return bean
     */
    @FormUrlEncoded
    @POST("/car/getCarDateReport")
    Observable<BaseBean<ReportBean>> getCarDateReport(@Field("carid") String carid,
                                                      @Field("userid") String userId,
                                                      @Field("startTime") String time);

    /**
     * 获取用车报告 月报告
     *
     * @param carid
     * @param userId
     * @param time
     * @return bean
     */
    @FormUrlEncoded
    @POST("/car/getCarMonthReport")
    Observable<BaseBean<ReportBean>> getCarMonthReport(@Field("carid") String carid,
                                                       @Field("userid") String userId,
                                                       @Field("monthTime") String time);

    /**
     * 获取轨迹列表 日报告
     *
     * @param carid
     * @param userId
     * @return bean
     */
    @FormUrlEncoded
    @POST("/travel/getTravelList")
    Observable<ArrayList<TravelBean>> getDayTravelList(@Field("carid") String carid,
                                                       @Field("userid") String userId,
                                                       @Field("currentPage") int currentPage,
                                                       @Field("pageSize") int pageSize,
                                                       @Field("startDate") String startTime);

    /**
     * 获取轨迹列表 月报告
     *
     * @param carid
     * @param userId
     * @return bean
     */
    @FormUrlEncoded
    @POST("/travel/getTravelListByMonth")
    Observable<ArrayList<TravelBean>> getMonthTravelList(@Field("carid") String carid,
                                                         @Field("userid") String userId,
                                                         @Field("currentPage") int currentPage,
                                                         @Field("pageSize") int pageSize,
                                                         @Field("monthTime") String monthTime);

    /**
     * 删除某条轨迹
     *
     * @param userId
     * @param travelid
     * @return bean
     */
    @FormUrlEncoded
    @POST("/travel/delTheOneOfMyTravel")
    Observable<BaseBean> deleteTravel(@Field("userId") String userId,
                                      @Field("travelid") String travelid);

    /**
     * 获取轨迹点列表
     *
     * @param carid
     * @param userId
     * @param startTime
     * @param stopTime
     * @return
     */
    @FormUrlEncoded
    @POST("/travel/getTCarGpsPointsForOneTravel")
    Observable<ArrayList<TravelPointBean>> getTravelPointList(@Field("carid") String carid,
                                                              @Field("userid") String userId,
                                                              @Field("startTime") long startTime,
                                                              @Field("stopTime") long stopTime);


    /**
     * 获取商品列表
     *
     * @return bean
     */
    @FormUrlEncoded
    @POST("/goods/getGoodsList")
    Observable<List<Goods>> getGoodsList(@Field("userId") String userId);

    /**
     * 添加商品至购物车
     *
     * @param userId
     * @param goodsid
     * @param quantity
     * @return 购物车中所有商品Item
     */
    @FormUrlEncoded
    @POST("/cart/addCart")
    Observable<List<GoodsItemBean>> addGoodsToCart(@Field("userId") String userId,
                                                   @Field("goodsid") String goodsid,
                                                   @Field("quantity") int quantity);
    /**
     * 创建订单
     *
     * @param userId
     * @param cartitemids 产品条目id集合， 用,隔开
     * @param addressid 地址id
     * @return
     */
    @FormUrlEncoded
    @POST("/order/createOrder")
    Observable<List<Goods>> createOrder(@Field("userId") String userId,
                                        @Field("cartitemids") String cartitemids,
                                        @Field("addressid") int addressid);

    /**
     * 查看我的订单(分页显示)
     *
     * @return bean
     */
    @FormUrlEncoded
    @POST("/order/getMyOrders")
    Observable<BaseListBean<OrderBean>> getMyOrders(@Field("userid") String userId,
                                                    @Field("currentPage") int currentPage,
                                                    @Field("pageSize") int pageSize);

    /**
     * 获取地址列表
     *
     * @param userid
     * @return bean
     */
    @FormUrlEncoded
    @POST("/address/getAddressList")
    Observable<ArrayList<Address>> getAddressList(@Field("userid") String userid);

    /**
     * 获取默认地址
     *
     * @return bean
     */
    @FormUrlEncoded
    @POST("/address/getDefaultAddress")
    Observable<Address> getDefaultAddress(@Field("userid") String userId);

    /**
     * 设置默认地址
     *
     * @return bean
     */
    @FormUrlEncoded
    @POST("/address/setDefaultAddress")
    Observable<BaseBean> setDefaultAddress(@Field("userid") String userId,
                                           @Field("addressid") String addressid);

    /**
     * 添加地址
     *
     * @param userId
     * @param receiver
     * @param phonenumber
     * @param city
     * @param detailaddress
     * @param isdefault     0默认 1非默认
     * @return
     */
    @FormUrlEncoded
    @POST("/address/addAddress")
    Observable<BaseBean<Address>> addAddress(@Field("userid") String userId,
                                             @Field("receiver") String receiver,
                                             @Field("phonenumber") String phonenumber,
                                             @Field("city") String city,
                                             @Field("detailaddress") String detailaddress,
                                             @Field("isdefault") int isdefault);

    /**
     * 修改地址
     *
     * @param userId
     * @param addressId
     * @param receiver
     * @param phonenumber
     * @param city
     * @param detailaddress
     * @param isDefault     0默认 1非默认
     * @return
     */
    @FormUrlEncoded
    @POST("/address/updateAddress")
    Observable<BaseBean> updateAddress(@Field("userid") String userId,
                                       @Field("addressid") String addressId,
                                       @Field("receiver") String receiver,
                                       @Field("phonenumber") String phonenumber,
                                       @Field("city") String city,
                                       @Field("detailaddress") String detailaddress,
                                       @Field("isdefault") int isDefault);

    /**
     * 删除地址
     *
     * @param userId
     * @param addressId
     * @return
     */
    @FormUrlEncoded
    @POST("/address/delAddress")
    Observable<BaseBean> delAddress(@Field("userid") String userId,
                                    @Field("addressid") String addressId);

    /**
     * 获取我的客户数，包括客户总数(teamnum)、直推客户(straightpushnum)、其它客户(othernum)
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("/user/getMyCount")
    Observable<MyCountBean> getMyCount(@Field("userid") String userId);

    /**
     * 获取我的客户列表
     *
     * @param userId
     * @param currentPage 当前页
     * @param pageSize    每页有多少条数据
     * @return
     */
    @FormUrlEncoded
    @POST("/user/getMySonUsers")
    Observable<BaseListBean<MyCustomerBean>> getMyCustomerList(@Field("userid") String userId,
                                                               @Field("currentPage") int currentPage,
                                                               @Field("pageSize") int pageSize);

    /**
     * 获取用户账户统计信息，包括总收益、余额和已提现
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("/AccountdetailedController/getDetailCount")
    Observable<IncomeBean> getDetailCount(@Field("userid") String userId);

    /**
     * 获取户账户详细信息
     *
     * @param userId
     * @param currentPage 当前页
     * @param pageSize    每页有多少条数据
     * @return
     */
    @FormUrlEncoded
    @POST("/AccountdetailedController/getDetailData")
    Observable<BaseListBean<WithdrawBean>> getBillList(@Field("userid") String userId,
                                                       @Field("currentPage") int currentPage,
                                                       @Field("pageSize") int pageSize);

    /**
     * 获取提现记录列表
     *
     * @param userId
     * @param currentPage 当前页
     * @param pageSize    每页有多少条数据
     * @return
     */
    @FormUrlEncoded
    @POST("/AccountdetailedController/myWithdrawDetaile")
    Observable<BaseListBean<WithdrawBean>> getWithdrawList(@Field("userid") String userId,
                                                           @Field("currentPage") int currentPage,
                                                           @Field("pageSize") int pageSize);


    /**
     * 获取用户可提现金额
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("/AccountdetailedController/getCanWithdraw")
    Call<JsonObject> geCanWithdraw(@Field("userid") String userId);

    /**
     * 申请提现
     *
     * @param userId
     * @param accountbank
     * @param accountuser
     * @param accountnum
     * @param dealbalance
     * @return
     */
    @FormUrlEncoded
    @POST("/AccountdetailedController/drawMyMoney")
    Observable<BaseBean> drawMyMoney(@Field("userid") String userId,
                                     @Field("accountbank") String accountbank,
                                     @Field("accountuser") String accountuser,
                                     @Field("accountnum") String accountnum,
                                     @Field("dealbalance") String dealbalance);

    /**
     * 获取我的银行卡列表
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("/account/getAccountList")
    Observable<ArrayList<BankCardBean>> getCardList(@Field("userid") String userId);

    /**
     * 添加银行卡
     *
     * @param userId
     * @param accountNum
     * @param accountUser
     * @param accountBank
     * @return
     */
    @FormUrlEncoded
    @POST("/account/addAccount")
    Observable<BaseBean<BankCardBean>> addBankCard(@Field("userid") String userId,
                                                   @Field("accountnum") String accountNum,
                                                   @Field("accountuser") String accountUser,
                                                   @Field("accountbank") String accountBank,
                                                   @Field("isdefault") int isDefault);

    /**
     * 添加银行卡
     *
     * @param userId
     * @param accountNum
     * @param accountUser
     * @param accountBank
     * @return
     */
    @FormUrlEncoded
    @POST("/account/updateAccountById")
    Observable<BaseBean> updateCard(@Field("userid") String userId,
                                    @Field("accountid") String accountid,
                                    @Field("accountnum") String accountNum,
                                    @Field("accountuser") String accountUser,
                                    @Field("accountbank") String accountBank,
                                    @Field("isdefault") int isDefault);

    /**
     * 删除银行卡
     *
     * @param userId
     * @param accountid
     * @return
     */
    @FormUrlEncoded
    @POST("/account/delAccount")
    Observable<BaseBean> delCard(@Field("userid") String userId,
                                 @Field("accountid") String accountid);

    /**
     * 设置某张银行卡为默认卡
     *
     * @param userId
     * @param accountid
     * @return
     */
    @FormUrlEncoded
    @POST("/account/setDefaultAccount")
    Observable<BaseBean> setCardDefault(@Field("userid") String userId,
                                        @Field("accountid") String accountid);

    /**
     * 获取隐身数据
     *
     * @param userId
     * @param carid
     * @return
     */
    @FormUrlEncoded
    @POST("/car/invisibleinfo")
    Observable<BaseBean<StealthBean>> getStealthData(@Field("userid") String userId,
                                                     @Field("carid") String carid);

    /**
     * 开启隐身
     *
     * @param userId
     * @param carid
     * @param days
     * @param hours
     * @return
     */
    @FormUrlEncoded
    @POST("/car/begininvisible")
    Observable<BaseBean> beginStealth(@Field("userid") String userId,
                                      @Field("carid") String carid,
                                      @Field("days") String days,
                                      @Field("hours") String hours);

    /**
     * 关闭隐身
     *
     * @param userId
     * @param carid
     * @param invisibleid
     * @return
     */
    @FormUrlEncoded
    @POST("/car/stopinvisible")
    Observable<BaseBean> stopStealth(@Field("userid") String userId,
                                     @Field("carid") String carid,
                                     @Field("invisibleid") String invisibleid);

    /**
     * 设置轨迹保护
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("/travel/protectAllMyTravel")
    Observable<BaseBean> startProtect(@Field("userid") String userId);

    /**
     * 关闭轨迹保护
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("/travel/releaseAllMyTravel")
    Observable<BaseBean> stopProtect(@Field("userid") String userId);

    /**
     * 添加车辆
     *
     * @param userId
     * @param cartype           车型(小型车0、大型车1)
     * @param platenumber       车牌号
     * @param vin               车架码
     * @param ein               发动机号码
     * @param brandname         品牌名称
     * @param brandpath         品牌图片
     * @param typename          车型名称
     * @param typepath          车型图片
     * @param modelId           品牌图片
     * @param model             车款名称
     * @param modelpath         车款图片
     * @param gasno             油号 92,95,98,0.
     * @param carregistdate     注册时间
     * @param totalmileage      行驶里程
     * @param insurancedeadline 保险日期
     * @param emergencyphonenum 紧急联系人电话
     * @return
     */
    @FormUrlEncoded
    @POST("/car/addCar")
    Observable<AddCarResult> addCar(@Field("userid") String userId,
                                    @Field("cartype") int cartype,
                                    @Field("platenumber") String platenumber,
                                    @Field("vin") String vin,
                                    @Field("ein") String ein,
                                    @Field("brandname") String brandname,
                                    @Field("brandpath") String brandpath,
                                    @Field("typename") String typename,
                                    @Field("typepath") String typepath,
                                    @Field("modelId") String modelId,
                                    @Field("model") String model,
                                    @Field("modelpath") String modelpath,
                                    @Field("gasno") int gasno,
                                    @Field("carRegistdate") String carregistdate,
                                    @Field("totalmileage") String totalmileage,
                                    @Field("insurancedeadlineDate") String insurancedeadline,
                                    @Field("emergencyphonenum") String emergencyphonenum
    );

    /**
     * 修改车辆
     *
     * @param userId
     * @param carid             要修改的车辆Id
     * @param cartype           车型(小型车0、大型车1)
     * @param platenumber       车牌号
     * @param vin               车架码
     * @param ein               发动机号码
     * @param brandname         品牌名称
     * @param brandpath         品牌图片
     * @param typename          车型名称
     * @param typepath          车型图片
     * @param modelId           品牌图片
     * @param model             车款名称
     * @param modelpath         车款图片
     * @param gasno             油号 92,95,98,0.
     * @param carregistdate     注册时间
     * @param totalmileage      行驶里程
     * @param insurancedeadline 保险日期
     * @param emergencyphonenum 紧急联系人电话
     * @return
     */
    @FormUrlEncoded
    @POST("/car/updateCarInfo")
    Observable<AddCarResult> updateCar(@Field("userid") String userId,
                                       @Field("carid") String carid,
                                       @Field("cartype") int cartype,
                                       @Field("platenumber") String platenumber,
                                       @Field("vin") String vin,
                                       @Field("ein") String ein,
                                       @Field("brandname") String brandname,
                                       @Field("brandpath") String brandpath,
                                       @Field("typename") String typename,
                                       @Field("typepath") String typepath,
                                       @Field("modelId") String modelId,
                                       @Field("model") String model,
                                       @Field("modelpath") String modelpath,
                                       @Field("gasno") int gasno,
                                       @Field("carRegistdate") String carregistdate,
                                       @Field("totalmileage") String totalmileage,
                                       @Field("insurancedeadlineDate") String insurancedeadline,
                                       @Field("emergencyphonenum") String emergencyphonenum
    );

    /**
     * 删除车辆
     *
     * @param userId
     * @param carid
     * @return
     */
    @FormUrlEncoded
    @POST("/car/deleteCar")
    Observable<BaseBean> deleteCar(@Field("userid") String userId,
                                   @Field("carid") String carid);

    /**
     * 绑定盒子
     *
     * @param userId
     * @param carid
     * @param din    驾图盒子唯一识别码（车机号）
     * @param sn     车机序列号
     * @return
     */
    @FormUrlEncoded
    @POST("/box/addBox")
    Observable<BaseBean> bindBox(@Field("userid") String userId,
                                 @Field("carid") String carid,
                                 @Field("din") String din,
                                 @Field("sn") String sn);

    /**
     * 解绑盒子
     *
     * @param userId
     * @param carid
     * @param din    驾图盒子唯一识别码（车机号）
     * @param sn     车机序列号
     * @return
     */
    @FormUrlEncoded
    @POST("/box/unbindBox")
    Observable<BaseBean> unbindBox(@Field("userid") String userId,
                                   @Field("carid") String carid,
                                   @Field("din") String din,
                                   @Field("sn") String sn);

}

