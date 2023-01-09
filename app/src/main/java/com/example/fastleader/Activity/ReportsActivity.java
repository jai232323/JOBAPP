package com.example.fastleader.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fastleader.R;
import com.google.android.material.card.MaterialCardView;

public class ReportsActivity extends AppCompatActivity {

    MaterialCardView ServiceReport;
    MaterialCardView EmployeeReport;
    MaterialCardView CustomerReport;
    MaterialCardView BillingReport;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Reports");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ServiceReport = findViewById(R.id.ServiceReport);
        ServiceReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportsActivity.this,DailySerViceReportActivity.class);
                startActivity(intent);
            }
        });
        EmployeeReport = findViewById(R.id.EmployeeReport);
        EmployeeReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportsActivity.this,EmployeeReportActivity.class);
                startActivity(intent);
            }
        });
        CustomerReport = findViewById(R.id.CustomerReport);
        CustomerReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportsActivity.this,CustomerReportActivity.class);
                startActivity(intent);
            }
        });
        BillingReport = findViewById(R.id.BillingReport);
        BillingReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportsActivity.this,BillingReportActivity.class);
                startActivity(intent);
            }
        });
    }
}