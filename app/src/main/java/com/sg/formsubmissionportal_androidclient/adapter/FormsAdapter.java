package com.sg.formsubmissionportal_androidclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sg.formsubmissionportal_androidclient.R;
import com.sg.formsubmissionportal_androidclient.model.Form;
import com.sg.formsubmissionportal_androidclient.ui.FormStatusActivity;
import com.sg.formsubmissionportal_androidclient.ui.FormUsersActivity;
import com.sg.formsubmissionportal_androidclient.ui.MainActivity.MainActivity;

import java.util.ArrayList;

public class FormsAdapter extends RecyclerView.Adapter<FormsAdapter.MyFormsAdapterViewHolder> {

    private ArrayList<Form> forms;
    private String fragment;
    private Context context;

    public FormsAdapter(ArrayList<Form> forms,String fragment,Context context) {
        this.fragment=fragment;
        this.forms = forms;
        this.context=context;
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
            cardView=itemView.findViewById(R.id.form_item_CV);


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(fragment.equals("MyForms")){
                        if(MainActivity.role.equals("STUDENT")) {
                            Intent intent = new Intent(context, FormStatusActivity.class);
                            int pos = getAdapterPosition();
                            Form form = forms.get(pos);
                            intent.putExtra("form", (Parcelable) form);
                            context.startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(context, FormUsersActivity.class);
                            int pos = getAdapterPosition();
                            Form form = forms.get(pos);
                            intent.putExtra("form", (Parcelable) form);
                            context.startActivity(intent);
                        }
                    }

                    if(fragment.equals("AllForms")){

                    }
                }
            });

        }
    }
}
