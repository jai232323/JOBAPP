package com.example.fastleader.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fastleader.Adapter.TaskAdapter;
import com.example.fastleader.Model.JobData;
import com.example.fastleader.Model.TaskData;
import com.example.fastleader.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MyTaskFragment extends Fragment {


    private ArrayList<JobData> list;
    private TaskAdapter adapter;

    RecyclerView TaskRecyclerView;
    LinearLayout no_jobs;

    String U_MobileNumber1;

    String U_Id;

    ProgressDialog pd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_task, container, false);


        pd = new ProgressDialog(getContext());

        SharedPreferences prefs1 = getContext().getSharedPreferences("PREFS",
                getContext().MODE_PRIVATE);
        U_Id=prefs1.getString("U_Id","none");

        TaskRecyclerView = view.findViewById(R.id.TaskRecyclerView);
        no_jobs = view.findViewById(R.id.no_jobs);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        TaskRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new TaskAdapter(getContext(),list);
        TaskRecyclerView.setAdapter(adapter);


        SharedPreferences prefs3 = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        U_MobileNumber1 = prefs3.getString("U_MobileNumber", "none");

        getUserJobData();

        return view;
    }

    private void getUserJobData() {

        pd.setMessage("Loading...");
        pd.show();

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Tasks");

     //   Query query = reference1.orderByChild("Jobcreatedate").limitToFirst(10);



        Query query = FirebaseDatabase.getInstance().getReference("Jobs");

        //query.orderByChild("Uid").equalTo("NDkzSlCBJpWewkS4vHk").
        query.orderByChild("Uid").equalTo(U_Id).addValueEventListener(new ValueEventListener() {
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

                        pd.dismiss();
                        JobData data = dataSnapshot.getValue(JobData.class);


                        Log.i("data",data.toString()+"...");


                        list.add(0, data);
                        adapter.notifyDataSetChanged();
//                        if (U_MobileNumber1.equals(data.getUmobilenumber())){
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
                pd.dismiss();
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}