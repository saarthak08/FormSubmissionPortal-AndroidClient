package com.sg.formsubmissionportal_androidclient.di;

import com.sg.formsubmissionportal_androidclient.ui.LoginActivity;
import com.sg.formsubmissionportal_androidclient.ui.SignupActivity;


import javax.inject.Singleton;

@Singleton
@dagger.Component(modules = {ApplicationContextModule.class,LoginSignupServiceModule.class})
public interface Component {

    void inject(LoginActivity loginActivity);

    void inject(SignupActivity signupActivity);
}
