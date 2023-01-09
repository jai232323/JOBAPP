package com.example.fastleader.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class TaskData implements Parcelable {

    private String C_CustomerName,T_TaskDecription,T_ReminderDate;


    private String Tid;
    private String Billamount;
    private String Jobcreateby;
    private String Jobassignby;
    private String Jobcreatedate;
    private String Jobclosedate;
    private String Jobstatus;
    private String Jobname;
    private String Jobimage;
    private String Uid;
    private String Umobilenumber;



    public TaskData() {
    }

    public TaskData(String c_CustomerName, String t_TaskDecription, String t_ReminderDate,
                    String tid, String billamount, String jobcreateby, String jobassignby,
                    String jobcreatedate, String jobclosedate, String jobstatus, String jobname,
                    String jobimage, String uid, String umobilenumber) {
        C_CustomerName = c_CustomerName;
        T_TaskDecription = t_TaskDecription;
        T_ReminderDate = t_ReminderDate;
        Tid = tid;
        Billamount = billamount;
        Jobcreateby = jobcreateby;
        Jobassignby = jobassignby;
        Jobcreatedate = jobcreatedate;
        Jobclosedate = jobclosedate;
        Jobstatus = jobstatus;
        Jobname = jobname;
        Jobimage = jobimage;
        Uid = uid;
        Umobilenumber = umobilenumber;
    }

    protected TaskData(Parcel in) {
        C_CustomerName = in.readString();
        T_TaskDecription = in.readString();
        T_ReminderDate = in.readString();
        Tid = in.readString();
        Billamount = in.readString();
        Jobcreateby = in.readString();
        Jobassignby = in.readString();
        Jobcreatedate = in.readString();
        Jobclosedate = in.readString();
        Jobstatus = in.readString();
        Jobname = in.readString();
        Jobimage = in.readString();
        Uid = in.readString();
        Umobilenumber = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(C_CustomerName);
        dest.writeString(T_TaskDecription);
        dest.writeString(T_ReminderDate);
        dest.writeString(Tid);
        dest.writeString(Billamount);
        dest.writeString(Jobcreateby);
        dest.writeString(Jobassignby);
        dest.writeString(Jobcreatedate);
        dest.writeString(Jobclosedate);
        dest.writeString(Jobstatus);
        dest.writeString(Jobname);
        dest.writeString(Jobimage);
        dest.writeString(Uid);
        dest.writeString(Umobilenumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TaskData> CREATOR = new Creator<TaskData>() {
        @Override
        public TaskData createFromParcel(Parcel in) {
            return new TaskData(in);
        }

        @Override
        public TaskData[] newArray(int size) {
            return new TaskData[size];
        }
    };

    public String getC_CustomerName() {
        return C_CustomerName;
    }

    public void setC_CustomerName(String c_CustomerName) {
        C_CustomerName = c_CustomerName;
    }

    public String getT_TaskDecription() {
        return T_TaskDecription;
    }

    public void setT_TaskDecription(String t_TaskDecription) {
        T_TaskDecription = t_TaskDecription;
    }

    public String getT_ReminderDate() {
        return T_ReminderDate;
    }

    public void setT_ReminderDate(String t_ReminderDate) {
        T_ReminderDate = t_ReminderDate;
    }

    public String getTid() {
        return Tid;
    }

    public void setTid(String tid) {
        Tid = tid;
    }

    public String getBillamount() {
        return Billamount;
    }

    public void setBillamount(String billamount) {
        Billamount = billamount;
    }

    public String getJobcreateby() {
        return Jobcreateby;
    }

    public void setJobcreateby(String jobcreateby) {
        Jobcreateby = jobcreateby;
    }

    public String getJobassignby() {
        return Jobassignby;
    }

    public void setJobassignby(String jobassignby) {
        Jobassignby = jobassignby;
    }

    public String getJobcreatedate() {
        return Jobcreatedate;
    }

    public void setJobcreatedate(String jobcreatedate) {
        Jobcreatedate = jobcreatedate;
    }

    public String getJobclosedate() {
        return Jobclosedate;
    }

    public void setJobclosedate(String jobclosedate) {
        Jobclosedate = jobclosedate;
    }

    public String getJobstatus() {
        return Jobstatus;
    }

    public void setJobstatus(String jobstatus) {
        Jobstatus = jobstatus;
    }

    public String getJobname() {
        return Jobname;
    }

    public void setJobname(String jobname) {
        Jobname = jobname;
    }

    public String getJobimage() {
        return Jobimage;
    }

    public void setJobimage(String jobimage) {
        Jobimage = jobimage;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getUmobilenumber() {
        return Umobilenumber;
    }

    public void setUmobilenumber(String umobilenumber) {
        Umobilenumber = umobilenumber;
    }
}
