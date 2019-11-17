package com.sg.formsubmissionportal_androidclient.network;

import com.google.gson.JsonObject;
import com.sg.formsubmissionportal_androidclient.model.User;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FormService {

    @POST("data")
    Call<ResponseBody> dataPost(
            @Body JsonObject body);


}
