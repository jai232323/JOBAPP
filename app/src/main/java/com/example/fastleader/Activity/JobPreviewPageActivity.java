package com.example.fastleader.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fastleader.Adapter.JobfiedlvalueAdapter;
import com.example.fastleader.Model.JobData;
import com.example.fastleader.Model.Jobfiedlvalue;
import com.example.fastleader.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JobPreviewPageActivity extends AppCompatActivity {




    ImageView Job_Image;
    TextView Jobid,T_TaskDecription,JobStatus,CustomerName,CustomerMobileNo,ShopName,Area,
            Problem,Model,Type,Brand,
            JobCreateBy,JobAssignBy,JobCreateDate,JobCloseDate,BillAmount,ClosingBy,JobDeliveryDate,
            TestingBy,TestingAmount,Oldbalance,ReceivedAmount,BalanceAmount,NetBalance,ReceivedDate,
            CashMode;

    JobData JOB_INFO;

    RecyclerView JobFieldValueRecyclerView;

    private ArrayList<Jobfiedlvalue> list;
    private JobfiedlvalueAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_previce_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Preview Page");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        JOB_INFO = getIntent().getParcelableExtra("JOB_INFO");


        JobFieldValueRecyclerView = findViewById(R.id.JobFieldValueRecyclerView);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        JobFieldValueRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new JobfiedlvalueAdapter(this,list);
        JobFieldValueRecyclerView.setAdapter(adapter);

        getData();


        Job_Image = findViewById(R.id.Job_Image);
        Jobid = findViewById(R.id.Jobid);

        CustomerName = findViewById(R.id.CustomerName);
        CustomerMobileNo = findViewById(R.id.CustomerMobileNo);
        ShopName = findViewById(R.id.ShopName);
        Area = findViewById(R.id.Area);
        Problem = findViewById(R.id.Problem);
        Model = findViewById(R.id.Model);
        Type = findViewById(R.id.Type);
        Brand = findViewById(R.id.Brand);
        JobCreateBy = findViewById(R.id.JobCreateBy);
        JobAssignBy = findViewById(R.id.JobAssignBy);
        JobCreateDate = findViewById(R.id.JobCreateDate);
        JobStatus = findViewById(R.id.JobStatus);
        JobCloseDate = findViewById(R.id.JobCloseDate);
        BillAmount = findViewById(R.id.BillAmount);


        ClosingBy = findViewById(R.id.ClosingBy);
        JobDeliveryDate = findViewById(R.id.JobDeliveryDate);
        TestingBy = findViewById(R.id.TestingBy);
        TestingAmount = findViewById(R.id.TestingAmount);
        Oldbalance = findViewById(R.id.Oldbalance);
        ReceivedAmount = findViewById(R.id.ReceivedAmount);
        BalanceAmount = findViewById(R.id.BalanceAmount);
        NetBalance = findViewById(R.id.NetBalance);
        ReceivedDate = findViewById(R.id.ReceivedDate);
        CashMode = findViewById(R.id.CashMode);


        ClosingBy.setText(JOB_INFO.getClosingby());
        JobDeliveryDate.setText(JOB_INFO.getJobdeliverydate());
        TestingBy.setText(JOB_INFO.getTestingby());
        String Testingamount = String.valueOf(JOB_INFO.getTestingamount());
        TestingAmount.setText(Testingamount);
        String Oldbalance1 = String.valueOf(JOB_INFO.getOldbalance());
        Oldbalance.setText(Oldbalance1);
        String Receivedamount = String.valueOf(JOB_INFO.getReceivedamount());
        ReceivedAmount.setText(Receivedamount);
        String Balanceamount = String.valueOf(JOB_INFO.getBalanceamount());
        BalanceAmount.setText(Balanceamount);
        String Netbalance = String.valueOf(JOB_INFO.getNetbalance());
        NetBalance.setText(Netbalance);
        ReceivedDate.setText(JOB_INFO.getReceiveddate());
        CashMode.setText(JOB_INFO.getCashmode());



        //SetData
        Glide.with(this)
                .load(JOB_INFO.getJobimage())
                .into(Job_Image);
        Jobid.setText(JOB_INFO.getJobid());

        CustomerName.setText(JOB_INFO.getCustomername());
        CustomerMobileNo.setText(JOB_INFO.getCustomer_mobilenumber());
        ShopName.setText(JOB_INFO.getCustomer_ShopName());
        Area.setText(JOB_INFO.getCustomer_Area());
        Problem.setText(JOB_INFO.getProblem());
        Model.setText(JOB_INFO.getModel());
        Type.setText(JOB_INFO.getType());
        Brand.setText(JOB_INFO.getBrand());
        JobCreateBy.setText(JOB_INFO.getJobcreateby());
        JobAssignBy.setText(JOB_INFO.getJobassignby());
        JobCreateDate.setText(JOB_INFO.getJobcreatedate());
        JobStatus.setText(JOB_INFO.getJobstatus());
        JobCloseDate.setText(JOB_INFO.getJobclosedate());
        String billamount = String.valueOf(JOB_INFO.getBillamount());
        BillAmount.setText(billamount);


    }

    private void getData() {

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("JobFieldValue");
        reference1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Jobfiedlvalue data = dataSnapshot.getValue(Jobfiedlvalue.class);

               //     Integer jobid = JOB_INFO.getJobid();

                    String Jobid = String.valueOf(JOB_INFO.getJobid());

                    if (Jobid.equals(data.getTid())){
                        list.add(0, data);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(JobPreviewPageActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.job_edit_menu,menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (toggle.onOptionsItemSelected(item)) {
//            return true;
//        }
        switch (item.getItemId()){
            case R.id.Edit:


                Intent intent = new Intent(JobPreviewPageActivity.this,JobEditActivity.class);
                intent.putExtra("JOB_INFO",JOB_INFO);
                startActivity(intent);
                finish();

                //  startActivity(new Intent(JobPreviewPageActivity.this,JobEditActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}