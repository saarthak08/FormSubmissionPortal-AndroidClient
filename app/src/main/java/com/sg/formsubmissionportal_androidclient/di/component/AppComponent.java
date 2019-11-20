package com.sg.formsubmissionportal_androidclient.di.component;

import com.sg.formsubmissionportal_androidclient.di.module.ApplicationContextModule;
import com.sg.formsubmissionportal_androidclient.di.module.LoginSignupServiceModule;
import com.sg.formsubmissionportal_androidclient.ui.LoginActivity;
import com.sg.formsubmissionportal_androidclient.ui.SignupActivity;


import javax.inject.Singleton;

@Singleton
@dagger.Component(modules = {ApplicationContextModule.class, LoginSignupServiceModule.class})
public interface AppComponent {

    void inject(LoginActivity loginActivity);

    void inject(SignupActivity signupActivity);
}
