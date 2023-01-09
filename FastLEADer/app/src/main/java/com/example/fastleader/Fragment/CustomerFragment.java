package com.example.fastleader.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fastleader.Activity.AddCustomersActivity;
import com.example.fastleader.Activity.AddLeadsActivity;
import com.example.fastleader.Adapter.CustomerAdapter;
import com.example.fastleader.Adapter.LeadsAdapter;
import com.example.fastleader.Model.CustomerData;
import com.example.fastleader.Model.JobData;
import com.example.fastleader.Model.LeadsData;
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


public class CustomerFragment extends Fragment {


    ExtendedFloatingActionButton fab;


    RecyclerView CustomerRecyclerView;

    private ArrayList<CustomerData> list;
    private CustomerAdapter adapter;

    ProgressDialog pd;


    EditText Search;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_customer, container, false);

      //  ((AppCompatActivity)getActivity()).getSupportActionBar().hide();





        pd = new ProgressDialog(getContext());

        Search = view.findViewById(R.id.Search);
        fab = view.findViewById(R.id.fab);

        CustomerRecyclerView = view.findViewById(R.id.CustomerRecyclerView);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        CustomerRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new CustomerAdapter(getContext(),list);
        CustomerRecyclerView.setAdapter(adapter);

        getCustomerData();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddCustomersActivity.class));
            }
        });
        CustomerRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
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

        return view;
    }

    private void getCustomerData() {

        pd.setMessage("Loading...");
        pd.show();

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Customers");
        reference1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    pd.dismiss();
                    CustomerData data = dataSnapshot.getValue(CustomerData.class);
                    list.add(0, data);
                    adapter.notifyDataSetChanged();
                    Collections.reverse(list);

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
                }
                Collections.reverse(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pd.dismiss();
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filter(String text) {
        ArrayList<CustomerData> filterlist = new ArrayList<>();
        for (CustomerData item : list){

            String mn = item.getC_MobileNumber();

            if (mn.toLowerCase().
                    contains(text.toString())){
                filterlist.add(item);
            }

            adapter.Filteredlist(filterlist);
            adapter.notifyDataSetChanged();
        }
    }
}