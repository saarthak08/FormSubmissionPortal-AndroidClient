package com.sg.formsubmissionportal_androidclient.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginSignupService {

    @POST("api/authenticate")
    Call<JsonObject> login(
            @Body JsonObject body);

}
