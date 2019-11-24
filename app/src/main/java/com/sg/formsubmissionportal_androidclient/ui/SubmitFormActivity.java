package com.sg.formsubmissionportal_androidclient.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sg.formsubmissionportal_androidclient.R;
import com.sg.formsubmissionportal_androidclient.model.Form;
import com.sg.formsubmissionportal_androidclient.model.FormDetail;
import com.sg.formsubmissionportal_androidclient.network.FormService;
import com.sg.formsubmissionportal_androidclient.ui.MainActivity.MainActivity;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitFormActivity extends AppCompatActivity {

    @Inject
    @Named("formService")
    public FormService formService;

    private Form form;
    private ProgressBar progressBar;
    private TextView formTitle;
    private TextView formDepartment;
    private TextView formCode;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText facultyNo;
    private EditText enrollmentNo;
    private EditText phoneNumber;
    private Button submitButton;
    private View parentLayout;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_form);
        getSupportActionBar().setTitle("Submit Form");
        MainActivity.getComponent().inject(SubmitFormActivity.this);
        Intent i=getIntent();
        if(i.getExtras()!=null){
            form=i.getParcelableExtra("form");
        }
        formTitle=findViewById(R.id.form_fill_title);
        formDepartment=findViewById(R.id.form_fill_department);
        formCode=findViewById(R.id.form_fill_code);
        firstName=findViewById(R.id.editText_firstName);
        lastName=findViewById(R.id.editText_lastName);
        email=findViewById(R.id.editText_email);
        facultyNo=findViewById(R.id.editText_facultyNumber);
        enrollmentNo=findViewById(R.id.editText_enrolmentNumber);
        phoneNumber=findViewById(R.id.editText_phoneNumber);
        submitButton=findViewById(R.id.buttonSubmit);
        cancelButton=findViewById(R.id.buttonCancel);
        formTitle.setText(form.getTitle());
        formDepartment.setText(form.getDepartment());
        formCode.setText(form.getFormCode());
        progressBar=findViewById(R.id.progressBarSubmitForm);
        firstName.setText(MainActivity.firstName);
        lastName.setText(MainActivity.lastName);
        email.setText(MainActivity.email);
        parentLayout = findViewById(android.R.id.content);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitFormActivity.this.finish();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setIndeterminate(true);
                if(firstName.getText().toString().trim().length()!=0&&lastName.getText().toString().trim().length()!=0&&email.getText().toString().trim().length()!=0
                        &&phoneNumber.getText().toString().trim().length()!=0&&facultyNo.getText().toString().trim().length()!=0&&enrollmentNo.getText().toString().trim().length()!=0){
                    JsonObject jsonObject=new JsonObject();
                    JsonObject formDetail=new JsonObject();
                    formDetail.addProperty("email",email.getText().toString().trim());
                    formDetail.addProperty("firstName",firstName.getText().toString().trim());
                    formDetail.addProperty("lastName",lastName.getText().toString().trim());
                    formDetail.addProperty("enrollmentNumber",enrollmentNo.getText().toString().trim());
                    formDetail.addProperty("facultyNumber",facultyNo.getText().toString().trim());
                    formDetail.addProperty("status",true);
                    formDetail.addProperty("phoneNumber",phoneNumber.getText().toString().trim());
                    jsonObject.add("formDetails",formDetail);
                    jsonObject.addProperty("formCode",form.getFormCode());
                    formService.submitForm(jsonObject).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.code()==200){
                                Snackbar.make(parentLayout,"Form Submitted",Snackbar.LENGTH_SHORT).show();
                            }
                            else if(response.code()==208){
                                Snackbar.make(parentLayout,"Form already submitted",Snackbar.LENGTH_SHORT).show();
                            }
                            else if(response.code()==405){
                                Snackbar.make(parentLayout,"Invalid form data!",Snackbar.LENGTH_SHORT).show();
                            }
                            else{
                                Snackbar.make(parentLayout,"Access Denied!",Snackbar.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Snackbar.make(parentLayout,"Something went wrong! Try Again..",Snackbar.LENGTH_SHORT).show();
                        }
                    });
                    progressBar.setVisibility(View.INVISIBLE);
                    progressBar.setIndeterminate(false);
                }
                else{
                    Snackbar.make(parentLayout,"Error, empty inputs!",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}
