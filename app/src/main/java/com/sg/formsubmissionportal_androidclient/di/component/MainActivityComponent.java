package com.sg.formsubmissionportal_androidclient.di.component;


import com.sg.formsubmissionportal_androidclient.adapter.FormsAdapter;
import com.sg.formsubmissionportal_androidclient.di.module.OtherNetworkServicesModule;
import com.sg.formsubmissionportal_androidclient.ui.FormStatusActivity;
import com.sg.formsubmissionportal_androidclient.ui.FormUsersActivity;
import com.sg.formsubmissionportal_androidclient.ui.MainActivity.allforms.AllFormsViewModel;
import com.sg.formsubmissionportal_androidclient.ui.MainActivity.myforms.MyFormsViewModel;
import com.sg.formsubmissionportal_androidclient.ui.MainActivity.myprofile.MyProfileFragment;
import com.sg.formsubmissionportal_androidclient.ui.SubmitFormActivity;

import javax.inject.Singleton;

@Singleton
@dagger.Component(modules = {OtherNetworkServicesModule.class})
public interface MainActivityComponent {

    void inject(AllFormsViewModel allFormsViewModel);

    void inject(MyFormsViewModel myFormsViewModel);

    void inject(MyProfileFragment myProfileFragment);

    void inject(FormStatusActivity formStatusActivity);

    void inject(FormUsersActivity formUsersActivity);

    void inject(SubmitFormActivity submitFormActivity);

    void inject(FormsAdapter formsAdapter);
}