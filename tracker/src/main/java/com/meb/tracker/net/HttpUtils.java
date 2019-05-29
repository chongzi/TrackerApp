package com.meb.tracker.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtils {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://fanyi.youdao.com/") // 设置网络请求的Url地址
            .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
//            .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 支持RxJava平台
            .build();



}
