package com.example.fastleader.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.util.ArrayList;

public class CustomerReportActivity extends AppCompatActivity {


    EditText Search;
    Button Submit;

    RecyclerView CustomerRecyclerView;
    LinearLayout no_jobs;



    private ArrayList<JobData> list;
    private TaskAdapter adapter;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_report);

        pd = new ProgressDialog(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Customer Report");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Search = findViewById(R.id.Search);

        CustomerRecyclerView = findViewById(R.id.CustomerRecyclerView);


        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(CustomerReportActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        CustomerRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new TaskAdapter(CustomerReportActivity.this,list);
        CustomerRecyclerView.setAdapter(adapter);
        no_jobs = findViewById(R.id.no_jobs);



        Search.addTextChangedListener(new TextWatcher() {
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

     //   getCustomerData();

        Submit = findViewById(R.id.Submit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Customer_mn = Search.getText().toString();
                getCustomerData1(Customer_mn);
            }
        });


    }

    private void getCustomerData1(String customer_mn) {

        Query query = FirebaseDatabase.getInstance().getReference("Jobs");
        query.orderByChild("Customer_mobilenumber").equalTo(customer_mn)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                if (!snapshot.exists()){
                    pd.dismiss();
                    no_jobs.setVisibility(View.VISIBLE);
                    CustomerRecyclerView.setVisibility(View.INVISIBLE);
                }else {
                    pd.dismiss();
                    no_jobs.setVisibility(View.INVISIBLE);
                    CustomerRecyclerView.setVisibility(View.VISIBLE);

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

            }
        });
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

    private void getCustomerData() {
        pd.setMessage("Loading...");
        pd.show();


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Jobs");
                reference.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();

                        if (!snapshot.exists()){
                            pd.dismiss();
                            no_jobs.setVisibility(View.VISIBLE);
                            CustomerRecyclerView.setVisibility(View.INVISIBLE);
                        }else {
                            pd.dismiss();
                            no_jobs.setVisibility(View.INVISIBLE);
                            CustomerRecyclerView.setVisibility(View.VISIBLE);

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
                        Toast.makeText(CustomerReportActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}