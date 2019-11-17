package com.sg.formsubmissionportal_androidclient.di;

import com.sg.formsubmissionportal_androidclient.ui.LoginActivity.LoginActivity;


import javax.inject.Singleton;

@Singleton
@dagger.Component(modules = {ApplicationContextModule.class, CallServiceModule.class})
public interface Component {

    void inject(LoginActivity loginActivity);
}
