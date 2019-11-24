package com.sg.formsubmissionportal_androidclient.di.module;

import com.sg.formsubmissionportal_androidclient.network.FormService;
import com.sg.formsubmissionportal_androidclient.network.RetrofitInstance;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;


@Singleton
@Module
public class OtherNetworkServicesModule {

    private String token;
    private Retrofit retrofitInstance;

    public OtherNetworkServicesModule(String token){
        this.token=token;
        retrofitInstance=RetrofitInstance.getAuthorizedClient(this.token);
    }

    @Provides
    @Named("formService")
    public FormService getFormService(){
        return retrofitInstance.create(FormService.class);
    }

    @Provides
    @Named("userService")
    public FormService getUserService(){
        return retrofitInstance.create(FormService.class);
    }

}