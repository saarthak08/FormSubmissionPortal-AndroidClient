package com.sg.formsubmissionportal_androidclient.network;

import com.sg.formsubmissionportal_androidclient.model.User;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

public interface UserService {

    @GET("user/info")
    Call<User> getUserInfo();


    @GET("users")
    Call<Response> getJsonResponse();

}
