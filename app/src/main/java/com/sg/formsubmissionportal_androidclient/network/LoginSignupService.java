package com.sg.formsubmissionportal_androidclient.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginSignupService {

    @POST("api/authenticate")
    Call<JsonObject> login(
            @Body JsonObject body);

    @POST("api/signup/register")
    Call<JsonObject> signup(
            @Body JsonObject body);


}
