package com.sg.formsubmissionportal_androidclient.di;

import android.app.Application;

import com.sg.formsubmissionportal_androidclient.di.component.AppComponent;
import com.sg.formsubmissionportal_androidclient.di.component.DaggerAppComponent;
import com.sg.formsubmissionportal_androidclient.di.module.ApplicationContextModule;
import com.sg.formsubmissionportal_androidclient.di.module.LoginSignupServiceModule;

public class App extends Application {
    private AppComponent appComponent;
    private static App application;
    public static int PRIVATE_MODE = 0;
    public static final String PREFER_NAME = "FSP";
    public static final String IS_USER_LOGIN = "IsUserLoggedIn";


    public static App getApp() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        appComponent = DaggerAppComponent.builder().applicationContextModule(new ApplicationContextModule(application))
                .loginSignupServiceModule(new LoginSignupServiceModule())
                .build();

    }


    public AppComponent getAppComponent() {
        return appComponent;
    }

}
