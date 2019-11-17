package com.sg.formsubmissionportal_androidclient.di;


import com.sg.formsubmissionportal_androidclient.network.CallService;
import com.sg.formsubmissionportal_androidclient.network.RetrofitInstance;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Singleton
@Module
public class CallServiceModule {

    private Retrofit retrofitInstance= RetrofitInstance.getService();

    @Provides
    public CallService getCallService(){
        return retrofitInstance.create(CallService.class);
    }
}
