package com.sg.formsubmissionportal_androidclient.ui.MainActivity.myforms;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sg.formsubmissionportal_androidclient.R;
import com.sg.formsubmissionportal_androidclient.adapter.FormsAdapter;
import com.sg.formsubmissionportal_androidclient.model.Form;

import java.util.ArrayList;

public class MyFormsFragment extends Fragment {

    private MyFormsViewModel myFormsViewModel;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FormsAdapter formsAdapter;
    private ProgressBar progressBar;
    private ArrayList<Form> formsList;
    private TextView textView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myFormsViewModel =
                ViewModelProviders.of(this).get(MyFormsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myforms, container, false);
        myFormsViewModel.setText();
        swipeRefreshLayout = getActivity().findViewById(R.id.swipeRefreshLayoutMain);
        progressBar = getActivity().findViewById(R.id.progressBarMain);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_my_forms);
        textView = view.findViewById(R.id.text_noforms_my_forms);
        textView.setText(myFormsViewModel.getText().getValue());
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        myFormsViewModel.getMyForms().observe(MyFormsFragment.this, new Observer<ArrayList<Form>>() {
            @Override
            public void onChanged(ArrayList<Form> forms) {
                formsList = forms;
                loadView();
            }

        });
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.DKGRAY, Color.RED, Color.GREEN, Color.MAGENTA, Color.BLACK, Color.CYAN);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ArrayList<Form> forms=new ArrayList<>();
                forms=myFormsViewModel.getMyForms().getValue();
                if(forms!=null){
                    formsList=forms;
                    loadView();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    public void loadView(){
        formsAdapter = new FormsAdapter(formsList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(formsAdapter);
        if (formsList == null) {
            progressBar.setVisibility(View.INVISIBLE);
            progressBar.setIndeterminate(false);
            textView.setVisibility(View.VISIBLE);
        } else {
            if (formsList.size() == 0) {
                progressBar.setVisibility(View.INVISIBLE);
                progressBar.setIndeterminate(false);
                textView.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.INVISIBLE);
                progressBar.setIndeterminate(false);
                textView.setVisibility(View.INVISIBLE);
            }
        }
    }


}