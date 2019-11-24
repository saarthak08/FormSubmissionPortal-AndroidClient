package com.sg.formsubmissionportal_androidclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sg.formsubmissionportal_androidclient.R;
import com.sg.formsubmissionportal_androidclient.model.FormDetail;
import com.sg.formsubmissionportal_androidclient.model.User;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FormUsersAdapter extends RecyclerView.Adapter<FormUsersAdapter.FormUsersAdapterViewHolder> {

    private ArrayList<FormDetail> formDetails;
    private ArrayList<User> users;
    private Context context;

    public FormUsersAdapter(ArrayList<FormDetail> formDetails, ArrayList<User> users,Context context) {
        this.formDetails = formDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public FormUsersAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_form_users, parent, false);
        return new FormUsersAdapterViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull FormUsersAdapterViewHolder holder, int position) {
        holder.userName.setText(formDetails.get(position).getFirstName()+" "+formDetails.get(position).getLastName());
        holder.email.setText(formDetails.get(position).getEmail());
        if(formDetails.get(position).getStatus()){
            holder.status.setText("Form Status: Active");
        }
        else{
            holder.status.setText("Form Status: Rejected");
        }
    }

    @Override
    public int getItemCount() {
        return formDetails == null ? 0 : formDetails.size();
    }


    public class FormUsersAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView userName;
        private TextView email;
        private TextView status;
        private CardView cardView;

        public FormUsersAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.item_user_name);
            email=itemView.findViewById(R.id.item_user_email);
            status=itemView.findViewById(R.id.item_formstatus);
            cardView=itemView.findViewById(R.id.form_item_CV);
        }
    }
}
