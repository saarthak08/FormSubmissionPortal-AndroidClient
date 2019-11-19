package com.sg.formsubmissionportal_androidclient.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sg.formsubmissionportal_androidclient.R;
import com.sg.formsubmissionportal_androidclient.databinding.ActivityLoginBinding;
import com.sg.formsubmissionportal_androidclient.di.App;
import com.sg.formsubmissionportal_androidclient.model.User;
import com.sg.formsubmissionportal_androidclient.network.FormService;
import com.sg.formsubmissionportal_androidclient.network.LoginSignupService;
import com.sg.formsubmissionportal_androidclient.ui.MainActivity.MainActivity;

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
    public LoginSignupService loginSignupService;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    public static final String PREFER_NAME = "FSP";
    public static final String IS_USER_LOGIN = "IsUserLoggedIn";
    public static final String KEY_NAME = "token";

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
        pref=getSharedPreferences(PREFER_NAME,PRIVATE_MODE);
    }

    public class ClickHandlers{

        public void onLoginButtonClicked(View view){
            if(email.getText().length()!=0&&password.getText().length()!=0){
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setIndeterminate(true);
                JsonObject req=new JsonObject();
                req.addProperty("username",email.getText().toString().trim());
                req.addProperty("password",password.getText().toString().trim());
                Call<JsonObject> call= loginSignupService.login(req);
                Log.d("Request",req.toString());
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.code() == 200) {
                            //Toast.makeText(LoginActivity.this,"Login successful",Toast.LENGTH_SHORT).show();
                            //Log.d("Request",response.body().get("token").toString());
                            pref = LoginActivity.this.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
                            editor = pref.edit();
                            editor.putString(KEY_NAME,response.body().get("token").toString());
                            editor.putBoolean(IS_USER_LOGIN,true);
                            progressBar.setVisibility(View.INVISIBLE);
                            String mJsonString = response.body().get("user").toString();
                            JsonParser parser = new JsonParser();
                            JsonElement mJson =  parser.parse(mJsonString);
                            Gson gson = new Gson();
                            User object = gson.fromJson(mJson, User.class);
                            editor.putLong("userid",object.getId());
                            editor.putString("email",object.getEmail());
                            editor.putString("firstName",object.getFirstName());
                            editor.putString("lastName",object.getLastName());
                            editor.putString("role",object.getRole().getUserType());
                            editor.commit();
                            progressBar.setIndeterminate(false);
                            Intent i=new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            LoginActivity.this.finish();
                        }
                        else if(response.code()==401){
                            progressBar.setIndeterminate(false);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this,"Invalid Username or Password!",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            progressBar.setIndeterminate(false);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this,"Login failed! Please try again.",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        progressBar.setIndeterminate(false);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(LoginActivity.this,"Login failed! Please try again.",Toast.LENGTH_SHORT).show();
                        Log.d("Request",t.toString());
                    }
                });
            }
            else{
                progressBar.setIndeterminate(false);
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(LoginActivity.this,"Empty Inputs!",Toast.LENGTH_SHORT).show();
            }

        }


        public void onForgotPasswordClicked(View view){

            /*
            pref=getSharedPreferences(PREFER_NAME,PRIVATE_MODE);
            editor = pref.edit();
            editor.clear();
            editor.apply();




            if(pref.contains(IS_USER_LOGIN)){
            boolean t=pref.getBoolean(IS_USER_LOGIN,false);
            if(t){
                String username=pref.getString(KEY_NAME,"");
                Intent i=new Intent(MainActivity.this,HomeActivity.class);
                i.putExtra("username",username);
                startActivity(i);
                MainActivity.this.finish();
            }

             */

        }


        public void onSignUpButtonClicked(View view){
            startActivity(new Intent(LoginActivity.this,SignupActivity.class));
        }
    }
}
