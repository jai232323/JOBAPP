package com.example.fastleader.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastleader.Adapter.CustomerAdapter;
import com.example.fastleader.Adapter.ShowCustomerNameAdapter;
import com.example.fastleader.Model.CustomerData;
import com.example.fastleader.Model.JobData;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class ShowCustomerNameActivity extends AppCompatActivity {


    RecyclerView ShowCustomerRecyclerView;

    private ArrayList<CustomerData> list;
    private ShowCustomerNameAdapter adapter;

    private Activity activity;

    ImageView AddCustomer;



    DatabaseReference reference;

    ProgressDialog pd;
    String downloadUrl="",c_leadname,c_company,c_mobilenumber,c_email,c_address,c_addevent;
    Bitmap bitmap;
    private final int REQ = 1;
    StorageReference storageReference;

    TextView C_CustomersImageText;
    ImageView C_Image;

    AlertDialog alertDialog;

    EditText SearchJobid;

    //Auto Increment for Jobid
    final int total = 30000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_customer_name);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add Customer");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        pd = new ProgressDialog(this);

        SearchJobid = findViewById(R.id.SearchJobid);

        AddCustomer = findViewById(R.id.AddCustomer);
        AddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(ShowCustomerNameActivity.this);
                View view1 = inflater.inflate(R.layout.custome_customeredit_dialog,null);

                MaterialCardView MC_CustomerImage;

                EditText C_CustomerName,C_Company,C_MobileNumber,C_Email,C_Address,C_AddEvent;
                Button C_OK;


                MC_CustomerImage =view1.findViewById(R.id.MC_CustomerImage);
                C_CustomersImageText= view1.findViewById(R.id.C_CustomersImageText);
                C_Image =view1. findViewById(R.id.C_Image);
                C_CustomerName =view1. findViewById(R.id.C_CustomerName);
                C_Company = view1.findViewById(R.id.C_Company);
                C_MobileNumber = view1.findViewById(R.id.C_MobileNumber);
                C_Email = view1.findViewById(R.id.C_Email);
                C_Address = view1.findViewById(R.id.C_Address);
                C_AddEvent = view1.findViewById(R.id.C_AddEvent);
                C_OK = view1.findViewById(R.id.Save);


                reference = FirebaseDatabase.getInstance().getReference("Customers");


                storageReference= FirebaseStorage.getInstance().getReference();
                C_OK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        c_leadname = C_CustomerName.getText().toString();
                        c_company = C_Company.getText().toString();
                        c_mobilenumber = C_MobileNumber.getText().toString();
                        c_email = C_Email.getText().toString();
                        c_address = C_Address.getText().toString();
                        c_addevent = C_AddEvent.getText().toString();


                        if (bitmap==null){
                            UploadLeadsData(c_leadname,c_company,c_mobilenumber,c_email,
                                    c_address,c_addevent);
                        }else {
                            UploadImage();
                        }
                    }
                });
                MC_CustomerImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openGallery();
                    }
                });

                 alertDialog = new AlertDialog.Builder(ShowCustomerNameActivity.this)
                        .setView(view1)
                        .create();
                alertDialog.show();



            }
        });

        activity=this;
        ShowCustomerRecyclerView =findViewById(R.id.ShowCustomerNameRecyclerView);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(ShowCustomerNameActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        ShowCustomerRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new ShowCustomerNameAdapter(activity,ShowCustomerNameActivity.this,list);
        ShowCustomerRecyclerView.setAdapter(adapter);

        getCustomerName();


    }

    private void UploadImage() {
        pd.setMessage("Wait a seconds");
        pd.show();


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg = baos.toByteArray();

        final StorageReference filePath;
        filePath=storageReference.child("Customers").child(finalimg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(ShowCustomerNameActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    //insertData(email, password);
                                    UploadLeadsData(c_leadname,c_company,c_mobilenumber,c_email,
                                            c_address,c_addevent,downloadUrl);
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(ShowCustomerNameActivity.this,"Something went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void UploadLeadsData(String c_leadname, String c_company,
                                 String c_mobilenumber, String c_email,
                                 String c_address, String c_addevent, String downloadUrl) {
        String C_ID = reference.push().getKey().toString();

        String  currentDateTimeString = DateFormat.getDateTimeInstance()
                .format(new Date());


        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("C_ID",C_ID);
        hashMap.put("C_CustomerName",c_leadname);
        hashMap.put("C_Company",c_company);
        hashMap.put("C_MobileNumber",c_mobilenumber);
        hashMap.put("C_Email",c_email);
        hashMap.put("C_Address",c_address);
        hashMap.put("C_AddEvent",c_addevent);
        hashMap.put("C_Image",downloadUrl);
        hashMap.put("DataTime",currentDateTimeString);

        reference.child(C_ID).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ShowCustomerNameActivity.this, "Leads Added Successfully", Toast.LENGTH_SHORT).show();
//                finish();
                pd.dismiss();
                alertDialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(ShowCustomerNameActivity.this, "Something Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UploadLeadsData(String c_leadname, String c_company,
                                 String c_mobilenumber, String c_email,
                                 String c_address, String c_addevent) {
        String C_ID = reference.push().getKey().toString();

        String  currentDateTimeString = DateFormat.getDateTimeInstance()
                .format(new Date());


        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("C_ID",C_ID);
        hashMap.put("C_CustomerName",c_leadname);
        hashMap.put("C_Company",c_company);
        hashMap.put("C_MobileNumber",c_mobilenumber);
        hashMap.put("C_Email",c_email);
        hashMap.put("C_Address",c_address);
        hashMap.put("C_AddEvent",c_addevent);
        hashMap.put("C_Image","https://firebasestorage.googleapis.com/v0/b/fastleader-dc4e0.appspot.com/o/person_icon.png?alt=media&token=57698bea-6b41-4793-8073-b4fbbb65de62");
        hashMap.put("DataTime",currentDateTimeString);


        reference.child(C_ID).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ShowCustomerNameActivity.this, "Customer Added Successfully", Toast.LENGTH_SHORT).show();
//                finish();
                pd.dismiss();
                alertDialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(ShowCustomerNameActivity.this, "Something Error", Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void openGallery() {
        Intent picImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picImage,REQ);
    }

    private void getCustomerName() {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Customers");
        reference1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CustomerData data = dataSnapshot.getValue(CustomerData.class);
                    list.add(0, data);
                    adapter.notifyDataSetChanged();
                    Collections.reverse(list);

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
                Collections.reverse(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShowCustomerNameActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filter(String text) {
        ArrayList<CustomerData> filterlist = new ArrayList<>();
        for (CustomerData item : list){

            if (item.getC_MobileNumber().toLowerCase().contains(text.toLowerCase())){

                filterlist.add(item);

            }
            adapter.Filteredlist(filterlist);
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ && resultCode == RESULT_OK) {

            Uri uri = data.getData();


            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            C_Image.setImageBitmap(bitmap);
            C_CustomersImageText.setVisibility(View.INVISIBLE);
        }
    }
}