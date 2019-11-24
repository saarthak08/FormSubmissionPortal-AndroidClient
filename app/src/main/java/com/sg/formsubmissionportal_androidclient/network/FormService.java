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
import retrofit2.http.Query;

public interface FormService {

    @GET("forms/get-forms/{userid}")
    Call<ResponseBody> getUserForms(@Path("userid") String userid);


    @GET("forms/get-all-forms")
    Call<ResponseBody> getAllForms();


    @GET("forms/get-formDetails/{formCode}")
    Call<ResponseBody> getMyFormDetails(@Path("formCode") String formCode);


    @GET("forms/get-all-formDetails/{formCode}")
    Call<ResponseBody> getAllFormDetails(@Path("formCode") String formCode);


    @GET("forms/get-form-checkpoints/{formCode}")
    Call<ResponseBody> getFormCheckpoints(@Path("formCode") String formCode);

    @GET("forms/get-form-checkpoints/{formCode}/{userid}")
    Call<ResponseBody> getFormCheckpointsForAUserDetail(@Path("formCode")String formCode,@Path("userid") Long userid);

    @GET("forms/get-form-timestamps/{formCode}/{userid}")
    Call<ResponseBody> getTimestampsForUserCheckPoints(@Path("formCode") String formCode, @Path("userid") Long userid);


    @GET("forms/checkFormStatus/{formCode}/{userid}")
    Call<ResponseBody> checkFormStatus(@Path("formCode") String formCode, @Path("userid") Long userid);

    @POST("forms/checkForm")
    Call<ResponseBody> checkForm( JsonObject jsonObject);

}
