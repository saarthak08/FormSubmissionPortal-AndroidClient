package com.sg.formsubmissionportal_androidclient.ui.MainActivity.allforms;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AllFormsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AllFormsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is AllForms fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}