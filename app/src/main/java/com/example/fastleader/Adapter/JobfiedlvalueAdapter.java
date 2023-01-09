package com.example.fastleader.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastleader.Model.CustomerData;
import com.example.fastleader.Model.Jobfiedlvalue;
import com.example.fastleader.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class JobfiedlvalueAdapter extends RecyclerView.Adapter<JobfiedlvalueAdapter.ViewHolder> {


    private Context context;
    private ArrayList<Jobfiedlvalue> items;


    public JobfiedlvalueAdapter(Context context, ArrayList<Jobfiedlvalue> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.jobfield_adapter,parent,false);

        return new JobfiedlvalueAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Jobfiedlvalue data = items.get(position);


        holder.color.setText(data.getColor());
        holder.size.setText(data.getSize());


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        private TextView color,size;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            color = itemView.findViewById(R.id.color);
            size = itemView.findViewById(R.id.size);

        }
    }

}
