package com.sg.formsubmissionportal_androidclient.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sg.formsubmissionportal_androidclient.R;
import com.sg.formsubmissionportal_androidclient.databinding.ActivityLoginBinding;
import com.sg.formsubmissionportal_androidclient.databinding.ActivitySignupBinding;
import com.sg.formsubmissionportal_androidclient.di.App;
import com.sg.formsubmissionportal_androidclient.network.LoginSignupService;

import javax.inject.Inject;

public class SignupActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText idNo;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button signup;
    private ActivitySignupBinding activitySignupBinding;
    private ProgressBar progressBar;
    @Inject
    public LoginSignupService loginSignupService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        activitySignupBinding= DataBindingUtil.setContentView(SignupActivity.this,R.layout.activity_signup);
        activitySignupBinding.setClickHandlers(new ClickHandlers());
        email=activitySignupBinding.emails;
        password=activitySignupBinding.passwords;
        firstName=activitySignupBinding.firstNameTV;
        lastName=activitySignupBinding.lastNameTV;
        idNo=activitySignupBinding.idNoTV;
        signup=activitySignupBinding.signupbuttons;
        progressBar=activitySignupBinding.progressBar1;
        App.getApp().getComponent().inject(SignupActivity.this);
        radioGroup=activitySignupBinding.radioGroup;
    }


    public class ClickHandlers{

        public void onSignUpButtonClicked(View view){

        }
    }
}
