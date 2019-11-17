package com.sg.formsubmissionportal_androidclient.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sg.formsubmissionportal_androidclient.R;
import com.sg.formsubmissionportal_androidclient.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private TextView forgotPassword;
    private Button login;
    private Button Signup;
    private ActivityLoginBinding activityLoginBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("FormSubmissionPortal-AMU");

    }

    public class ClickHandlers{


        public void onLoginButtonClicked(View view){

        }


        public void onForgotPasswordClicked(View view){

        }


        public void onSignUpButtonClicked(View view){

        }
    }
}
