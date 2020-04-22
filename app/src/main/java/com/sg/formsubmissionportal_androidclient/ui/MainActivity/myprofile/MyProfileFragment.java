package com.sg.formsubmissionportal_androidclient.ui.MainActivity.myprofile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sg.formsubmissionportal_androidclient.R;
import com.sg.formsubmissionportal_androidclient.ui.MainActivity.MainActivity;

public class MyProfileFragment extends Fragment {

    private TextView firstName;
    private TextView lastName;
    private TextView email;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView idNumber;
    private TextView role;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_myprofile, container, false);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firstName=view.findViewById(R.id.profile_firstName);
        lastName=view.findViewById(R.id.profile_lastName);
        email=view.findViewById(R.id.profile_email);
        swipeRefreshLayout = getActivity().findViewById(R.id.swipeRefreshLayoutMain);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        idNumber=view.findViewById(R.id.profile_id);
        role=view.findViewById(R.id.profile_role);
        firstName.setText("First Name: "+ MainActivity.firstName);
        lastName.setText("Last Name: "+MainActivity.lastName);
        email.setText("Email: "+MainActivity.email);
        idNumber.setText("ID: "+MainActivity.userid);
        role.setText("Role: "+MainActivity.role);
    }
}