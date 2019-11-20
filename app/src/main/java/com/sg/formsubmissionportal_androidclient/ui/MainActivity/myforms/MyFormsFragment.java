package com.sg.formsubmissionportal_androidclient.ui.MainActivity.myforms;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sg.formsubmissionportal_androidclient.R;

public class MyFormsFragment extends Fragment {

    private MyFormsViewModel myFormsViewModel;
    private TextView noFormText;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myFormsViewModel =
                ViewModelProviders.of(this).get(MyFormsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myforms, container, false);
        noFormText = root.findViewById(R.id.text_noforms_my_forms);
        recyclerView=root.findViewById(R.id.rv_my_forms);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}