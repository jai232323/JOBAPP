package com.example.fastleader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fastleader.Activity.FullImagerActivity;
import com.example.fastleader.Activity.JobPreviewPageActivity;
import com.example.fastleader.Model.JobData;
import com.example.fastleader.R;

import java.io.File;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {



    private Context context;
    private ArrayList<JobData> items;


    public TaskAdapter(Context context, ArrayList<JobData> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_adapter,parent,false);

        return new TaskAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        JobData data = items.get(position);


        String jobid = String.valueOf(data.getJobid());

        holder.Jobid.setText(jobid);

        String bamount = data.getBillamount().toString();


        holder.CustomerMobileNo.setText(data.getCustomer_mobilenumber());

        //CustomerName
//        if (data.getCustomername().equals(""))
//        {
//            holder.CustomerName.setText("");
//        }else {
//            holder.CustomerName.setText(data.getCustomername());
//        }
        holder.CustomerName.setText(data.getCustomername());
        holder.CustomerName.setHint(data.getCustomername());
        //JobAssignBy
        if (data.getJobassignby().equals("Job AssignBY")){
            holder.JobAssignBy.setText("");
        }else {
            holder.JobAssignBy.setText(data.getJobassignby());
        }


        //Close Date
        if (data.getJobclosedate().equals("dd-mm-yyy")){
            holder.JobCloseDate.setText("XXXXXX");
        }else {
            holder.JobCloseDate.setText(" To "+data.getJobclosedate());
        }


        holder.JobStatus.setText(data.getJobstatus());
        holder.JobCreateDate.setText(data.getJobcreatedate());
//        holder.JobName.setText(data.getJobname());

        holder.JobCreateBy.setText(data.getJobcreateby());
      //  holder.JobAssignBy.setText(data.getJobassignby());


        holder.BillAmount.setText(bamount);

        Glide.with(context)
                .load(data.getJobimage())
                .into(holder.Jobimage);

        holder.Jobimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FullImagerActivity.class);
                intent.putExtra("image",data.getJobimage());
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, JobPreviewPageActivity.class);
                intent.putExtra("JOB_INFO",data);
                context.startActivity(intent);

            }
        });



        //Job Edit and Delete
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String id = data.getTid();
//                String cn = data.getC_CustomerName();
//                String ba = data.getBillamount();
//                String jcb = data.getJobcreateby();
//                String jab = data.getJobassignby();
//                String createdate = data.getJobcreatedate();
//                String jclosedate = data.getJobclosedate();
//                String js = data.getJobstatus();
//                String jn = data.getJobname();
//                String ji = data.getJobimage();
//                String td = data.getT_TaskDecription();
//
//                Intent intent = new Intent(context, JobEditActivity.class);
//                intent.putExtra("id",id);
//                intent.putExtra("customerName",cn);
//                intent.putExtra("billamount",ba);
//                intent.putExtra("jobcreateby",jcb);
//                intent.putExtra("jobassignby",jab);
//                intent.putExtra("jobcreatedate",createdate);
//                intent.putExtra("jclosedate",jclosedate);
//                intent.putExtra("jobstatus",js);
//                intent.putExtra("jobname",jn);
//                intent.putExtra("jobimage",ji);
//                intent.putExtra("taskdesc",td);
//
//                //      Toast.makeText(context, cn, Toast.LENGTH_SHORT).show();
//
//                context.startActivity(intent);
//
//            }
//        });
//        holder.dotsedit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String id = data.getTid();
//                String cn = data.getC_CustomerName();
//                String ba = data.getBillamount();
//                String jcb = data.getJobcreateby();
//                String jab = data.getJobassignby();
//                String jclosedate = data.getJobclosedate();
//                String js = data.getJobstatus();
//                String jn = data.getJobname();
//                String ji = data.getJobimage();
//                String td = data.getT_TaskDecription();
//
//                Intent intent = new Intent(context, JobEditActivity.class);
//                intent.putExtra("id",id);
//                intent.putExtra("customerName",cn);
//                intent.putExtra("billamount",ba);
//                intent.putExtra("jobcreateby",jcb);
//                intent.putExtra("jobassignby",jab);
//                intent.putExtra("jclosedate",jclosedate);
//                intent.putExtra("jobstatus",js);
//                intent.putExtra("jobname",jn);
//                intent.putExtra("jobimage",ji);
//                intent.putExtra("taskdesc",td);
//
//          //      Toast.makeText(context, cn, Toast.LENGTH_SHORT).show();
//
//                context.startActivity(intent);
//
//            }
//        });

//        if (data.getC_CustomerName().equals("")){
//            holder.itemView.setVisibility(View.INVISIBLE);
//        }else {
//            holder.itemView.setVisibility(View.VISIBLE);
//        }


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void Filteredlist(ArrayList<JobData> filterlist) {

        items = filterlist;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView Jobid;
        TextView CustomerName,JobCreateDate,JobName,JobStatus,JobCreateBy,JobAssignBy,JobCloseDate,BillAmount;

        TextView CustomerMobileNo;

        ImageView Jobimage,dotsedit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Jobid = itemView.findViewById(R.id.Jobid);
            CustomerName = itemView.findViewById(R.id.CustomerName);
            JobCreateDate = itemView.findViewById(R.id.JobCreateDate);
            JobName = itemView.findViewById(R.id.JobName);
            JobStatus = itemView.findViewById(R.id.JobStatus);
            JobCreateBy = itemView.findViewById(R.id.JobCreateBy);
            JobAssignBy = itemView.findViewById(R.id.JobAssignBy);
            JobCloseDate = itemView.findViewById(R.id.JobCloseDate);
            BillAmount = itemView.findViewById(R.id.BillAmount);

            Jobimage = itemView.findViewById(R.id.Jobimage);
            dotsedit = itemView.findViewById(R.id.dotsedit);

            CustomerMobileNo = itemView.findViewById(R.id.CustomerMobileNo);

        }
    }

}
