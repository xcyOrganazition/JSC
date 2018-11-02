package cn.com.jinshangcheng.net;


import java.util.List;

import cn.com.jinshangcheng.bean.Address;
import cn.com.jinshangcheng.bean.Goods;
import cn.com.jinshangcheng.bean.LoginBean;

import io.reactivex.Observable;
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


}
