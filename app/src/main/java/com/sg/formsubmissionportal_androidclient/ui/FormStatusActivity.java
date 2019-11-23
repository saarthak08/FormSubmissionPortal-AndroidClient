package com.sg.formsubmissionportal_androidclient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.sg.formsubmissionportal_androidclient.R;
import com.sg.formsubmissionportal_androidclient.databinding.ActivityFormStatusBinding;
import com.sg.formsubmissionportal_androidclient.di.App;
import com.sg.formsubmissionportal_androidclient.model.Form;
import com.sg.formsubmissionportal_androidclient.model.FormDetail;
import com.sg.formsubmissionportal_androidclient.model.User;
import com.sg.formsubmissionportal_androidclient.network.FormService;
import com.sg.formsubmissionportal_androidclient.ui.MainActivity.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormStatusActivity extends AppCompatActivity {

    private Form form;
    private ProgressBar progressBar;
    private TextView formTitle;
    private TextView formDepartment;
    private TextView formCode;
    private TextView firstName;
    private TextView lastName;
    private TextView email;
    private TextView facultyNo;
    private TextView enrollmentNo;
    private TextView phoneNumber;
    private FormDetail formDetail;
    private ActivityFormStatusBinding formStatusBinding;
    private Map<String,String> formCheckPoints;
    private Map<String, Boolean> userFormCheckPoints;
    private StateProgressBar stateProgressBar;
    private ArrayList<String> checkPoints;
    private Map<String,String> formTimestamps;


    @Inject
    @Named("formService")
    public FormService formService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_status);

        Intent i = getIntent();
        if (i.getExtras() != null) {
            form = i.getParcelableExtra("form");
        }
        getSupportActionBar().setTitle("Form Status");
        MainActivity.getComponent().inject(FormStatusActivity.this);
        formStatusBinding = DataBindingUtil.setContentView(FormStatusActivity.this, R.layout.activity_form_status);
        formStatusBinding.setClickHandlers(new ClickHandlers());
        progressBar=formStatusBinding.progressBarStatus;
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(false);
        formCheckPoints=new HashMap<>();
        userFormCheckPoints=new HashMap<>();
        formTimestamps=new HashMap<>();
        formTitle = formStatusBinding.formTitleStatus;
        stateProgressBar=formStatusBinding.formStatus;
        formDepartment = formStatusBinding.formDepartmentStatus;
        formCode = formStatusBinding.formCodeStatus;
        firstName = formStatusBinding.formDetailFirstName;
        lastName = formStatusBinding.formDetailLastName;
        email = formStatusBinding.formDetailEmail;
        facultyNo = formStatusBinding.formDetailFacultyNo;
        enrollmentNo = formStatusBinding.formDetailEnrollmentNo;
        phoneNumber=formStatusBinding.formDetailPhoneNo;
        formTitle.setText(form.getTitle());
        stateProgressBar.setMaxDescriptionLine(4);
        formDepartment.setText("Department: " + form.getDepartment());
        formCode.setText("Form Code: " + form.getFormCode());
        checkPoints=new ArrayList<>();

        getFormDetails();
        getUserTimeStamps();
        getFormCheckPoints();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getUserCheckPoints();
            }
        },1000);

    }

    public void getFormDetails() {
       formService.getMyFormDetails(form.getFormCode()).enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               if(response.code()==200){
                    JsonParser jsonParser=new JsonParser();
                   try {
                       JsonElement jsonElement=jsonParser.parse(response.body().string());
                       JsonObject jsonObject=jsonElement.getAsJsonObject();
                       Gson gson=new Gson();
                       formDetail=gson.fromJson(jsonObject.get("formDetail").getAsJsonObject(),FormDetail.class);
                       setFormDetails();
                   } catch (IOException e) {
                       Toast.makeText(FormStatusActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                   }
               }
               else if(response.code()==405){
                   Toast.makeText(App.getApp(), "No FormDetail found!", Toast.LENGTH_SHORT).show();
               }
               else{
                   Toast.makeText(App.getApp(), "Access Denied! ", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<ResponseBody> call, Throwable t) {
               Toast.makeText(App.getApp(), "Something went wrong! Try Again..", Toast.LENGTH_SHORT).show();
           }
       });
    }


    public void getFormCheckPoints(){
        formService.getFormCheckpoints(form.getFormCode()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){
                    try {
                        JsonElement jsonElement=new JsonParser().parse(response.body().string());
                        JsonObject jsonObject=jsonElement.getAsJsonObject().get("formCheckPoint").getAsJsonObject();
                        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                        for(Map.Entry<String,JsonElement> entry : entrySet){
                            formCheckPoints.put(entry.getKey(), jsonObject.get(entry.getKey()).getAsString());
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else{
                    Toast.makeText(FormStatusActivity.this,"Form not found!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(App.getApp(), "Something went wrong! Try Again..", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void setFormDetails(){
        if(formDetail!=null){
            progressBar.setIndeterminate(false);
            firstName.setText("First Name: "+formDetail.getFirstName());
            lastName.setText("Last Name: "+formDetail.getLastName());
            email.setText("Email: "+formDetail.getEmail());
            phoneNumber.setText("Phone Number: "+formDetail.getPhoneNumber());
            facultyNo.setText("Faculty Number: "+formDetail.getFacultyNumber());
            enrollmentNo.setText("Enrollment Number: "+formDetail.getEnrollmentNumber());
        }
    }

    public void getUserCheckPoints(){
        formService.getFormCheckpointsForAUserDetail(form.getFormCode(),MainActivity.userid.toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){
                    JsonElement jsonElement= null;
                    try {
                        jsonElement = new JsonParser().parse(response.body().string());
                        JsonObject jsonObject=jsonElement.getAsJsonObject().get("formCheckPoints").getAsJsonObject();
                        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                        for(Map.Entry<String,JsonElement> entry : entrySet){
                            userFormCheckPoints.put(entry.getKey(), jsonObject.get(entry.getKey()).getAsBoolean());
                        }
                        int size=1;
                        for(Map.Entry<String, Boolean> entry:userFormCheckPoints.entrySet()){
                            size++;
                            if(!entry.getValue()){
                                break;
                            }
                        }
                        if(size==2){
                            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                        }
                        else if(size==3){
                            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                        }
                        else if(size==4){
                            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                        }
                        else if(size==5)
                        {
                            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE);
                        }
                        if(size== entrySet.size()+1){
                            stateProgressBar.setAllStatesCompleted(true);
                        }
                        stateProgressBar.enableAnimationToCurrentState(true);
                        stateProgressBar.checkStateCompleted(true);
                        progressBar.setVisibility(View.INVISIBLE);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if(response.code()==404){
                    Toast.makeText(FormStatusActivity.this,"Form not found!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(App.getApp(), "Access Denied! ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(App.getApp(), "Something went wrong! Try Again..", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getUserTimeStamps(){
        formService.getTimestampsForUserChecPoints(form.getFormCode(),MainActivity.userid.toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){
                    JsonElement jsonElement= null;
                    try {
                        jsonElement = new JsonParser().parse(response.body().string());
                        JsonObject jsonObject=jsonElement.getAsJsonObject().get("formTimestamps").getAsJsonObject();
                        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                        checkPoints.add("Submit");
                        int size=1;
                        for(Map.Entry<String,JsonElement> entry : entrySet){
                            size++;
                            formTimestamps.put(entry.getKey(), jsonObject.get(entry.getKey()).getAsString());
                            Long abc=0L;
                            try {
                                abc = jsonObject.get(entry.getKey()).getAsLong();
                            }
                            catch (Exception e){
                            }
                            if(abc!=0) {
                                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                                cal.setTimeInMillis(abc * 1000);
                                String day = DateFormat.format("EEE", cal).toString();
                                String date = DateFormat.format("MMM-dd/yy", cal).toString();
                                String time = DateFormat.format("h:mm a", cal).toString();
                                checkPoints.add(entry.getKey() + "\n" + date + "\n" + day + "\n" + time);
                            }
                            else {
                                checkPoints.add(entry.getKey());
                            }
                            stateProgressBar.enableAnimationToCurrentState(true);
                            stateProgressBar.checkStateCompleted(true);
                            if(size==2){
                                stateProgressBar.setMaxStateNumber(StateProgressBar.StateNumber.TWO);
                            }
                            else if(size==3){
                                stateProgressBar.setMaxStateNumber(StateProgressBar.StateNumber.THREE);
                            }
                            else if(size==4){
                                stateProgressBar.setMaxStateNumber(StateProgressBar.StateNumber.FOUR);
                            }
                            else if(size==5){
                                stateProgressBar.setMaxStateNumber(StateProgressBar.StateNumber.FIVE);
                            }
                            stateProgressBar.setStateDescriptionData(checkPoints);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if(response.code()==404){
                    Toast.makeText(FormStatusActivity.this,"Form not found!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(App.getApp(), "Access Denied! ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(App.getApp(), "Something went wrong! Try Again..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class ClickHandlers {

    }
}
