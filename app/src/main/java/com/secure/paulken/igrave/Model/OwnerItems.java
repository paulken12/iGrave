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
    private String owner_address;
    private String owner_con_per;

    public OwnerItems(String owner_fname, String owner_mname, String owner_lname, String owner_address, String owner_con_per) {
        this.owner_fname = owner_fname;
        this.owner_mname = owner_mname;
        this.owner_lname = owner_lname;
        this.owner_address = owner_address;
        this.owner_con_per = owner_con_per;
    }

    public OwnerItems(){}



    public ContentValues ownerValues()
    {
        ContentValues values = new ContentValues(5);
        values.put(ItemsTable.COL_OWNER_FNAME,owner_fname);
        values.put(ItemsTable.COL_OWNER_MNAME,owner_mname);
        values.put(ItemsTable.COL_OWNER_LNAME,owner_lname);
        values.put(ItemsTable.COL_OWNER_ADDRESS,owner_address);
        values.put(ItemsTable.COL_OWNER_CON_PER,owner_con_per);
        return values;
    }


    public OwnerItems(int owner_id, String owner_fname, String owner_mname, String owner_lname, String owner_address, String owner_con_per) {
        this.owner_id = owner_id;
        this.owner_fname = owner_fname;
        this.owner_mname = owner_mname;
        this.owner_lname = owner_lname;
        this.owner_address = owner_address;
        this.owner_con_per = owner_con_per;
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

    protected OwnerItems(Parcel in) {
        owner_id = in.readInt();
        owner_fname = in.readString();
        owner_mname = in.readString();
        owner_lname = in.readString();
        owner_address = in.readString();
        owner_con_per = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(owner_id);
        dest.writeString(owner_fname);
        dest.writeString(owner_mname);
        dest.writeString(owner_lname);
        dest.writeString(owner_address);
        dest.writeString(owner_con_per);
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
