package com.sg.formsubmissionportal_androidclient.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sg.formsubmissionportal_androidclient.di.App;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit = null;
    private static Retrofit authRetrofit = null;
    private static final String BASE_URL = "https://formsubmissionportal-amu.herokuapp.com/api/";
    private static final int REQUEST_TIMEOUT = 60;
    private static OkHttpClient okHttpClient;
    private static String token="";


    public static Retrofit getLognSignupClient() {
        if (okHttpClient == null) {
            initOkHttp();
        }

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }


    private static void initOkHttp() {


        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .cache(null)
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient = httpClient.build();
    }


    public static Retrofit getAuthorizedClient(String authToken) {

        token=authToken;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
                        .addHeader("Cache-Control", "no-cache")
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.cache(null).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        if (authRetrofit == null) {
            authRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client).build();
        }
        return authRetrofit;

    }
}