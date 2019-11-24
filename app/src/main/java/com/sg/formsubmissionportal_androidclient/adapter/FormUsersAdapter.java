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
import com.sg.formsubmissionportal_androidclient.model.FormDetail;
import com.sg.formsubmissionportal_androidclient.model.User;
import com.sg.formsubmissionportal_androidclient.ui.FormStatusActivity;
import com.sg.formsubmissionportal_androidclient.ui.FormUsersActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FormUsersAdapter extends RecyclerView.Adapter<FormUsersAdapter.FormUsersAdapterViewHolder> {

    private ArrayList<FormDetail> formDetails;
    private ArrayList<User> users;
    private Context context;
    private Form form;

    public FormUsersAdapter(ArrayList<FormDetail> formDetails, ArrayList<User> users,Form form,Context context) {
        this.formDetails = formDetails;
        this.users=users;
        this.form=form;
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
            cardView=itemView.findViewById(R.id.form_user_CV);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FormStatusActivity.class);
                    int pos = getAdapterPosition();
                    FormDetail formDetail = formDetails.get(pos);
                    intent.putExtra("formDetails", (Parcelable) formDetail);
                    intent.putExtra("form",(Parcelable) form);
                    User user=null;
                    for(User u:users){
                        if(u.getEmail().equals(formDetail.getEmail())){
                            user=u;
                        }
                    }
                    intent.putExtra("user",(Parcelable)user);
                    context.startActivity(intent);
                }
            });
        }
    }
}
