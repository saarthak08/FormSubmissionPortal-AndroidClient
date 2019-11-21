package com.sg.formsubmissionportal_androidclient.ui.MainActivity.allforms;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
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
import com.sg.formsubmissionportal_androidclient.ui.MainActivity.myforms.MyFormsFragment;
import com.sg.formsubmissionportal_androidclient.ui.MainActivity.myforms.MyFormsViewModel;

import java.util.ArrayList;

public class AllFormsFragment extends Fragment {

    private AllFormsViewModel allFormsViewModel;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FormsAdapter formsAdapter;
    private ProgressBar progressBar;
    private ArrayList<Form> formsList;
    private TextView textView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        allFormsViewModel =
                ViewModelProviders.of(this).get(AllFormsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_allforms, container, false);
        allFormsViewModel.setText();
        swipeRefreshLayout = getActivity().findViewById(R.id.swipeRefreshLayoutMain);
        progressBar = getActivity().findViewById(R.id.progressBarMain);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_all_forms);
        textView = view.findViewById(R.id.text_noforms_all_forms);
        textView.setText(allFormsViewModel.getText().getValue());
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        allFormsViewModel.getAllForms().observe(AllFormsFragment.this, new Observer<ArrayList<Form>>() {
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
                ArrayList<Form> forms = new ArrayList<>();
                forms = allFormsViewModel.getAllForms().getValue();
                formsList = forms;
                loadView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        checkList();
    }

    public void loadView() {
        formsAdapter = new FormsAdapter(formsList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(formsAdapter);
        checkList();
    }

    public void checkList()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(formsList==null){
                    textView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    progressBar.setIndeterminate(false);
                }
                else{
                    if(formsList.size()==0){
                        textView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        progressBar.setIndeterminate(false);
                    }
                    else{
                        textView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        textView.setVisibility(View.INVISIBLE);
                    }
                }
            }
        },500);
    }
}