package com.sg.formsubmissionportal_androidclient.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sg.formsubmissionportal_androidclient.R;
import com.sg.formsubmissionportal_androidclient.di.App;
import com.sg.formsubmissionportal_androidclient.model.Form;
import com.sg.formsubmissionportal_androidclient.network.FormService;
import com.sg.formsubmissionportal_androidclient.ui.FormStatusActivity;
import com.sg.formsubmissionportal_androidclient.ui.FormUsersActivity;
import com.sg.formsubmissionportal_androidclient.ui.MainActivity.MainActivity;
import com.sg.formsubmissionportal_androidclient.ui.SubmitFormActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormsAdapter extends RecyclerView.Adapter<FormsAdapter.MyFormsAdapterViewHolder> {

    private ArrayList<Form> forms;
    private String fragment;
    private Context context;
    public Map<String, String> formCheckpoints;

    @Inject
    @Named("formService")
    public FormService formService;

    public FormsAdapter(ArrayList<Form> forms,String fragment,Context context) {
        this.fragment=fragment;
        this.forms = forms;
        this.context=context;
        MainActivity.getComponent().inject(FormsAdapter.this);
        formCheckpoints=new HashMap<>();
    }


    @NonNull
    @Override
    public MyFormsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_form_list, parent, false);
        return new MyFormsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FormsAdapter.MyFormsAdapterViewHolder holder, int position) {
        holder.title.setText(forms.get(position).getTitle());
        holder.department.setText(forms.get(position).getDepartment());
        holder.formCode.setText(forms.get(position).getFormCode());
    }

    @Override
    public int getItemCount() {
        return forms == null ? 0 : forms.size();
    }


    public class MyFormsAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView formCode;
        TextView department;
        CardView cardView;

        public MyFormsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_form_title);
            department = itemView.findViewById(R.id.item_form_department);
            formCode = itemView.findViewById(R.id.item_form_formCode);
            cardView = itemView.findViewById(R.id.form_item_CV);


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fragment.equals("MyForms")) {
                        if (MainActivity.role.equals("STUDENT")) {
                            Intent intent = new Intent(context, FormStatusActivity.class);
                            int pos = getAdapterPosition();
                            Form form = forms.get(pos);
                            intent.putExtra("form", (Parcelable) form);
                            context.startActivity(intent);
                        } else {
                            Intent intent = new Intent(context, FormUsersActivity.class);
                            int pos = getAdapterPosition();
                            Form form = forms.get(pos);
                            intent.putExtra("form", (Parcelable) form);
                            context.startActivity(intent);
                        }
                    }

                    if (fragment.equals("AllForms")) {
                        if (MainActivity.role.equals("STUDENT")) {
                            Intent intent = new Intent(context, SubmitFormActivity.class);
                            int pos = getAdapterPosition();
                            Form form = forms.get(pos);
                            intent.putExtra("form", (Parcelable) form);
                            context.startActivity(intent);
                        } else {
                            int pos = getAdapterPosition();
                            Form form = forms.get(pos);
                            LayoutInflater li = LayoutInflater.from(context);
                            final View promptsView = li.inflate(R.layout.material_dialog_layout, null);
                            final TextView departmentDialog = promptsView.findViewById(R.id.dialog_formDepartment);
                            final TextView formCodeDialog = promptsView.findViewById(R.id.dialog_formCode);
                            final TextView formCheckpointsDialog = promptsView.findViewById(R.id.dialog_formCheckpoints);
                            final ProgressBar progressBar=promptsView.findViewById(R.id.progressBar_dialog);
                            progressBar.setVisibility(View.VISIBLE);
                            progressBar.setIndeterminate(true);
                            getFormCheckPoints(form,formCheckpointsDialog,progressBar);
                            final Button getUsers = promptsView.findViewById(R.id.button3);
                            departmentDialog.setText("Form Department: " + form.getDepartment());
                            formCodeDialog.setText("Form Code: " + form.getFormCode());
                            new MaterialDialog.Builder(context).title(form.getTitle())
                                    .customView(promptsView, true)
                                    .positiveText("OK")
                                    .show();

                            getUsers.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(context, FormUsersActivity.class);
                                    int pos = getAdapterPosition();
                                    Form form = forms.get(pos);
                                    intent.putExtra("form", (Parcelable) form);
                                    context.startActivity(intent);
                                }
                            });
                        }
                    }
                }
            });



        }

        public void getFormCheckPoints(Form form, final TextView textView, final ProgressBar progressBar) {
            formService.getFormCheckpoints(form.getFormCode()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200) {
                        try {
                            progressBar.setVisibility(View.GONE);
                            JsonElement jsonElement = new JsonParser().parse(response.body().string());
                            JsonObject jsonObject = jsonElement.getAsJsonObject().get("formCheckPoint").getAsJsonObject();
                            Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                            textView.setText("FormCheckPoints: \n\n");
                            for (Map.Entry<String, JsonElement> entry : entrySet) {
                                textView.append(entry.getKey()+":\n\t"+entry.getValue()+"\n\n");
                                formCheckpoints.put(entry.getKey(), jsonObject.get(entry.getKey()).getAsString());
                            }

                        } catch (IOException e) {
                            progressBar.setVisibility(View.GONE);
                            e.printStackTrace();
                        }


                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "Form not found!", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(App.getApp(), "Something went wrong! Try Again..", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
