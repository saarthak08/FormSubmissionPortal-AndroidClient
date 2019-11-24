package com.sg.formsubmissionportal_androidclient.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sg.formsubmissionportal_androidclient.R;
import com.sg.formsubmissionportal_androidclient.adapter.FormUsersAdapter;
import com.sg.formsubmissionportal_androidclient.adapter.FormsAdapter;
import com.sg.formsubmissionportal_androidclient.di.App;
import com.sg.formsubmissionportal_androidclient.di.component.MainActivityComponent;
import com.sg.formsubmissionportal_androidclient.model.Form;
import com.sg.formsubmissionportal_androidclient.model.FormDetail;
import com.sg.formsubmissionportal_androidclient.model.User;
import com.sg.formsubmissionportal_androidclient.network.FormService;
import com.sg.formsubmissionportal_androidclient.ui.MainActivity.MainActivity;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormUsersActivity extends AppCompatActivity {


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
    private ArrayList<User> users;
    private RecyclerView recyclerView;
    private ArrayList<FormDetail> formDetails;
    private FormUsersAdapter formsAdapter;

    @Inject
    @Named("formService")
    public FormService formService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_users);
        progressBar=findViewById(R.id.progressBarFormUsers);
        progressBar.setIndeterminate(true);
        recyclerView=findViewById(R.id.rv_form_users);
        Intent i = getIntent();
        if (i.getExtras() != null) {
            form = i.getParcelableExtra("form");
        }
        getSupportActionBar().setTitle("Users of Form ("+form.getFormCode()+")");
        MainActivity.getComponent().inject(FormUsersActivity.this);
        getFormUsers();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getAllFormDetails();
            }
        },500);
    }

    private void getFormUsers(){
        formService.getFormUsers(form.getFormCode()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){
                    try {
                        JsonElement jsonElement = new JsonParser().parse(response.body().string());
                        JsonArray jsonArray=jsonElement.getAsJsonObject().get("formUsers").getAsJsonArray();
                        users=new ArrayList<>();
                        Gson gson=new Gson();
                        for(JsonElement jsonElement1: jsonArray){
                            users.add(gson.fromJson(jsonElement1,User.class));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if(response.code()==405){
                    Toast.makeText(FormUsersActivity.this,"Form not found!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(App.getApp(), "Access Denied! ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(App.getApp(), "Something went wrong! Try Again..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllFormDetails(){
        formService.getAllFormDetails(form.getFormCode()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){
                    try {
                        JsonElement jsonElement = new JsonParser().parse(response.body().string());
                        JsonArray jsonArray=jsonElement.getAsJsonObject().get("formDetails").getAsJsonArray();
                        formDetails=new ArrayList<>();
                        Gson gson=new Gson();
                        for(JsonElement jsonElement1: jsonArray){
                            formDetails.add(gson.fromJson(jsonElement1,FormDetail.class));
                        }
                        initiateView();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if(response.code()==405){
                    Toast.makeText(FormUsersActivity.this,"Form not found!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(App.getApp(), "Access Denied! ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(App.getApp(), "Something went wrong! Try Again..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initiateView(){
        formsAdapter = new FormUsersAdapter(formDetails,users,FormUsersActivity.this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(FormUsersActivity.this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(FormUsersActivity.this));
        recyclerView.setAdapter(formsAdapter);
        progressBar.setVisibility(View.GONE);
    }
}
