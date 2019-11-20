package com.sg.formsubmissionportal_androidclient.ui.MainActivity.allforms;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sg.formsubmissionportal_androidclient.di.App;
import com.sg.formsubmissionportal_androidclient.model.Form;
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

public class AllFormsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    private MutableLiveData<ArrayList<Form>> forms;

    @Inject
    @Named("formService")
    public FormService formService;


    public AllFormsViewModel() {
        mText = new MutableLiveData<>();
        forms = new MutableLiveData<>();
        MainActivity.getComponent().inject(this);
    }

    public void setText() {
        mText.setValue("No available forms! Sorry! :(");
    }

    public LiveData<String> getText() {
        return mText;
    }


    public LiveData<ArrayList<Form>> getAllForms() {
    /*    formService.getAllForms(MainActivity.userid.toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    JsonObject jsonObject = null;
                    try {
                        JsonParser jsonParser=new JsonParser();
                        JsonElement jsonElement=jsonParser.parse(response.body().string());
                        jsonObject=jsonElement.getAsJsonObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    JsonArray jsonArray = jsonObject.get("forms").getAsJsonArray();
                    ArrayList<Form> tempForms = new ArrayList<>();
                    for (JsonElement j : jsonArray) {
                        Gson gson = new Gson();
                        Form form = gson.fromJson(j, Form.class);
                        tempForms.add(form);
                    }
                    forms.setValue(tempForms);
                } else {
                    Toast.makeText(App.getApp(), "Access Denied! ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(App.getApp(), "Something went wrong! Try Again..", Toast.LENGTH_SHORT).show();
            }
        });

     */
        return forms;
    }
}