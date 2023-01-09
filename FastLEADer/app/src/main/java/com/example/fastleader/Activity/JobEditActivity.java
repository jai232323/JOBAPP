package com.example.fastleader.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fastleader.Model.JobData;
import com.example.fastleader.Model.LabelData;
import com.example.fastleader.Model.Labelvalue;
import com.example.fastleader.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class JobEditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{



    JobData JOB_INFO;
    TextView JobId;

    final Random myRandom = new Random();
    LinearLayout mainContainer;



    Button C_CustomerName;
    String CustomerName;


    SearchableSpinner ProblemSpin,ModelSpin,TypeSpin,BrandSpin;
    ImageButton AddProblem,AddModel,AddType,AddBrand;

    SearchableSpinner JobStatus;
    ImageButton AddJobStatus;
    DatabaseReference JobStatusRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference ProblemRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference ModelRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference TypeRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference BrandRef = FirebaseDatabase.getInstance().getReference();
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> arrayListProblem = new ArrayList<>();
    private ArrayList<String> arrayListModel = new ArrayList<>();
    private ArrayList<String> arrayListType = new ArrayList<>();
    private ArrayList<String> arrayListBrand = new ArrayList<>();

    Button JobCreateDate;
    EditText ClosingBy,ClosingAmount;

    Button JobCloseDate,JobDeliveryDate;

    EditText TestingBy,TestingAmount;

    //AMount Details
    EditText OldBalance,BillAmount,ReceivedAmount,BalanceAmount,NetBalance;

    Button ReceivedDate;
    SearchableSpinner CashMode;
    EditText JobCreateBy;
    SearchableSpinner JobAssignBy;
    MaterialCardView MC_CustomerImage;
    ImageView Job_Image;
    TextView JobImageText;

    Button Edit,Delete;

    String jobid;
    String Jobcreatedate,Jobclosedate,Jobdeliverydate,Receiveddate,Closingby,Testingby,Jobcreateby;
    Double Testingamount,Closingamount,Oldbalance,Billamount,Receivedamount,Balanceamount,Netbalance;


    String downloadUrl="";
    Bitmap bitmap;
    private final int REQ = 1;
    StorageReference storageReference;


    ProgressDialog pd;



    DatePickerDialog.OnDateSetListener onDateSetListener1;
    DatePickerDialog.OnDateSetListener onDateSetListener2;
    DatePickerDialog.OnDateSetListener onDateSetListener3;
    DatePickerDialog.OnDateSetListener onDateSetListener4;


    DatabaseReference reference ;
    DatabaseReference referenceUsers = FirebaseDatabase.getInstance().getReference();
    String U_Id,U_MobileNumber1;
    String Uname;

    ArrayList<String> arrayListUser = new ArrayList<>();

    DatabaseReference referenceJF;

    String JF_Id;

    String jobid1 = String.valueOf(5000);


    DatabaseReference referenceUser;

    String cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_edit);

        JOB_INFO = getIntent().getParcelableExtra("JOB_INFO");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Job");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pd = new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference("Jobs");

        //Auto Increment for Jobid
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                if (snapshot.exists()){
//                    jobid1 = String.valueOf(snapshot.getChildrenCount());
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        referenceJF = FirebaseDatabase.getInstance().getReference("JobFieldValue");
        JF_Id = referenceJF.push().getKey();
        storageReference= FirebaseStorage.getInstance().getReference();

        SharedPreferences prefs1 = getSharedPreferences("PREFS", MODE_PRIVATE);
        U_Id=prefs1.getString("U_Id","none");


        Query query = FirebaseDatabase.getInstance().getReference("Users");
//        query.orderByChild("Uid").equalTo()



        SharedPreferences prefs3 = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        U_MobileNumber1 = prefs3.getString("U_MobileNumber", "none");

        SharedPreferences prefs2 = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        Uname= prefs2.getString("Uname","none");

        jobid = String.valueOf(myRandom.nextInt(500000));

        JobId = findViewById(R.id.JobId);
        JobId.setText("JOB ID : "+JOB_INFO.getJobid());


        mainContainer=findViewById(R.id.mainContainer);
        Log.i("mainContainer>>>",mainContainer+"xxx");


        C_CustomerName = findViewById(R.id.C_CustomerName);
        C_CustomerName.setText(JOB_INFO.getCustomername());
        C_CustomerName.setHint(JOB_INFO.getCustomername());
//        C_CustomerName.setText("Get Customer Leave");

//        C_CustomerName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(JobEditActivity.this,
//                        ShowCustomerNameActivity.class);
//                // startActivity(intent);
//                startActivityForResult(intent,2001);
//            }
//        });

        CustomerName = getIntent().getStringExtra("CustomerName");
        C_CustomerName.setText(CustomerName);
         cn = C_CustomerName.getText().toString();


        ProblemSpin = findViewById(R.id.ProblemSpin);
        ProblemRef.child("Problems").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListProblem.clear();

                arrayListProblem.add(JOB_INFO.getProblem());
                for (DataSnapshot items : snapshot.getChildren()){
                    arrayListProblem.add(items.child("Problemname").getValue(String.class));

                }


                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                        (JobEditActivity.this,
                                com.airbnb.lottie.R.layout.
                                        support_simple_spinner_dropdown_item,arrayListProblem);

                ProblemSpin.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(JobEditActivity.this,"Something Issues",Toast.LENGTH_SHORT).show();
            }
        });
        AddProblem = findViewById(R.id.AddProblem);
        AddProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(JobEditActivity.this);
                View view1 = inflater.inflate(R.layout.custome_add_problem,null);


                AlertDialog alertDialog = new AlertDialog.Builder(JobEditActivity.this)
                        .setView(view1)
                        .create();
                alertDialog.show();

                EditText AddProblem1 = view1.findViewById(R.id.AddProblem1);
                Button Save = view1.findViewById(R.id.Save);

                Save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProgressDialog pd = new ProgressDialog(JobEditActivity.this);
                        pd.setMessage("wait...");
                        pd.show();

                        String AddProblem2 = AddProblem1.getText().toString();

                        if (AddProblem2.isEmpty()){
                            AddProblem1.setError("Required Problem");
                            AddProblem1.requestFocus();
                        }else {

                            DatabaseReference ProblemRef = FirebaseDatabase.getInstance().
                                    getReference("Problems");

                            String Problemid = ProblemRef.push().getKey();

                            HashMap<String,Object> hashMap = new HashMap<>();
                            hashMap.put("Problemid",Problemid);
                            hashMap.put("Problemname",AddProblem2);

                            ProblemRef.child(Problemid).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    pd.dismiss();
                                    alertDialog.dismiss();

                                    Toast.makeText(JobEditActivity.this, "Problem\nAdded Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    alertDialog.dismiss();
                                    Toast.makeText(JobEditActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                    }
                });


            }
        });

        ModelSpin = findViewById(R.id.ModelSpin);
        ModelRef.child("Models").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListModel.clear();

                arrayListModel.add(JOB_INFO.getModel());
                for (DataSnapshot items : snapshot.getChildren()){
                    arrayListModel.add(items.child("Modelname").getValue(String.class));

                }


                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                        (JobEditActivity.this,
                                com.airbnb.lottie.R.layout.
                                        support_simple_spinner_dropdown_item,arrayListModel);

                ModelSpin.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(JobEditActivity.this,"Something Issues",Toast.LENGTH_SHORT).show();
            }
        });
        AddModel = findViewById(R.id.AddModel);
        AddModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(JobEditActivity.this);
                View view1 = inflater.inflate(R.layout.custome_add_model,null);


                AlertDialog alertDialog = new AlertDialog.Builder(JobEditActivity.this)
                        .setView(view1)
                        .create();
                alertDialog.show();

                EditText AddModel1 = view1.findViewById(R.id.AddModel1);
                Button Save = view1.findViewById(R.id.Save);

                Save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProgressDialog pd = new ProgressDialog(JobEditActivity.this);
                        pd.setMessage("wait...");
                        pd.show();

                        String AddModel2 = AddModel1.getText().toString();

                        if (AddModel2.isEmpty()){
                            AddModel1.setError("Required Job Status");
                            AddModel1.requestFocus();
                        }else {

                            DatabaseReference ModelRef = FirebaseDatabase.getInstance().
                                    getReference("Models");

                            String Modelid = ModelRef.push().getKey();

                            HashMap<String,Object> hashMap = new HashMap<>();
                            hashMap.put("Modelid",Modelid);
                            hashMap.put("Modelname",AddModel2);

                            ModelRef.child(Modelid).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    pd.dismiss();
                                    alertDialog.dismiss();

                                    Toast.makeText(JobEditActivity.this, "Models\nAdded Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    alertDialog.dismiss();
                                    Toast.makeText(JobEditActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                    }
                });


            }
        });

        TypeSpin = findViewById(R.id.TypeSpin);
        TypeRef.child("Types").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListType.clear();

                arrayListType.add(JOB_INFO.getType());
                for (DataSnapshot items : snapshot.getChildren()){
                    arrayListType.add(items.child("Typename").getValue(String.class));

                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                        (JobEditActivity.this,
                                com.airbnb.lottie.R.layout.
                                        support_simple_spinner_dropdown_item,arrayListType);

                TypeSpin.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(JobEditActivity.this,"Something Issues",Toast.LENGTH_SHORT).show();
            }
        });
        AddType = findViewById(R.id.AddType);
        AddType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(JobEditActivity.this);
                View view1 = inflater.inflate(R.layout.custome_add_type,null);


                AlertDialog alertDialog = new AlertDialog.Builder(JobEditActivity.this)
                        .setView(view1)
                        .create();
                alertDialog.show();

                EditText AddType1 = view1.findViewById(R.id.AddType1);
                Button Save = view1.findViewById(R.id.Save);

                Save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProgressDialog pd = new ProgressDialog(JobEditActivity.this);
                        pd.setMessage("wait...");
                        pd.show();

                        String AddType2 = AddType1.getText().toString();

                        if (AddType2.isEmpty()){
                            AddType1.setError("Required Type");
                            AddType1.requestFocus();
                        }else {

                            DatabaseReference TypeRef = FirebaseDatabase.getInstance().
                                    getReference("Types");

                            String Typeid = TypeRef.push().getKey();

                            HashMap<String,Object> hashMap = new HashMap<>();
                            hashMap.put("Typeid",Typeid);
                            hashMap.put("Typename",AddType2);

                            TypeRef.child(Typeid).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    pd.dismiss();
                                    alertDialog.dismiss();

                                    Toast.makeText(JobEditActivity.this, "Type\nAdded Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    alertDialog.dismiss();
                                    Toast.makeText(JobEditActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                    }
                });


            }
        });

        BrandSpin = findViewById(R.id.BrandSpin);
        BrandRef.child("Brands").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListBrand.clear();

                arrayListBrand.add(JOB_INFO.getBrand());
                for (DataSnapshot items : snapshot.getChildren()){
                    arrayListBrand.add(items.child("Brandname").getValue(String.class));

                }


                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                        (JobEditActivity.this,
                                com.airbnb.lottie.R.layout.
                                        support_simple_spinner_dropdown_item,arrayListBrand);

                BrandSpin.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(JobEditActivity.this,"Something Issues",Toast.LENGTH_SHORT).show();
            }
        });
        AddBrand = findViewById(R.id.AddBrand);
        AddBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(JobEditActivity.this);
                View view1 = inflater.inflate(R.layout.custome_add_brand,null);


                AlertDialog alertDialog = new AlertDialog.Builder(JobEditActivity.this)
                        .setView(view1)
                        .create();
                alertDialog.show();

                EditText AddBrand1 = view1.findViewById(R.id.AddBrand1);
                Button Save = view1.findViewById(R.id.Save);

                Save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProgressDialog pd = new ProgressDialog(JobEditActivity.this);
                        pd.setMessage("wait...");
                        pd.show();

                        String AddBrand2 = AddBrand1.getText().toString();

                        if (AddBrand2.isEmpty()){
                            AddBrand1.setError("Required Brand");
                            AddBrand1.requestFocus();
                        }else {

                            DatabaseReference TypeRef = FirebaseDatabase.getInstance().
                                    getReference("Brands");

                            String Brandid = TypeRef.push().getKey();

                            HashMap<String,Object> hashMap = new HashMap<>();
                            hashMap.put("Brandid",Brandid);
                            hashMap.put("Brandname",AddBrand2);

                            TypeRef.child(Brandid).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    pd.dismiss();
                                    alertDialog.dismiss();

                                    Toast.makeText(JobEditActivity.this, "Brand\nAdded Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    alertDialog.dismiss();
                                    Toast.makeText(JobEditActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                    }
                });


            }
        });

        JobStatus = findViewById(R.id.JobStatus);
        JobStatusRef.child("JobStatus").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();

                arrayList.add(JOB_INFO.getJobstatus());
                for (DataSnapshot items : snapshot.getChildren()){
                    arrayList.add(items.child("Job_statusname").getValue(String.class));

                }
                arrayList.add("Open");
                arrayList.add("Close");
                arrayList.add("Delivered");
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                        (JobEditActivity.this,
                                com.airbnb.lottie.R.layout.
                                        support_simple_spinner_dropdown_item,arrayList);

                JobStatus.setAdapter(arrayAdapter);

//                String [] rent_date = new String[]{JOB_INFO.getJobstatus(),"Open","Close","Delivered"};
//
//
////                JobStatus.setAdapter(new ArrayAdapter<String>(JobEditActivity.this,
////                        android.R.layout.simple_spinner_dropdown_item, arrayList ));
//
//                JobStatus.setAdapter(new ArrayAdapter<String>(JobEditActivity.this,
//                        android.R.layout.simple_spinner_dropdown_item, rent_date));
//
//                JobStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        //   RentType.getSelectedItem();
//                        JobStatus.getSelectedItem().toString();
//                        //  Double rent_type = Double.valueOf(RentType.getSelectedItem().toString());
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });


                //Toast.makeText(CreatePortionActivity.this, BuildingSpinner.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(JobEditActivity.this,"Something Issues",Toast.LENGTH_SHORT).show();
            }
        });
        AddJobStatus = findViewById(R.id.AddJobStatus);
        AddJobStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(JobEditActivity.this);
                View view1 = inflater.inflate(R.layout.custome_job_status,null);


                AlertDialog alertDialog = new AlertDialog.Builder(JobEditActivity.this)
                        .setView(view1)
                        .create();
                alertDialog.show();

                EditText AddJobStatus1 = view1.findViewById(R.id.AddJobStatus1);
                Button Save = view1.findViewById(R.id.Save);

                Save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProgressDialog pd = new ProgressDialog(JobEditActivity.this);
                        pd.setMessage("wait...");
                        pd.show();

                        String JobStatus1 = AddJobStatus1.getText().toString();

                        if (JobStatus1.isEmpty()){
                            AddJobStatus1.setError("Required Job Status");
                            AddJobStatus1.requestFocus();
                        }else {

                            DatabaseReference JobStatusRef = FirebaseDatabase.getInstance().
                                    getReference("JobStatus");

                            String JobStatusid = reference.push().getKey();

                            HashMap<String,Object> hashMap = new HashMap<>();
                            hashMap.put("Job_statusid",JobStatusid);
                            hashMap.put("Job_statusname",JobStatus1);

                            JobStatusRef.child(JobStatusid).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    pd.dismiss();
                                    alertDialog.dismiss();

                                    Toast.makeText(JobEditActivity.this, "Job Status\nAdded Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    alertDialog.dismiss();
                                    Toast.makeText(JobEditActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                    }
                });
            }
        });



        ClosingBy = findViewById(R.id.ClosingBy);
        ClosingBy.setText(JOB_INFO.getClosingby());
//        ClosingBy.setText(Uname);
        ClosingAmount = findViewById(R.id.ClosingAmount);
        String closingamount = String.valueOf(JOB_INFO.getClosingamount());
        ClosingAmount.setText(closingamount);
        JobCreateDate = findViewById(R.id.JobCreateDate);

        JobCreateDate.setText(JOB_INFO.getJobcreatedate());
//        JobCreateDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar calendar = Calendar.getInstance();
//                int year = calendar.get(Calendar.YEAR);
//                int month = calendar.get(Calendar.MONTH);
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(JobEditActivity.this,
//                        onDateSetListener4,year,month,day);
//                datePickerDialog.show();
//            }
//        });
//        onDateSetListener4 = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                month = month+1;
//                String date = dayOfMonth + "/" +month+"/"+year;
//                JobCreateDate.setText(date);
//            }
//        };



        JobCloseDate = findViewById(R.id.JobCloseDate);
        JobCloseDate.setText(JOB_INFO.getJobclosedate());
        JobCloseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(JobEditActivity.this,
                        onDateSetListener3,year,month,day);
                datePickerDialog.show();
            }
        });
        onDateSetListener3 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth + "/" +month+"/"+year;
                JobCloseDate.setText(date);
            }
        };

        JobDeliveryDate = findViewById(R.id.JobDeliveryDate);

        JobDeliveryDate.setText(JOB_INFO.getJobdeliverydate());
        JobDeliveryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(JobEditActivity.this,
                        onDateSetListener2,year,month,day);
                datePickerDialog.show();
            }
        });
        onDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth + "/" +month+"/"+year;
                JobDeliveryDate.setText(date);
            }
        };


        TestingBy = findViewById(R.id.TestingBy);
        TestingBy.setText(JOB_INFO.getTestingby());
        TestingAmount = findViewById(R.id.TestingAmount);
        String testingamount = String.valueOf(JOB_INFO.getTestingamount());
        TestingAmount.setText(testingamount);

        OldBalance = findViewById(R.id.OldBalance);
        String old_balance = String.valueOf(JOB_INFO.getOldbalance());
        OldBalance.setText(old_balance);

        BillAmount = findViewById(R.id.BillAmount);
        String bill_amount = String.valueOf(JOB_INFO.getBalanceamount());
        BillAmount.setText(bill_amount);

        ReceivedAmount = findViewById(R.id.ReceivedAmount);
        String received_amount = String.valueOf(JOB_INFO.getReceivedamount());
        ReceivedAmount.setText(received_amount);

        BalanceAmount = findViewById(R.id.BalanceAmount);
        String balance_amount = String.valueOf(JOB_INFO.getBalanceamount());
        BillAmount.setText(balance_amount);

        NetBalance = findViewById(R.id.NetBalance);
        String net_balance = String.valueOf(JOB_INFO.getNetbalance());
        NetBalance.setText(net_balance);

        ReceivedDate = findViewById(R.id.ReceivedDate);


        ReceivedDate.setText(JOB_INFO.getReceiveddate());
        ReceivedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(JobEditActivity.this,
                        onDateSetListener1,year,month,day);
                datePickerDialog.show();
            }
        });
        onDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth + "/" +month+"/"+year;
                ReceivedDate.setText(date);
            }
        };

        CashMode = findViewById(R.id.CashMode);

        String [] cashmode = new String[]{JOB_INFO.getCashmode(),"Cash","Upi","Net-Banking"};

        CashMode.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, cashmode));

        CashMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   RentType.getSelectedItem();
                CashMode.getSelectedItem().toString();
                //  Double rent_type = Double.valueOf(RentType.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        JobCreateBy = findViewById(R.id.JobCreateBy);
        JobCreateBy.setText(JOB_INFO.getJobcreateby());
//        JobCreateBy.setText(Uname);
        JobAssignBy = findViewById(R.id.JobAssignBy);


        //Get Tenant Names
        referenceUsers.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListUser.clear();

                arrayListUser.add(JOB_INFO.getJobassignby());
                //arrayListUser.add("Job AssignBY");

                for (DataSnapshot items : snapshot.getChildren()){
                    arrayListUser.add(items.child("Uname").getValue(String.class));
                    //  arrayListUser.add(items.child("Uid").getValue(String.class));

                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(JobEditActivity.this,
                        com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,
                        arrayListUser);
                JobAssignBy.setAdapter(arrayAdapter);
                JobAssignBy.getSelectedItem().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(JobEditActivity.this,"Something Issues",Toast.LENGTH_SHORT).show();
            }
        });

        MC_CustomerImage = findViewById(R.id.MC_CustomerImage);
        Job_Image = findViewById(R.id.Job_Image);
        Glide.with(JobEditActivity.this)
                .load(JOB_INFO.getJobimage())
                .into(Job_Image);
        JobImageText = findViewById(R.id.JobImageText);

        MC_CustomerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });


        Edit = findViewById(R.id.Edit);
        Delete = findViewById(R.id.Delete);
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(JobEditActivity.this);
//                alertDialogBuilder.setIcon(R.drawable.delete);
                alertDialogBuilder.setMessage("Are you sure,you want to Delete");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                String job_id = String.valueOf(JOB_INFO.getJobid());
                                DatabaseReference reference1 = FirebaseDatabase.getInstance().
                                        getReference().
                                        child("Jobs").child(String.valueOf(job_id));

                                reference1.removeValue();
                                finish();

                                Toast.makeText(JobEditActivity.this, "Deleted Successfully", Toast.LENGTH_LONG).show();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(JobEditActivity.this, "Edit Page", Toast.LENGTH_SHORT).show();



                if (ClosingAmount.getText().toString().isEmpty()){
                    ClosingAmount.setText("0");
                }
//                else if (TestingAmount.getText().toString().isEmpty()){
//                    TestingAmount.setText("0");
//                }
                else if (OldBalance.getText().toString().isEmpty()){
                    OldBalance.setText("0");
                }else if (BillAmount.getText().toString().isEmpty()){
                    BillAmount.setText("0");
                }else if (ReceivedAmount.getText().toString().isEmpty()){
                    ReceivedAmount.setText("0");
                }else if (BalanceAmount.getText().toString().isEmpty()){
                    BillAmount.setText("0");
                }else if (NetBalance.getText().toString().isEmpty()){
                    NetBalance.setText("0");
                }


                Closingby = ClosingBy.getText().toString();
                Closingamount = Double.valueOf(ClosingAmount.getText().toString());
                Testingby = TestingBy.getText().toString();
                Testingamount = Double.valueOf(TestingAmount.getText().toString());
                Oldbalance = Double.valueOf(OldBalance.getText().toString());
                Billamount = Double.valueOf(BillAmount.getText().toString());
                Receivedamount = Double.valueOf(ReceivedAmount.getText().toString());
                Balanceamount = Double.valueOf(BalanceAmount.getText().toString());
                Netbalance = Double.valueOf(NetBalance.getText().toString());
                Jobcreateby = JobCreateBy.getText().toString();

                if (bitmap==null){
                    Update(JOB_INFO.getJobimage());
                }
                else {
                    UploadImage();
                }
            }
        });

        getLabelValuesData();

    }

    private void UploadImage() {
        pd.setMessage("Wait a seconds");
        pd.show();


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg = baos.toByteArray();

        final StorageReference filePath;
        filePath=storageReference.child("Task").child(finalimg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(JobEditActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);



                                    String job_id = String.valueOf(JOB_INFO.getJobid());

                                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().
                                            child("Jobs").child(String.valueOf(job_id));

                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("Jobimage",""+downloadUrl);
                                    reference1.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            pd.dismiss();
                                            Toast.makeText(JobEditActivity.this, "Job Edited", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                                    Update(downloadUrl);

//                                    UploadWithImage();
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(JobEditActivity.this,"Something went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private HashMap<String,String> keyMap=new HashMap<>();
//    private void UploadWithImage() {
//
//
//
//        keyMap.put("color","red");
//        keyMap.put("size","xl");
//        keyMap.put("Tid",jobid);
//        keyMap.put("Jfid",JF_Id);
//
//        referenceJF.child(JF_Id).setValue(keyMap);
//
//        Jobclosedate = JobCloseDate.getText().toString();
//        Jobdeliverydate = JobDeliveryDate.getText().toString();
//        Receiveddate = ReceivedDate.getText().toString();
//        Jobcreatedate = JobCreateDate.getText().toString();
//
//        //Get CustomerId
//        Query query = FirebaseDatabase.getInstance().getReference("Customers");
//        query.orderByChild("C_CustomerName").equalTo(CustomerName).
//                addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    String Customerid =  dataSnapshot.child("C_ID").getValue().toString();
//
//                    query.orderByChild("C_CustomerName").equalTo(CustomerName).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
//                                String CustomerMobileNumber = dataSnapshot.child("C_MobileNumber").getValue().toString();
//                                HashMap<String,Object> hashMap = new HashMap<>();
//                                hashMap.put("Jobid",jobid);
//                                hashMap.put("Jobimage",downloadUrl);
//                                hashMap.put("Customername",CustomerName);
//                                hashMap.put("Jobstatus",JobStatus.getSelectedItem().toString());
//                                hashMap.put("Closingby",Closingby);
//                                hashMap.put("Closingamount",Closingamount);
//                                hashMap.put("Jobcreatedate",Jobcreatedate);
//                                hashMap.put("Jobclosedate",Jobclosedate);
//                                hashMap.put("Jobdeliverydate",Jobdeliverydate);
//                                hashMap.put("Testingby",Testingby);
//                                hashMap.put("Testingamount",Testingamount);
//                                hashMap.put("Oldbalance",Oldbalance);
//                                hashMap.put("Billamount",Billamount);
//                                hashMap.put("Receivedamount",Receivedamount);
//                                hashMap.put("Balanceamount",Balanceamount);
//                                hashMap.put("Netbalance",Netbalance);
//                                hashMap.put("Receiveddate",Receiveddate);
//                                hashMap.put("Cashmode",CashMode.getSelectedItem().toString());
//                                hashMap.put("Jobcreateby",Jobcreateby);
//                                hashMap.put("Jobassignby",JobAssignBy.getSelectedItem().toString());
//                                hashMap.put("Uid",U_Id);
//                                hashMap.put("Umobilenumber",U_MobileNumber1);
//                                hashMap.put("Uname",Uname);
//
//
//
//
//                                reference.child(String.valueOf(jobid)).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        Toast.makeText(JobEditActivity.this, "Job Added Successfully", Toast.LENGTH_SHORT).show();
//                                        finish();
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(JobEditActivity.this, "Something went wrong!!!", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
//
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//    }
    private HashMap<String,String> keyMap1=new HashMap<>();
    private void Update(String jobimage) {

        final String[] Customerid = new String[1];
        final String[] CustomerMobileNumber = new String[1];


        Jobclosedate = JobCloseDate.getText().toString();
        Jobdeliverydate = JobDeliveryDate.getText().toString();
        Receiveddate = ReceivedDate.getText().toString();
        Jobcreatedate = JobCreateDate.getText().toString();

        keyMap1.put("color","red");
        keyMap1.put("size","xl");
        keyMap1.put("Tid",jobid);
        keyMap1.put("Jfid",JF_Id);

        referenceJF.child(JF_Id).setValue(keyMap1);

        //Get CustomerId
        Query query = FirebaseDatabase.getInstance().getReference("Customers");
        query.orderByChild("C_CustomerName").equalTo(cn).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                             Customerid[0] =  dataSnapshot.child("C_ID").getValue().toString();

                            query.orderByChild("C_CustomerName").equalTo(cn).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){

                                         CustomerMobileNumber[0] = dataSnapshot1.
                                                child("C_MobileNumber").getValue().toString();


                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("Jobid",JOB_INFO.getJobid());
        hashMap.put("Jobimage",jobimage);
//        hashMap.put("Customer_id", Customerid[0]);
//        hashMap.put("Customer_mobilenumber", CustomerMobileNumber[0]);
        hashMap.put("Customername",JOB_INFO.getCustomername());
        hashMap.put("Problem",ProblemSpin.getSelectedItem().toString());
        hashMap.put("Model",ModelSpin.getSelectedItem().toString());
        hashMap.put("Type",TypeSpin.getSelectedItem().toString());
        hashMap.put("Brand",BrandSpin.getSelectedItem().toString());
        hashMap.put("Jobstatus",JobStatus.getSelectedItem().toString());
        hashMap.put("Closingby",Closingby);
        hashMap.put("Closingamount",Closingamount);
        hashMap.put("Jobcreatedate",JOB_INFO.getJobcreatedate());
        hashMap.put("Jobclosedate",Jobclosedate);
        hashMap.put("Jobdeliverydate",Jobdeliverydate);
        hashMap.put("Testingby",Testingby);
        hashMap.put("Testingamount",Testingamount);
        hashMap.put("Oldbalance",Oldbalance);
        hashMap.put("Billamount",Billamount);
        hashMap.put("Receivedamount",Receivedamount);
        hashMap.put("Balanceamount",Balanceamount);
        hashMap.put("Netbalance",Netbalance);
        hashMap.put("Receiveddate",Receiveddate);
        hashMap.put("Cashmode",CashMode.getSelectedItem().toString());
        hashMap.put("Jobcreateby",Jobcreateby);
        hashMap.put("Jobassignby",JobAssignBy.getSelectedItem().toString());





        reference.child(String.valueOf(JOB_INFO.getJobid())).
                updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(JobEditActivity.this, "Job Edited", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(JobEditActivity.this, "Something went wrong!!!", Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void openGallery() {
        Intent picImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picImage,REQ);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Log.i("reqCode**", requestCode + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<GAFOOR");

        //   Toast.makeText(context,"dataaXXX"+requestCode+data.getExtras(),Toast.LENGTH_SHORT).show();
        if (requestCode == 2001 && resultCode == RESULT_OK) {

            CustomerName = data.getStringExtra("CustomerName");
            C_CustomerName.setText(CustomerName);
//             finish();
//            Long holdOrderNo = data.getLongExtra("OrderID", 0l);
//            selectedTable = data.getStringExtra("HoldStatus");
        }

        if (requestCode == REQ && resultCode == RESULT_OK) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Job_Image.setImageBitmap(bitmap);
            JobImageText.setVisibility(View.INVISIBLE);
        }
    }

    public void loadDynamicFields(Map<String, List<String>> jobfieldListMap){
        Set<String> jobfieldList=jobfieldListMap.keySet();
        Log.i("dataMpa",jobfieldListMap+"zzzz");
        for(String jobfield:jobfieldList){
            loadDropDownField(jobfield,jobfieldListMap.get(jobfield));
        }
    }
    private void loadDropDownField(String fieldName,List<String> stringList)
    {
        try {
            // List<Jobfieldvalue>     fields=sqlliteHandler.getFieldValueList(jobfield.getFieldname());

            if(null!=stringList && stringList.size()>0) {

                LayoutInflater inflater = (LayoutInflater)
                        this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layoutView = inflater.inflate(R.layout.layout_dropdpwn, null);
                TextView txtQuestion = layoutView.findViewById(R.id.questionTxt);
                txtQuestion.setText(fieldName);

                Spinner spinner = layoutView.findViewById(R.id.fieldNameSpinner);

                ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                        R.layout.spinner_row, stringList);
                spinner.setAdapter(arrayAdapter);





                mainContainer.addView(layoutView);
                Log.i("s","yes"+"s");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    private List<LabelData> getLabelsData() {
        List<LabelData> labelDataList=new ArrayList<>();
        try {

            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Labels");
            reference1.addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        LabelData data = dataSnapshot.getValue(LabelData.class);
                        labelDataList.add(data);
                        Log.i("1234567890987654321",data.getL_ID()+"s");


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return labelDataList;
    }

    private List<Labelvalue> getLabelValuesData() {
        List<Labelvalue> labelDataList=new ArrayList<>();
        try {

            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("NewLabel");
            reference1.addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Labelvalue data = dataSnapshot.getValue(Labelvalue.class);
                        labelDataList.add(data);


                        Log.i("data : ",data+"s");
                        Log.i("id : ",data.getLid()+"s");
                        Log.i("Labelname : ",data.getLabelname()+"s");
                        Log.i("Labelvalue : " ,data.getLabelvalue()+"s");

                        //  Toast.makeText(context, "12345", Toast.LENGTH_SHORT).show();


                        //JobFieldValue

//
//                        HashMap<String,Object> hashMap = new HashMap<>();
//
//                        hashMap.put("Tid",T_ID);
//                        hashMap.put("Jfid",JF_Id);
//                        hashMap.put("id",data.getLid());
//                        hashMap.put("Labelname",data.getLabelname());
//                        hashMap.put("Labelvalue",data.getLabelvalue());
//
//                        referenceJF.child(JF_Id).setValue(hashMap);


                    }
                    loadDynamicFields(getLablesMap(labelDataList));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return labelDataList;
    }


    public Map<String,List<String>> getLablesMap(List<Labelvalue> keyDataList){

        Map <String,List<String>> updatedMap=new LinkedHashMap<>();
        Map<String, String> labelValuesMap=new HashMap();
        String name="";
        List<String> spinnerList=null;
        try{

            // List<Labelvalue> keyDataList=getLabelValuesData();
            Log.i("scccccfff",keyDataList+"<<<");
            for(Labelvalue labelvalue: keyDataList){

                name=labelvalue.getLabelname();
                spinnerList= updatedMap.get(name);
                if(null==spinnerList){
                    spinnerList=new ArrayList();
                }
                spinnerList.add(labelvalue.getLabelvalue());
                updatedMap.put(name,spinnerList);
            }


        }catch (Exception ex){
            ex.printStackTrace();
        }

        return updatedMap;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {


    }

}
