package com.example.fastleader.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fastleader.Adapter.TaskAdapter;
import com.example.fastleader.Model.JobData;
import com.example.fastleader.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DailySerViceReportActivity extends AppCompatActivity {

    MaterialButton DailySerViceDate;
    Button Submit;
    RecyclerView ServiceRecyclerView;
    LinearLayout no_jobs;



    private ArrayList<JobData> list;
    private TaskAdapter adapter;

    DatePickerDialog.OnDateSetListener onDateSetListener1;

    ProgressDialog pd;

    String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_ser_vice_report);

        pd = new ProgressDialog(DailySerViceReportActivity.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Daily Service Report");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DailySerViceDate = findViewById(R.id.DailySerViceDate);
        Submit = findViewById(R.id.Submit);

        ServiceRecyclerView = findViewById(R.id.ServiceRecyclerView);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(DailySerViceReportActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        ServiceRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new TaskAdapter(DailySerViceReportActivity.this,list);
        ServiceRecyclerView.setAdapter(adapter);
        no_jobs = findViewById(R.id.no_jobs);

        currentDate = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(new Date());
        DailySerViceDate.setText(currentDate);

        getDailyServiceData();

        DailySerViceDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(DailySerViceReportActivity.this,
                        onDateSetListener1,year,month,day);
                datePickerDialog.show();
            }
        });
        onDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth + "/" +month+"/"+year;
                DailySerViceDate.setText(date);
                getDailyServiceData();
            }
        };

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


             //   getDailyServiceData();
            }

//            private void getDailyServiceData() {
//                pd.setMessage("Loading...");
//                pd.show();
////                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Jobs");
////
////                Query query = reference1.orderByChild("Jobcreatedate").limitToFirst(10);
//
//
//                String dailyservicedate = DailySerViceDate.getText().toString();
//
//
//
//                Query query = FirebaseDatabase.getInstance().getReference("Jobs");
//
//                //dailyservicedate
//                query.orderByChild("Jobcreatedate").equalTo(dailyservicedate).
//                        addValueEventListener(new ValueEventListener() {
//                    @SuppressLint("NotifyDataSetChanged")
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        list.clear();
//
//                        if (!snapshot.exists()){
//                            pd.dismiss();
//                            no_jobs.setVisibility(View.VISIBLE);
//                            ServiceRecyclerView.setVisibility(View.INVISIBLE);
//                        }else {
//                            pd.dismiss();
//                            no_jobs.setVisibility(View.INVISIBLE);
//                            ServiceRecyclerView.setVisibility(View.VISIBLE);
//
//                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//
//                                pd.dismiss();
//                                JobData data = dataSnapshot.getValue(JobData.class);
//                                list.add(0, data);
//                                adapter.notifyDataSetChanged();
//
//                            }
//                            //  Collections.reverse(list);
//                            adapter.notifyDataSetChanged();
//
//
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(DailySerViceReportActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
        });




    }

    private void getDailyServiceData() {

        pd.setMessage("Loading...");
        pd.show();

        String dailyservicedate = DailySerViceDate.getText().toString();

        Query query = FirebaseDatabase.getInstance().getReference("Jobs");

        //dailyservicedate
        query.orderByChild("Jobcreatedate").equalTo(dailyservicedate).
                addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();

                        if (!snapshot.exists()){
                            pd.dismiss();
                            no_jobs.setVisibility(View.VISIBLE);
                            ServiceRecyclerView.setVisibility(View.INVISIBLE);
                        }else {
                            pd.dismiss();
                            no_jobs.setVisibility(View.INVISIBLE);
                            ServiceRecyclerView.setVisibility(View.VISIBLE);

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                pd.dismiss();
                                JobData data = dataSnapshot.getValue(JobData.class);
                                list.add(0, data);
                                adapter.notifyDataSetChanged();

                            }
                            //  Collections.reverse(list);
                            adapter.notifyDataSetChanged();


                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(DailySerViceReportActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}