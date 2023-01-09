package com.example.fastleader.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class JobData implements Parcelable {


    private String Jobid;
    private String Jobimage;
    private String Customer_mobilenumber;
    private String Customer_id;
    private String Customername;
    private String Customer_ShopName;
    private String Customer_Area;
    private String Problem;
    private String Model;
    private String Type;
    private String Brand;
    private String Jobstatus;
    private String Closingby;
    private Double Closingamount;
    private String Jobcreatedate;
    private String Jobclosedate;
    private String Jobdeliverydate;
    private String Testingby;
    private Double Testingamount;
    private Double Oldbalance;
    private Double Billamount;
    private Double Receivedamount;
    private Double Balanceamount;
    private Double Netbalance;
    private String Receiveddate;
    private String Cashmode;
    private String Jobcreateby;
    private String Jobassignby;
    private String Uid;
    private String Uname;
    private String Umobilenumber;

    public JobData() {
    }



    public JobData(String jobid, String jobimage, String customer_mobilenumber, String customer_id,
                   String customername, String customer_ShopName, String customer_Area, String problem,
                   String model, String type, String brand, String jobstatus, String closingby,
                   Double closingamount, String jobcreatedate, String jobclosedate, String jobdeliverydate,
                   String testingby, Double testingamount, Double oldbalance, Double billamount,
                   Double receivedamount, Double balanceamount, Double netbalance, String receiveddate,
                   String cashmode, String jobcreateby, String jobassignby, String uid, String uname,
                   String umobilenumber) {
        Jobid = jobid;
        Jobimage = jobimage;
        Customer_mobilenumber = customer_mobilenumber;
        Customer_id = customer_id;
        Customername = customername;
        Customer_ShopName = customer_ShopName;
        Customer_Area = customer_Area;
        Problem = problem;
        Model = model;
        Type = type;
        Brand = brand;
        Jobstatus = jobstatus;
        Closingby = closingby;
        Closingamount = closingamount;
        Jobcreatedate = jobcreatedate;
        Jobclosedate = jobclosedate;
        Jobdeliverydate = jobdeliverydate;
        Testingby = testingby;
        Testingamount = testingamount;
        Oldbalance = oldbalance;
        Billamount = billamount;
        Receivedamount = receivedamount;
        Balanceamount = balanceamount;
        Netbalance = netbalance;
        Receiveddate = receiveddate;
        Cashmode = cashmode;
        Jobcreateby = jobcreateby;
        Jobassignby = jobassignby;
        Uid = uid;
        Uname = uname;
        Umobilenumber = umobilenumber;
    }

    protected JobData(Parcel in) {
        Jobid = in.readString();
        Jobimage = in.readString();
        Customer_mobilenumber = in.readString();
        Customer_id = in.readString();
        Customername = in.readString();
        Customer_ShopName = in.readString();
        Customer_Area = in.readString();
        Problem = in.readString();
        Model = in.readString();
        Type = in.readString();
        Brand = in.readString();
        Jobstatus = in.readString();
        Closingby = in.readString();
        if (in.readByte() == 0) {
            Closingamount = null;
        } else {
            Closingamount = in.readDouble();
        }
        Jobcreatedate = in.readString();
        Jobclosedate = in.readString();
        Jobdeliverydate = in.readString();
        Testingby = in.readString();
        if (in.readByte() == 0) {
            Testingamount = null;
        } else {
            Testingamount = in.readDouble();
        }
        if (in.readByte() == 0) {
            Oldbalance = null;
        } else {
            Oldbalance = in.readDouble();
        }
        if (in.readByte() == 0) {
            Billamount = null;
        } else {
            Billamount = in.readDouble();
        }
        if (in.readByte() == 0) {
            Receivedamount = null;
        } else {
            Receivedamount = in.readDouble();
        }
        if (in.readByte() == 0) {
            Balanceamount = null;
        } else {
            Balanceamount = in.readDouble();
        }
        if (in.readByte() == 0) {
            Netbalance = null;
        } else {
            Netbalance = in.readDouble();
        }
        Receiveddate = in.readString();
        Cashmode = in.readString();
        Jobcreateby = in.readString();
        Jobassignby = in.readString();
        Uid = in.readString();
        Uname = in.readString();
        Umobilenumber = in.readString();
    }

    public static final Creator<JobData> CREATOR = new Creator<JobData>() {
        @Override
        public JobData createFromParcel(Parcel in) {
            return new JobData(in);
        }

        @Override
        public JobData[] newArray(int size) {
            return new JobData[size];
        }
    };

    public String getJobid() {
        return Jobid;
    }

    public void setJobid(String jobid) {
        Jobid = jobid;
    }

    public String getJobimage() {
        return Jobimage;
    }

    public void setJobimage(String jobimage) {
        Jobimage = jobimage;
    }

    public String getCustomer_mobilenumber() {
        return Customer_mobilenumber;
    }

    public void setCustomer_mobilenumber(String customer_mobilenumber) {
        Customer_mobilenumber = customer_mobilenumber;
    }

    public String getCustomer_id() {
        return Customer_id;
    }

    public void setCustomer_id(String customer_id) {
        Customer_id = customer_id;
    }

    public String getCustomername() {
        return Customername;
    }

    public void setCustomername(String customername) {
        Customername = customername;
    }

    public String getCustomer_ShopName() {
        return Customer_ShopName;
    }

    public void setCustomer_ShopName(String customer_ShopName) {
        Customer_ShopName = customer_ShopName;
    }

    public String getCustomer_Area() {
        return Customer_Area;
    }

    public void setCustomer_Area(String customer_Area) {
        Customer_Area = customer_Area;
    }

    public String getProblem() {
        return Problem;
    }

    public void setProblem(String problem) {
        Problem = problem;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getJobstatus() {
        return Jobstatus;
    }

    public void setJobstatus(String jobstatus) {
        Jobstatus = jobstatus;
    }

    public String getClosingby() {
        return Closingby;
    }

    public void setClosingby(String closingby) {
        Closingby = closingby;
    }

    public Double getClosingamount() {
        return Closingamount;
    }

    public void setClosingamount(Double closingamount) {
        Closingamount = closingamount;
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

    public String getJobdeliverydate() {
        return Jobdeliverydate;
    }

    public void setJobdeliverydate(String jobdeliverydate) {
        Jobdeliverydate = jobdeliverydate;
    }

    public String getTestingby() {
        return Testingby;
    }

    public void setTestingby(String testingby) {
        Testingby = testingby;
    }

    public Double getTestingamount() {
        return Testingamount;
    }

    public void setTestingamount(Double testingamount) {
        Testingamount = testingamount;
    }

    public Double getOldbalance() {
        return Oldbalance;
    }

    public void setOldbalance(Double oldbalance) {
        Oldbalance = oldbalance;
    }

    public Double getBillamount() {
        return Billamount;
    }

    public void setBillamount(Double billamount) {
        Billamount = billamount;
    }

    public Double getReceivedamount() {
        return Receivedamount;
    }

    public void setReceivedamount(Double receivedamount) {
        Receivedamount = receivedamount;
    }

    public Double getBalanceamount() {
        return Balanceamount;
    }

    public void setBalanceamount(Double balanceamount) {
        Balanceamount = balanceamount;
    }

    public Double getNetbalance() {
        return Netbalance;
    }

    public void setNetbalance(Double netbalance) {
        Netbalance = netbalance;
    }

    public String getReceiveddate() {
        return Receiveddate;
    }

    public void setReceiveddate(String receiveddate) {
        Receiveddate = receiveddate;
    }

    public String getCashmode() {
        return Cashmode;
    }

    public void setCashmode(String cashmode) {
        Cashmode = cashmode;
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

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public String getUmobilenumber() {
        return Umobilenumber;
    }

    public void setUmobilenumber(String umobilenumber) {
        Umobilenumber = umobilenumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Jobid);
        parcel.writeString(Jobimage);
        parcel.writeString(Customer_mobilenumber);
        parcel.writeString(Customer_id);
        parcel.writeString(Customername);
        parcel.writeString(Customer_ShopName);
        parcel.writeString(Customer_Area);
        parcel.writeString(Problem);
        parcel.writeString(Model);
        parcel.writeString(Type);
        parcel.writeString(Brand);
        parcel.writeString(Jobstatus);
        parcel.writeString(Closingby);
        if (Closingamount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(Closingamount);
        }
        parcel.writeString(Jobcreatedate);
        parcel.writeString(Jobclosedate);
        parcel.writeString(Jobdeliverydate);
        parcel.writeString(Testingby);
        if (Testingamount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(Testingamount);
        }
        if (Oldbalance == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(Oldbalance);
        }
        if (Billamount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(Billamount);
        }
        if (Receivedamount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(Receivedamount);
        }
        if (Balanceamount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(Balanceamount);
        }
        if (Netbalance == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(Netbalance);
        }
        parcel.writeString(Receiveddate);
        parcel.writeString(Cashmode);
        parcel.writeString(Jobcreateby);
        parcel.writeString(Jobassignby);
        parcel.writeString(Uid);
        parcel.writeString(Uname);
        parcel.writeString(Umobilenumber);
    }
}
