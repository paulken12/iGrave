package com.secure.paulken.igrave.Model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.secure.paulken.igrave.DBHelper.ItemsTable;

public class OwnerItems implements Parcelable {

    private int owner_id;
    private String owner_fname;
    private String owner_mname;
    private String owner_lname;
    private String owner_bdate;
    private String owner_ddate;
    private String owner_con_per;
    private String owner_status;

    public OwnerItems(){}



    public ContentValues ownerValues()
    {
        ContentValues values = new ContentValues(7);
        values.put(ItemsTable.COL_OWNER_FNAME,owner_fname);
        values.put(ItemsTable.COL_OWNER_MNAME,owner_mname);
        values.put(ItemsTable.COL_OWNER_LNAME,owner_lname);
        values.put(ItemsTable.COL_OWNER_BDATE,owner_bdate);
        values.put(ItemsTable.COL_OWNER_DDATE,owner_ddate);
        values.put(ItemsTable.COL_OWNER_CON_PER,owner_con_per);
        values.put(ItemsTable.COL_OWNER_STATUS,owner_status);
        return values;
    }

    public OwnerItems(int owner_id, String owner_fname, String owner_mname, String owner_lname, String owner_bdate, String owner_ddate, String owner_con_per, String owner_status) {
        this.owner_id = owner_id;
        this.owner_fname = owner_fname;
        this.owner_mname = owner_mname;
        this.owner_lname = owner_lname;
        this.owner_bdate = owner_bdate;
        this.owner_ddate = owner_ddate;
        this.owner_con_per = owner_con_per;
        this.owner_status = owner_status;
    }

    public OwnerItems(String owner_fname, String owner_mname, String owner_lname, String owner_bdate, String owner_ddate, String owner_con_per, String owner_status) {
        this.owner_fname = owner_fname;
        this.owner_mname = owner_mname;
        this.owner_lname = owner_lname;
        this.owner_bdate = owner_bdate;
        this.owner_ddate = owner_ddate;
        this.owner_con_per = owner_con_per;
        this.owner_status = owner_status;
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

    protected OwnerItems(Parcel in) {
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

    public static final Creator<OwnerItems> CREATOR = new Creator<OwnerItems>() {
        @Override
        public OwnerItems createFromParcel(Parcel in) {
            return new OwnerItems(in);
        }

        @Override
        public OwnerItems[] newArray(int size) {
            return new OwnerItems[size];
        }
    };
}
