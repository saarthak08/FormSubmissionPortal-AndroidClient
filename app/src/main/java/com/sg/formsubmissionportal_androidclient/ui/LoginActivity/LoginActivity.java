package com.sg.formsubmissionportal_androidclient.ui.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.JsonObject;
import com.sg.formsubmissionportal_androidclient.R;
import com.sg.formsubmissionportal_androidclient.databinding.ActivityLoginBinding;
import com.sg.formsubmissionportal_androidclient.di.App;
import com.sg.formsubmissionportal_androidclient.network.CallService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private TextView forgotPassword;
    private Button login;
    private Button signup;
    private ActivityLoginBinding activityLoginBinding;
    private ProgressBar progressBar;
    @Inject
    public CallService callService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("FormSubmissionPortal-AMU");
        App.getApp().getComponent().inject(this);
        activityLoginBinding= DataBindingUtil.setContentView(LoginActivity.this,R.layout.activity_login);
        email=activityLoginBinding.email;
        password=activityLoginBinding.password;
       // forgotPassword=activityLoginBinding.textViewforget;
        login=activityLoginBinding.loginButton;
        signup=activityLoginBinding.signupbutton;
        progressBar=activityLoginBinding.progressBar1;
        activityLoginBinding.setClickHandlers(new ClickHandlers());
    }

    public class ClickHandlers{


        public void onLoginButtonClicked(View view){
            if(email.getText().length()!=0&&password.getText().length()!=0){
                JsonObject req=new JsonObject();
                req.addProperty("username",email.getText().toString().trim());
                req.addProperty("password",password.getText().toString().trim());
                Call<JsonObject> call=callService.login(req);
                Log.d("Request",req.toString());
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.code() == 200) {
                            Toast.makeText(LoginActivity.this,"Login successful",Toast.LENGTH_SHORT).show();
                            Log.d("Request",response.body().toString());
                        }
                        else if(response.code()==401){
                            Toast.makeText(LoginActivity.this,"Invalid Username or Password!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(LoginActivity.this,"Login failed! Please try again.",Toast.LENGTH_SHORT).show();
                        Log.d("Request",t.toString());
                    }
                });
            }
            else{
                Toast.makeText(LoginActivity.this,"Empty Inputs!",Toast.LENGTH_SHORT).show();
            }

        }


        public void onForgotPasswordClicked(View view){

        }


        public void onSignUpButtonClicked(View view){

        }
    }
}
