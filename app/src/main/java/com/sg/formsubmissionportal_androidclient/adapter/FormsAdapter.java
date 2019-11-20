package com.sg.formsubmissionportal_androidclient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sg.formsubmissionportal_androidclient.R;
import com.sg.formsubmissionportal_androidclient.model.Form;

import java.util.ArrayList;

public class FormsAdapter extends RecyclerView.Adapter<FormsAdapter.MyFormsAdapterViewHolder> {

    private ArrayList<Form> forms;


    public FormsAdapter(ArrayList<Form> forms) {
        this.forms = forms;
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

        public MyFormsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_form_title);
            department = itemView.findViewById(R.id.item_form_department);
            formCode = itemView.findViewById(R.id.item_form_formCode);
        }
    }
}
