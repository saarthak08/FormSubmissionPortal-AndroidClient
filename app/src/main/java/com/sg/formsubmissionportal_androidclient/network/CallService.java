package com.sg.formsubmissionportal_androidclient.network;

import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CallService {
    @POST("api/authenticate")
    Call<JsonObject> login(
            @Body JsonObject body);


    @POST("data")
    Call<ResponseBody> dataPost(
            @Body JsonObject body);

}
