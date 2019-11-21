package com.sg.formsubmissionportal_androidclient.network;

import com.google.gson.JsonObject;
import com.sg.formsubmissionportal_androidclient.model.User;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FormService {

    @GET("forms/get-forms/{userid}")
    Call<ResponseBody> getUserForms(@Path("userid") String userid);


    @GET("forms/get-all-forms")
    Call<ResponseBody> getAllForms();
}
