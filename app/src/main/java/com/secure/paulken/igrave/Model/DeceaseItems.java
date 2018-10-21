package com.secure.paulken.igrave.Model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.secure.paulken.igrave.DBHelper.ItemsTable;

public class DeceaseItems implements Parcelable {

    private int    decease_id;
    private String decease_fname;
    private String decease_mname;
    private String decease_lname;
    private String decease_bdate;
    private String decease_ddate;
    private int    decease_owner;

    public DeceaseItems(){}


    public ContentValues deceaseValues()
    {
        ContentValues values = new ContentValues(6);
        values.put(ItemsTable.COL_DECEASE_FNAME,decease_fname);
        values.put(ItemsTable.COL_DECEASE_MNAME,decease_mname);
        values.put(ItemsTable.COL_DECEASE_LNAME,decease_lname);
        values.put(ItemsTable.COL_DECEASE_BDATE,decease_bdate);
        values.put(ItemsTable.COL_DECEASE_DDATE,decease_ddate);
        values.put(ItemsTable.COL_DECEASE_OWNER,decease_owner);
        return values;
    }


    public DeceaseItems(String decease_fname, String decease_mname, String decease_lname, String decease_bdate, String decease_ddate, int decease_owner) {
        this.decease_fname = decease_fname;
        this.decease_mname = decease_mname;
        this.decease_lname = decease_lname;
        this.decease_bdate = decease_bdate;
        this.decease_ddate = decease_ddate;
        this.decease_owner = decease_owner;
    }

    public DeceaseItems(int decease_id, String decease_fname, String decease_mname, String decease_lname, String decease_bdate, String decease_ddate, int decease_owner) {
        this.decease_id = decease_id;
        this.decease_fname = decease_fname;
        this.decease_mname = decease_mname;
        this.decease_lname = decease_lname;
        this.decease_bdate = decease_bdate;
        this.decease_ddate = decease_ddate;
        this.decease_owner = decease_owner;
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

    protected DeceaseItems(Parcel in) {
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

    public static final Creator<DeceaseItems> CREATOR = new Creator<DeceaseItems>() {
        @Override
        public DeceaseItems createFromParcel(Parcel in) {
            return new DeceaseItems(in);
        }

        @Override
        public DeceaseItems[] newArray(int size) {
            return new DeceaseItems[size];
        }
    };
}
