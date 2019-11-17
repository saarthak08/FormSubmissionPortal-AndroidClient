package com.sg.formsubmissionportal_androidclient.services.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit=null;
    private static final String BASE_URL="http://192.168.43.255:8080/";
    private static final int REQUEST_TIMEOUT = 60;
    private static OkHttpClient okHttpClient;
    public static CallService getService()
    {
        if (okHttpClient == null)
            initOkHttp();
        if(retrofit==null)
        {
            retrofit=new Retrofit.Builder().baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(CallService.class);
    }

    private static void initOkHttp() {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient = httpClient.build();
    }
}
