package com.secure.paulken.igrave.Model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.secure.paulken.igrave.DBHelper.ItemsTable;

public class DataItems implements Parcelable {

    private int tomb_id;
    private String tomb_block;
    private int tomb_lot_no;
    private double tomb_lat;
    private double tomb_long;
    private String tomb_stat;
    private int tomb_owner;
    private int owner_id;
    private String owner_fname;
    private String owner_mname;
    private String owner_lname;
    private String owner_bdate;
    private String owner_ddate;
    private String owner_con_per;

    public DataItems(){}

    public DataItems(int tomb_id, String tomb_block, int tomb_lot_no, double tomb_lat, double tomb_long, String tomb_stat, int tomb_owner, int owner_id, String owner_fname, String owner_mname, String owner_lname, String owner_bdate, String owner_ddate, String owner_con_per, String owner_status) {
        this.tomb_id = tomb_id;
        this.tomb_block = tomb_block;
        this.tomb_lot_no = tomb_lot_no;
        this.tomb_lat = tomb_lat;
        this.tomb_long = tomb_long;
        this.tomb_stat = tomb_stat;
        this.tomb_owner = tomb_owner;
        this.owner_id = owner_id;
        this.owner_fname = owner_fname;
        this.owner_mname = owner_mname;
        this.owner_lname = owner_lname;
        this.owner_bdate = owner_bdate;
        this.owner_ddate = owner_ddate;
        this.owner_con_per = owner_con_per;
        this.owner_status = owner_status;
    }

    public int getTomb_id() {
        return tomb_id;
    }

    public void setTomb_id(int tomb_id) {
        this.tomb_id = tomb_id;
    }

    public String getTomb_block() {
        return tomb_block;
    }

    public void setTomb_block(String tomb_block) {
        this.tomb_block = tomb_block;
    }

    public int getTomb_lot_no() {
        return tomb_lot_no;
    }

    public void setTomb_lot_no(int tomb_lot_no) {
        this.tomb_lot_no = tomb_lot_no;
    }

    public double getTomb_lat() {
        return tomb_lat;
    }

    public void setTomb_lat(double tomb_lat) {
        this.tomb_lat = tomb_lat;
    }

    public double getTomb_long() {
        return tomb_long;
    }

    public void setTomb_long(double tomb_long) {
        this.tomb_long = tomb_long;
    }

    public String getTomb_stat() {
        return tomb_stat;
    }

    public void setTomb_stat(String tomb_stat) {
        this.tomb_stat = tomb_stat;
    }

    public int getTomb_owner() {
        return tomb_owner;
    }

    public void setTomb_owner(int tomb_owner) {
        this.tomb_owner = tomb_owner;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getOwner_fname() {
        return owner_fname;
    }

    public void setOwner_fname(String owner_fname) {
        this.owner_fname = owner_fname;
    }

    public String getOwner_mname() {
        return owner_mname;
    }

    public void setOwner_mname(String owner_mname) {
        this.owner_mname = owner_mname;
    }

    public String getOwner_lname() {
        return owner_lname;
    }

    public void setOwner_lname(String owner_lname) {
        this.owner_lname = owner_lname;
    }

    public String getOwner_bdate() {
        return owner_bdate;
    }

    public void setOwner_bdate(String owner_bdate) {
        this.owner_bdate = owner_bdate;
    }

    public String getOwner_ddate() {
        return owner_ddate;
    }

    public void setOwner_ddate(String owner_ddate) {
        this.owner_ddate = owner_ddate;
    }

    public String getOwner_con_per() {
        return owner_con_per;
    }

    public void setOwner_con_per(String owner_con_per) {
        this.owner_con_per = owner_con_per;
    }

    public String getOwner_status() {
        return owner_status;
    }

    public void setOwner_status(String owner_status) {
        this.owner_status = owner_status;
    }

    private String owner_status;


    protected DataItems(Parcel in) {
        tomb_id = in.readInt();
        tomb_block = in.readString();
        tomb_lot_no = in.readInt();
        tomb_lat = in.readDouble();
        tomb_long = in.readDouble();
        tomb_stat = in.readString();
        tomb_owner = in.readInt();
        owner_id = in.readInt();
        owner_fname = in.readString();
        owner_mname = in.readString();
        owner_lname = in.readString();
        owner_bdate = in.readString();
        owner_ddate = in.readString();
        owner_con_per = in.readString();
        owner_status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tomb_id);
        dest.writeString(tomb_block);
        dest.writeInt(tomb_lot_no);
        dest.writeDouble(tomb_lat);
        dest.writeDouble(tomb_long);
        dest.writeString(tomb_stat);
        dest.writeInt(tomb_owner);
        dest.writeInt(owner_id);
        dest.writeString(owner_fname);
        dest.writeString(owner_mname);
        dest.writeString(owner_lname);
        dest.writeString(owner_bdate);
        dest.writeString(owner_ddate);
        dest.writeString(owner_con_per);
        dest.writeString(owner_status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataItems> CREATOR = new Creator<DataItems>() {
        @Override
        public DataItems createFromParcel(Parcel in) {
            return new DataItems(in);
        }

        @Override
        public DataItems[] newArray(int size) {
            return new DataItems[size];
        }
    };
}
