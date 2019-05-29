package com.meb.tracker;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meb.tracker.net.CollectBody;
import com.meb.tracker.net.CollectService;
import com.meb.tracker.net.HeaderInterceptor;
import com.meb.tracker.net.ResultEntity;
import com.meb.tracker.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrackerManager {

    public static TrackerManager trackerManager;

    private Timer timer;
    private long period = 1 * 1000 * 60;
    private Retrofit retrofit;
    private boolean isUploading;//正在上传
    private List<CollectBody> collectBodyList = new ArrayList<>();
    private List<CollectBody> collectBodyListBuffer = new ArrayList<>();

    public static synchronized TrackerManager getInstance() {
        if (trackerManager == null) {
            trackerManager = new TrackerManager();
        }
        return trackerManager;
    }

    public void init() {
        initHistoryLog();
        initNet();
        initTimer();
    }

    private void initHistoryLog() {
        String json = SharedPreferencesUtils.getString(SharedPreferencesUtils.Key.SP_KEY_HISTROY_LOG, "");
        if (!TextUtils.isEmpty(json)) {
            Log.i("Tracker:历史日志", json);
            List<CollectBody> list = new Gson().fromJson(json, new TypeToken<List<CollectBody>>() {
            }.getType());
            if (list != null) {
                collectBodyList.addAll(list);
            }
        }
    }

    private void initTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                uploadLog();
            }
        }, 0, period);

    }

    private void initNet() {
        OkHttpClient client = new OkHttpClient.Builder()
//                .addNetworkInterceptor(httpLoggingInterceptor)
                .addInterceptor(new HeaderInterceptor())
//                .addNetworkInterceptor(new StethoInterceptor())
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.TRACKER_BASE_API) // 设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
//            .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 支持RxJava平台
                .client(client)
                .build();
    }

    private void uploadLog() {
        if (collectBodyList.size() == 0) return;
        isUploading = true;
        retrofit.create(CollectService.class)
                .collect(collectBodyList)
                .enqueue(new Callback<ResultEntity>() {
                    @Override
                    public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
                        ResultEntity resultEntity = response.body();
                        if (resultEntity != null && resultEntity.isSuccess()) {
                            Log.i("Tracker:事件上传成功", resultEntity.toString());
                            collectBodyList.clear();
                            SharedPreferencesUtils.setString(SharedPreferencesUtils.Key.SP_KEY_HISTROY_LOG, "");
                        }
                        isUploading = false;
                        synBuffer();
                    }

                    @Override
                    public void onFailure(Call<ResultEntity> call, Throwable t) {
                        Log.i("Tracker:事件上传失败", t.toString());
                        isUploading = false;
                    }
                });
    }

    private void synBuffer() {
        collectBodyList.addAll(collectBodyListBuffer);
        collectBodyListBuffer.clear();
        SharedPreferencesUtils.setString(SharedPreferencesUtils.Key.SP_KEY_HISTROY_LOG, new Gson().toJson(collectBodyList));
    }


    public void addCollect(CollectBody collectBody) {
        if (isUploading) {
            collectBodyListBuffer.add(collectBody);
            return;
        }
        collectBodyList.add(collectBody);
        SharedPreferencesUtils.setString(SharedPreferencesUtils.Key.SP_KEY_HISTROY_LOG, new Gson().toJson(collectBodyList));
    }
}
