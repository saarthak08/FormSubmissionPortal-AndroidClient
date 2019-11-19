package com.sg.formsubmissionportal_androidclient.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.sg.formsubmissionportal_androidclient.R;
import com.sg.formsubmissionportal_androidclient.ui.MainActivity.MainActivity;

public class LauncherActivity extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    public static final String PREFER_NAME = "FSP";
    public static final String IS_USER_LOGIN = "IsUserLoggedIn";
    public static final String KEY_NAME = "token";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref=getSharedPreferences(PREFER_NAME,PRIVATE_MODE);
        if(pref.contains("token")){
            if(pref.getString("token","").length()!=0){
                startActivity(new Intent(LauncherActivity.this, MainActivity.class));
                this.finish();
            }
            else{
                startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
                this.finish();
            }
        }
        else{
            startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
            this.finish();
        }
        setContentView(R.layout.activity_launcher);
    }
}
