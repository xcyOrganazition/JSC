package cn.com.jinshangcheng.net;


import java.util.concurrent.TimeUnit;

import cn.com.jinshangcheng.config.ConstParams;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xu on 2018/11/16.
 */
public class RetrofitServiceForWeiPay {

    //    public static final String HOST = "http://jiekou.jinshangcheng.com.cn:80";//测试环境
    public static final String HOST = "http://weixin.jinshangcheng.com.cn";//正式环境

    private static final String BASE_URL = HOST + "";

    private static NetApi netApi;
    private static GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor()//
                    .setLevel(HttpLoggingInterceptor.Level.BASIC))//
            .addNetworkInterceptor(new LogInterceptor())//
            .writeTimeout(ConstParams.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(ConstParams.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(ConstParams.READ_TIMEOUT, TimeUnit.SECONDS)
            .build();


    public static NetApi getRetrofit() {
        if (netApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)//
                    .addConverterFactory(gsonConverterFactory)//
                    .client(client)//
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)//
                    .build();
            netApi = retrofit.create(NetApi.class);
        }
        return netApi;
    }
}