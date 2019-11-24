package com.sg.formsubmissionportal_androidclient.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sg.formsubmissionportal_androidclient.R;
import com.sg.formsubmissionportal_androidclient.model.Form;
import com.sg.formsubmissionportal_androidclient.model.FormDetail;
import com.sg.formsubmissionportal_androidclient.network.FormService;

import javax.inject.Inject;
import javax.inject.Named;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_users);
        Intent i = getIntent();
        if (i.getExtras() != null) {
            form = i.getParcelableExtra("form");
        }
    }
}
