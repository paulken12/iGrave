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
    private String owner_address;
    private String owner_con_per;

    private int    decease_id;
    private String decease_fname;
    private String decease_mname;
    private String decease_lname;
    private String decease_bdate;
    private String decease_ddate;
    private int decease_owner;

    public DataItems(){}

    public DataItems(String tomb_block){
        this.tomb_block = tomb_block;
    }

    public DataItems(int tomb_id, String tomb_block, int tomb_lot_no, double tomb_lat, double tomb_long, String tomb_stat, int tomb_owner, int owner_id, String owner_fname, String owner_mname, String owner_lname, String owner_address, String owner_con_per, int decease_id, String decease_fname, String decease_mname, String decease_lname, String decease_bdate, String decease_ddate, int decease_owner) {
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
        this.owner_address = owner_address;
        this.owner_con_per = owner_con_per;
        this.decease_id = decease_id;
        this.decease_fname = decease_fname;
        this.decease_mname = decease_mname;
        this.decease_lname = decease_lname;
        this.decease_bdate = decease_bdate;
        this.decease_ddate = decease_ddate;
        this.decease_owner = decease_owner;
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

    public String getOwner_address() {
        return owner_address;
    }

    public void setOwner_address(String owner_address) {
        this.owner_address = owner_address;
    }

    public String getOwner_con_per() {
        return owner_con_per;
    }

    public void setOwner_con_per(String owner_con_per) {
        this.owner_con_per = owner_con_per;
    }

    public int getDecease_id() {
        return decease_id;
    }

    public void setDecease_id(int decease_id) {
        this.decease_id = decease_id;
    }

    public String getDecease_fname() {
        return decease_fname;
    }

    public void setDecease_fname(String decease_fname) {
        this.decease_fname = decease_fname;
    }

    public String getDecease_mname() {
        return decease_mname;
    }

    public void setDecease_mname(String decease_mname) {
        this.decease_mname = decease_mname;
    }

    public String getDecease_lname() {
        return decease_lname;
    }

    public void setDecease_lname(String decease_lname) {
        this.decease_lname = decease_lname;
    }

    public String getDecease_bdate() {
        return decease_bdate;
    }

    public void setDecease_bdate(String decease_bdate) {
        this.decease_bdate = decease_bdate;
    }

    public String getDecease_ddate() {
        return decease_ddate;
    }

    public void setDecease_ddate(String decease_ddate) {
        this.decease_ddate = decease_ddate;
    }

    public int getDecease_owner() {
        return decease_owner;
    }

    public void setDecease_owner(int decease_owner) {
        this.decease_owner = decease_owner;
    }

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
        owner_address = in.readString();
        owner_con_per = in.readString();
        decease_id = in.readInt();
        decease_fname = in.readString();
        decease_mname = in.readString();
        decease_lname = in.readString();
        decease_bdate = in.readString();
        decease_ddate = in.readString();
        decease_owner = in.readInt();
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
        dest.writeString(owner_address);
        dest.writeString(owner_con_per);
        dest.writeInt(decease_id);
        dest.writeString(decease_fname);
        dest.writeString(decease_mname);
        dest.writeString(decease_lname);
        dest.writeString(decease_bdate);
        dest.writeString(decease_ddate);
        dest.writeInt(decease_owner);
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
