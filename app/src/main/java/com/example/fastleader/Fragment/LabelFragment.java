package com.example.fastleader.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fastleader.Activity.AddLabelActivity;
import com.example.fastleader.Adapter.CustomerAdapter;
import com.example.fastleader.Adapter.LabelAdapter;
import com.example.fastleader.Model.CustomerData;
import com.example.fastleader.Model.LabelData;
import com.example.fastleader.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class LabelFragment extends Fragment {


    ExtendedFloatingActionButton fab;

    RecyclerView LabelRecyclerView;

    private ArrayList<LabelData> list;
    private LabelAdapter adapter;

    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_label, container, false);

        pd = new ProgressDialog(getContext());

        fab = view.findViewById(R.id.fab);

        LabelRecyclerView = view.findViewById(R.id.LabelRecyclerView);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        LabelRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new LabelAdapter(getContext(),list);
        LabelRecyclerView.setAdapter(adapter);

        getLabelsData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), AddLabelActivity.class));

               // ShowDialogBox();
            }
        });

        LabelRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy<0 && !fab.isShown())
                    fab.show();
                else if(dy>0 && fab.isShown())
                    fab.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        return  view;

    }

    private void getLabelsData() {

        pd.setMessage("Loading...");
        pd.show();

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Labels");
        reference1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    pd.dismiss();
                    LabelData data = dataSnapshot.getValue(LabelData.class);
                    list.add(0, data);

                }
                Collections.reverse(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pd.dismiss();
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}