package com.sg.formsubmissionportal_androidclient.di;

import com.sg.formsubmissionportal_androidclient.network.FormService;
import com.sg.formsubmissionportal_androidclient.network.LoginSignupService;
import com.sg.formsubmissionportal_androidclient.network.RetrofitInstance;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;


@Singleton
@Module
public class LoginSignupServiceModule {

    private Retrofit retrofitInstance= RetrofitInstance.getLognSignupClient();

    @Provides
    public LoginSignupService getService(){
        return retrofitInstance.create(LoginSignupService.class);
    }
}
