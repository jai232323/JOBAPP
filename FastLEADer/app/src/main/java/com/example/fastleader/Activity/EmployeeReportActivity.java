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
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fastleader.Adapter.TaskAdapter;
import com.example.fastleader.Model.JobData;
import com.example.fastleader.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class EmployeeReportActivity extends AppCompatActivity {


    Spinner EmployeeName;

    DatabaseReference reference  = FirebaseDatabase.getInstance().getReference();

    private ArrayList<JobData> list;
    private TaskAdapter adapter;
    ArrayList<String> arrayList = new ArrayList<>();

    Button StartDate;
    DatePickerDialog.OnDateSetListener onDateSetListener1;
    DatePickerDialog.OnDateSetListener onDateSetListener2;
    Button EndDate;
    Button Submit;
    RecyclerView EmployeeRecyclerView;
    LinearLayout no_jobs;

    ProgressDialog pd ;

    String startdate,enddate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_report);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Employee Report");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pd = new ProgressDialog(EmployeeReportActivity.this);

        EmployeeName = findViewById(R.id.EmployeeName);



        reference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot items : snapshot.getChildren()){
                    arrayList.add(items.child("Uname").getValue(String.class));
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EmployeeReportActivity
                        .this,
                        com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
                        arrayList);
                EmployeeName.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EmployeeReportActivity.this,"Something Issues",Toast.LENGTH_SHORT).show();
            }
        });

        StartDate = findViewById(R.id.StartDate);
        StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EmployeeReportActivity.this,
                        onDateSetListener1,year,month,day);
                datePickerDialog.show();
            }
        });
        onDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth + "/" +month+"/"+year;
                StartDate.setText(date);
            }
        };
        EndDate = findViewById(R.id.EndDate);
        EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EmployeeReportActivity.this,
                        onDateSetListener2,year,month,day);
                datePickerDialog.show();
            }
        });
        onDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth + "/" +month+"/"+year;
                EndDate.setText(date);
            }
        };
        Submit = findViewById(R.id.Submit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEmployeerData();
            }
        });

        EmployeeRecyclerView = findViewById(R.id.EmployeeRecyclerView);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(
                EmployeeReportActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        EmployeeRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new TaskAdapter(EmployeeReportActivity.this,list);
        EmployeeRecyclerView.setAdapter(adapter);
        no_jobs = findViewById(R.id.no_jobs);

    }

    private void getEmployeerData() {
        pd.setMessage("Loading...");
        pd.show();

        startdate = StartDate.getText().toString();
        enddate = EndDate.getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Jobs");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                if (!snapshot.exists()){
                    pd.dismiss();
                    no_jobs.setVisibility(View.VISIBLE);
                    EmployeeRecyclerView.setVisibility(View.INVISIBLE);
                }else {
                    pd.dismiss();
                    no_jobs.setVisibility(View.INVISIBLE);
                    EmployeeRecyclerView.setVisibility(View.VISIBLE);

                    String Close = "Close";

                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                        pd.dismiss();
                        JobData data = dataSnapshot1.getValue(JobData.class);

                      //  SimpleDateFormat dfDate  = new SimpleDateFormat("dd-mm-yyyy");



                        if (EmployeeName.getSelectedItem().toString().equals(data.getClosingby())
                                && "Close".equals(data.getJobstatus())
                                && startdate.equals(data.getJobcreatedate())
                                || enddate.equals(data.getJobclosedate())){

                            list.add(0, data);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        Query query = FirebaseDatabase.getInstance().getReference("Jobs");
//
//        //dailyservicedate
//        query.orderByChild("Jobcreatedate").equalTo(startdate).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Query query1 = FirebaseDatabase.getInstance().getReference("Jobs");
//                query1.orderByChild("Jobclosedate").equalTo(enddate).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        Query query2 = FirebaseDatabase.getInstance().getReference("Jobs");
//                        query2.orderByChild("Jobstatus").equalTo("Close").addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                Query query3 = FirebaseDatabase.getInstance().getReference("Jobs");
//                                query3.orderByChild("Closingby").
//                                        equalTo(EmployeeName.getSelectedItem().toString()).
//                                        addValueEventListener(new ValueEventListener() {
//                                            @SuppressLint("NotifyDataSetChanged")
//                                            @Override
//                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                                list.clear();
//
//                                                if (!snapshot.exists()){
//                                                    pd.dismiss();
//                                                    no_jobs.setVisibility(View.VISIBLE);
//                                                    EmployeeRecyclerView.setVisibility(View.INVISIBLE);
//                                                }else {
//                                                    pd.dismiss();
//                                                    no_jobs.setVisibility(View.INVISIBLE);
//                                                    EmployeeRecyclerView.setVisibility(View.VISIBLE);
//
//                                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//
//                                                        pd.dismiss();
//                                                        JobData data = dataSnapshot.getValue(JobData.class);
//                                                        list.add(0, data);
//                                                        adapter.notifyDataSetChanged();
//
//                                                    }
//                                                    //  Collections.reverse(list);
//                                                    adapter.notifyDataSetChanged();
//
//
//                                                }
//
//
//                                            }
//
//                                            @Override
//                                            public void onCancelled(@NonNull DatabaseError error) {
//                                                Toast.makeText(EmployeeReportActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//



    }
}