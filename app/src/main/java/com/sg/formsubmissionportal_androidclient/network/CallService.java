package com.sg.formsubmissionportal_androidclient.network;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CallService {
    @POST("register")
    Call<ResponseBody> userPost(
            @Body JsonObject body);


    @POST("data")
    Call<ResponseBody> dataPost(
            @Body JsonObject body);

}
