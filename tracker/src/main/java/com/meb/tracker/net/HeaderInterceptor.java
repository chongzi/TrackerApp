package com.meb.tracker.net;

import android.content.Context;
import android.content.SharedPreferences;

import com.meb.tracker.utils.SignUtils;

import java.io.IOException;
import java.util.TreeMap;
import java.util.UUID;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by cn on 2019/3/21.
 * Func:
 */
public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        //得到原始的请求对象
        Request request = chain.request();
        //得到用户所使用的请求方式
        String method = request.method();

        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonce = UUID.randomUUID().toString().replaceAll("-", "");
        //TreeMap里面的数据会按照key值自动升序排列
        TreeMap<String, String> param_map = new TreeMap<String, String>();
        param_map.put("appid", SignUtils._appId);
        param_map.put("timestamp", timestamp);
        param_map.put("nonce", nonce);

        if ("GET".equals(method)) {
            //url
            //原始的请求接口
//            String url = request.url().toString();
//            //获取参数列表
//            String[] parts = url.split("\\?");
//            //获取参数对
//            if (parts.length >= 2) {
//                String[] param_pairs = parts[1].split("&");
//                for (String pair : param_pairs) {
//                    String[] param = pair.split("=");
//                    if (param.length != 2) {
//                        //没有value的参数不进行处理
//                        continue;
//                    }
//                    param_map.put(param[0], URLDecoder.decode(param[1], "UTF-8"));
//                }
//            }

//            for (String key : param_map.keySet()) {//keySet获取map集合key的集合  然后在遍历key即可
//                String value = param_map.get(key);//
//                System.out.println("key:" + key + " vlaue:" + value);
//            }

            HttpUrl url = request.url();
            int size = url.querySize();
            for (int i = 0; i < size; i++) {
                String key = url.queryParameterName(i);
                String value = url.queryParameterValue(i);
                param_map.put(key, value);
            }

            //重新构建请求体
            request = new Request.Builder()
                    .url(url)
                    .addHeader("appid", SignUtils._appId)
                    .addHeader("timestamp", timestamp)
                    .addHeader("nonce", nonce)
                    .addHeader("DevicePlatform", "android")
                    .addHeader("sigture", SignUtils.getSigture(param_map))
                    .build();
        } else if ("POST".equals(method)) {
            //得到原始的url
            String url = request.url().toString();

//            if (request.body() instanceof FormBody) {
//                //得到原有的请求参数
//                FormBody body = (FormBody) request.body();//1 2 3
//                //TreeMap里面的数据会按照key值自动升序排列
//                TreeMap<String, String> param_map = new TreeMap<String, String>();
//                if (body != null) {
//                    for (int i = 0; i < body.size(); ++i) {
//                        //取出相关请求参数(原有的)
//                        String name = body.name(i);
//                        String value = body.value(i);
//                        param_map.put(name, value);
//                    }
//                }
//
//                for (String key : param_map.keySet()) {//keySet获取map集合key的集合  然后在遍历key即可
//                    String value = param_map.get(key);//
//                    System.out.println("key:" + key + " vlaue:" + value);
//                }
//
//            }

            request = new Request.Builder()
                    .url(url)
                    .post(request.body())
                    .addHeader("appid", SignUtils._appId)
                    .addHeader("timestamp", timestamp)
                    .addHeader("nonce", nonce)
                    .addHeader("DevicePlatform", "android")
                    .addHeader("sigture", SignUtils.getSigture(param_map))
                    .build();

        }
        //重新发送请求
        return chain.proceed(request);
    }
}
