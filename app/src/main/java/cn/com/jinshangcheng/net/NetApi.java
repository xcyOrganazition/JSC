package cn.com.jinshangcheng.net;


import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinshangcheng.bean.Address;
import cn.com.jinshangcheng.bean.BankCardBean;
import cn.com.jinshangcheng.bean.BaseBean;
import cn.com.jinshangcheng.bean.BaseListBean;
import cn.com.jinshangcheng.bean.CarBean;
import cn.com.jinshangcheng.bean.CarMaintainBean;
import cn.com.jinshangcheng.bean.Goods;
import cn.com.jinshangcheng.bean.IncomeBean;
import cn.com.jinshangcheng.bean.LoginBean;
import cn.com.jinshangcheng.bean.MyCountBean;
import cn.com.jinshangcheng.bean.MyCustomerBean;
import cn.com.jinshangcheng.bean.PositionBean;
import cn.com.jinshangcheng.bean.ReportBean;
import cn.com.jinshangcheng.bean.TravelBean;
import cn.com.jinshangcheng.bean.UserBean;
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
     * @param verifyCode
     * @return bean
     */
    @FormUrlEncoded
    @POST("/user/registOrLogin")
    Observable<LoginBean> login(@Field("phoneNumber") String phoneNumber,
                                @Field("verifyCode") String verifyCode);

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
                                                       @Field("startTime") String startTime,
                                                       @Field("stopTime") String stopTime);

    /**
     * 获取轨迹列表 日报告
     *
     * @param carid
     * @param userId
     * @return bean
     */
    @FormUrlEncoded
    @POST("/travel/getTravelListByMonth")
    Observable<ArrayList<TravelBean>> getMonthTravelList(@Field("carid") String carid,
                                                         @Field("userid") String userId,
//                                                         @Field("currentPage") int currentPage,
//                                                         @Field("pageSize") int pageSize,
                                                         @Field("monthTime") String monthTime);


    /**
     * 获取商品列表
     *
     * @return bean
     */
    @FormUrlEncoded
    @POST("/goods/getGoodsList")
    Observable<List<Goods>> getGoodsList(@Field("userId") String userId);

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

}

