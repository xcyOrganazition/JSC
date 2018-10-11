package com.jinshangcheng.net;


import com.jinshangcheng.bean.LoginBean;

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
     * @param workerCode
     * @param password
     * @return bean
     */
    @FormUrlEncoded
    @POST("login")
    Observable<LoginBean> login(@Field("workerCode") String workerCode,
                                @Field("password") String password);


}
