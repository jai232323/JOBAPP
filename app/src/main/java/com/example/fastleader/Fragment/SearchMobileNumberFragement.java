package com.example.fastleader.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fastleader.Activity.AddJobActivity;
import com.example.fastleader.Adapter.TaskAdapter;
import com.example.fastleader.Model.JobData;
import com.example.fastleader.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class SearchMobileNumberFragement extends Fragment {
    ExtendedFloatingActionButton fab;

    RecyclerView TaskRecyclerView;

    private ArrayList<JobData> list;
    private TaskAdapter adapter;

    Button SelectDate;
    ImageView search,cleardata;

    LinearLayout no_jobs;

    DatePickerDialog.OnDateSetListener onDateSetListener3;


    String Tid;


    EditText SearchJobid;
    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_mobile_number_fragement, container, false);

        pd = new ProgressDialog(getContext());

        fab = view.findViewById(R.id.fab);
        SelectDate = view.findViewById(R.id.SelectDate);
        search = view.findViewById(R.id.search);
        no_jobs = view.findViewById(R.id.no_jobs);
        cleardata = view.findViewById(R.id.cleardata);

        SearchJobid = view.findViewById(R.id.SearchJobid);





        SharedPreferences prefs1 = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        Tid=prefs1.getString("Tid","none");

        TaskRecyclerView = view.findViewById(R.id.TaskRecyclerView);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        TaskRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new TaskAdapter(getContext(),list);
        TaskRecyclerView.setAdapter(adapter);

        getTaskData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), AddJobActivity.class));

            }
        });
        TaskRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy<0 && !fab.isShown())
                    fab.show();
                else if(dy>0 && fab.isShown())
                    fab.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        return view;
    }

    private void getTaskData() {

        pd.setMessage("Loading...");
        pd.show();

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Jobs");

        Query query = reference1.orderByChild("Jobcreatedate").limitToFirst(10);

        reference1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                if (!snapshot.exists()){
                    pd.dismiss();
                    no_jobs.setVisibility(View.VISIBLE);
                    TaskRecyclerView.setVisibility(View.INVISIBLE);
                }else {
                    pd.dismiss();
                    no_jobs.setVisibility(View.INVISIBLE);
                    TaskRecyclerView.setVisibility(View.VISIBLE);

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        pd.dismiss();
                        JobData data = dataSnapshot.getValue(JobData.class);
                        list.add(0, data);
                        adapter.notifyDataSetChanged();

                    }
                    Collections.reverse(list);
                    adapter.notifyDataSetChanged();

                    SearchJobid.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            filter(editable.toString());
                        }

                        private void filter(String text) {
                            ArrayList<JobData> filterlist = new ArrayList<>();
                            for (JobData item : list){

                                String mn = item.getCustomer_mobilenumber();

                                if (mn.toLowerCase().
                                        contains(text.toString())){
                                    filterlist.add(item);
                                }

                                adapter.Filteredlist(filterlist);
                                adapter.notifyDataSetChanged();
        }
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

//    private void filter(String text) {
//        ArrayList<JobData> filterlist = new ArrayList<>();
//        for (JobData item : list){
//
//            if (item.getCustomer_mobilenumber().toLowerCase().contains(text.toLowerCase())){
//                filterlist.add(item);
//            }
//
//            adapter.Filteredlist(filterlist);
//            adapter.notifyDataSetChanged();
//
//        }
//    }
}