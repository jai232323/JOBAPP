package com.example.fastleader.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastleader.Adapter.TaskAdapter;
import com.example.fastleader.Model.CustomerData;
import com.example.fastleader.Model.JobData;
import com.example.fastleader.Model.TaskData;
import com.example.fastleader.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowJobDetailAssignbyActivity extends AppCompatActivity {


    CustomerData CUSTOMER_INFO;




    private ArrayList<JobData> list;
    private TaskAdapter adapter;

    RecyclerView TaskRecyclerView;
    LinearLayout no_jobs;

    String U_MobileNumber1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_job_assignby);


        CUSTOMER_INFO = getIntent().getParcelableExtra("CUSTOMER_INFO");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Job Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        TaskRecyclerView =findViewById(R.id.TaskRecyclerView);
        no_jobs = findViewById(R.id.no_jobs);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(ShowJobDetailAssignbyActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        TaskRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new TaskAdapter(ShowJobDetailAssignbyActivity.this,list);
        TaskRecyclerView.setAdapter(adapter);


        SharedPreferences prefs3 =ShowJobDetailAssignbyActivity.this.
                getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        U_MobileNumber1 = prefs3.getString("U_MobileNumber", "none");

        getUserJobData();



    }

    private void getUserJobData() {


        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Tasks");

        //   Query query = reference1.orderByChild("Jobcreatedate").limitToFirst(10);

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


                        Log.i("data",data.toString()+"...");


//
//                        if (CUSTOMER_INFO.getC_CustomerName().equals(data.getC_CustomerName())){
//                            list.add(0, data);
//                            adapter.notifyDataSetChanged();
//                        }


                        adapter.notifyDataSetChanged();


                    }
                    //  Collections.reverse(list);
                    adapter.notifyDataSetChanged();



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShowJobDetailAssignbyActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}