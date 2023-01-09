package com.example.fastleader.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fastleader.Adapter.TaskAdapter;
import com.example.fastleader.Model.CustomerData;
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

public class ShowCustomerJobActivity extends AppCompatActivity {

    CustomerData CUSTOMER_INFO;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_customer_job);

        CUSTOMER_INFO = getIntent().getParcelableExtra("CUSTOMER_INFO");


        fab = findViewById(R.id.fab);
        SelectDate = findViewById(R.id.SelectDate);
        search = findViewById(R.id.search);
        no_jobs = findViewById(R.id.no_jobs);
        cleardata = findViewById(R.id.cleardata);

        SearchJobid = findViewById(R.id.SearchJobid);

        SharedPreferences prefs1 = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        Tid=prefs1.getString("Tid","none");

        TaskRecyclerView = findViewById(R.id.TaskRecyclerView);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(ShowCustomerJobActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        TaskRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new TaskAdapter(ShowCustomerJobActivity.this,list);
        TaskRecyclerView.setAdapter(adapter);

        getCustomerJobData();

    }

    private void getCustomerJobData() {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Jobs");

        Query query = reference1.orderByChild("Jobcreatedate").limitToFirst(10);

        reference1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                if (!snapshot.exists()){
                    no_jobs.setVisibility(View.VISIBLE);
                    TaskRecyclerView.setVisibility(View.INVISIBLE);
                }else {
                    no_jobs.setVisibility(View.INVISIBLE);
                    TaskRecyclerView.setVisibility(View.VISIBLE);

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        JobData data = dataSnapshot.getValue(JobData.class);

                        if (CUSTOMER_INFO.getC_ID().equals(data.getCustomer_id())){

                            list.add(0, data);
                            adapter.notifyDataSetChanged();
                        }


                    }
                    //  Collections.reverse(list);
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
                    });

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShowCustomerJobActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void filter(String text) {
        ArrayList<JobData> filterlist = new ArrayList<>();
        for (JobData item : list){

            String Jobid = String.valueOf(item.getJobid());
            if (Jobid.toLowerCase().contains(text.toLowerCase())){

                filterlist.add(item);

            }
            adapter.Filteredlist(filterlist);
            adapter.notifyDataSetChanged();

        }
    }
}