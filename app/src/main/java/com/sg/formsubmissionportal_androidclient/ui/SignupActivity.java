package com.sg.formsubmissionportal_androidclient.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.sg.formsubmissionportal_androidclient.R;
import com.sg.formsubmissionportal_androidclient.databinding.ActivitySignupBinding;
import com.sg.formsubmissionportal_androidclient.di.App;
import com.sg.formsubmissionportal_androidclient.network.LoginSignupService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private EditText email;
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
    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        activitySignupBinding= DataBindingUtil.setContentView(SignupActivity.this,R.layout.activity_signup);
        activitySignupBinding.setClickHandlers(new ClickHandlers());
        email=activitySignupBinding.emails;
        firstName=activitySignupBinding.firstNameTV;
        lastName=activitySignupBinding.lastNameTV;
        idNo=activitySignupBinding.idNoTV;
        signup=activitySignupBinding.signupbuttons;
        progressBar=activitySignupBinding.progressBar1;
        App.getApp().getAppComponent().inject(SignupActivity.this);
        radioGroup=activitySignupBinding.radioGroup;
    }


    public class ClickHandlers{

        public void onSignUpButtonClicked(View view){
            if(firstName.getText().toString().trim().length()!=0&&lastName.getText().toString().trim().length()!=0&&email.getText().toString().trim().length()!=0&&idNo.getText().toString().trim().length()!=0){
                if(idNo.getText().toString().trim().length()<10){
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);
                    final JsonObject jsonObject=new JsonObject();
                    jsonObject.addProperty("firstName",firstName.getText().toString().trim());
                    jsonObject.addProperty("lastName",lastName.getText().toString().trim());
                    jsonObject.addProperty("email",email.getText().toString().trim());
                    jsonObject.addProperty("idNumber",email.getText().toString().trim());
                    if(radioGroup.getCheckedRadioButtonId()==R.id.radio1){
                        jsonObject.addProperty("role","STUDENT");
                    }
                    else if(radioGroup.getCheckedRadioButtonId()==R.id.radio2){
                        jsonObject.addProperty("role","DEAN");
                    }
                    else{
                        jsonObject.addProperty("role","PROVOST");
                    }
                    Log.d("Request",jsonObject.toString());
                    Call<JsonObject> call= loginSignupService.signup(jsonObject);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(final Call<JsonObject> call, Response<JsonObject> response) {
                            if(response.code()==200){
                                Toast.makeText(SignupActivity.this,"Successful",Toast.LENGTH_SHORT).show();
                                materialDialog=new MaterialDialog.Builder(SignupActivity.this).title("Verify your email!")
                                        .content("An email link has been sent to "+email.getText().toString()+". Please verify your credentials and set your password!")
                                        .positiveText("Try Again")
                                        .negativeText("Done")
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                callAgain(jsonObject);
                                                dialog.show();
                                            }
                                        })
                                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                materialDialog.dismiss();
                                                materialDialog.cancel();
                                                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                                                SignupActivity.this.finish();
                                            }
                                        })
                                        .cancelable(false)
                                        .canceledOnTouchOutside(false)
                                        .show();
                            }
                            else if(response.code()==405){
                                Toast.makeText(SignupActivity.this,"Email already present!",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(SignupActivity.this,"SignUp failed! Please try again.",Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setIndeterminate(false);
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            progressBar.setIndeterminate(false);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(SignupActivity.this,"SignUp failed! Please try again.",Toast.LENGTH_SHORT).show();
                            Log.d("Request",t.toString());
                        }
                    });
                }
                else{
                    Snackbar.make(view,"Invalid Faculty/Employee Number",Snackbar.LENGTH_SHORT).show();
                }
            }
            else{
                Snackbar.make(view,"Empty Input!",Snackbar.LENGTH_SHORT).show();
            }

        }

        public void callAgain(JsonObject jsonObject){
            Call<JsonObject> call= loginSignupService.signup(jsonObject);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.code()==200){
                        Toast.makeText(SignupActivity.this,"Link Sent Again!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(SignupActivity.this,"Error! Please try again.",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(SignupActivity.this,"Error! Please try again.",Toast.LENGTH_SHORT).show();

                }
            });

        }
    }
}
