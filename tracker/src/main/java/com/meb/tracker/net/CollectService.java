package com.meb.tracker.net;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CollectService {

    @POST("Collect/BatchCollect")
    Call<ResultEntity> collect(@Body List<CollectBody> collectBodyList);
}
