package com.sg.formsubmissionportal_androidclient.ui.MainActivity.myforms;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyFormsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyFormsViewModel() {
        mText = new MutableLiveData<>();
    }

    public void setNoFormText(){
        mText.setValue("Hey! You have currently no forms. Please choose & submit your desired forms from the \'All Forms\' section.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}