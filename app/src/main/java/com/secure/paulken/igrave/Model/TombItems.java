package com.secure.paulken.igrave.Model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.secure.paulken.igrave.DBHelper.ItemsTable;

public class TombItems implements Parcelable {

    private int tomb_id;
    private String tomb_block;
    private int tomb_lot_no;
    private double tomb_lat;
    private double tomb_long;
    private String tomb_stat;
    private int tomb_owner;


    public TombItems(){}

    public ContentValues tombValues()
    {
        ContentValues values = new ContentValues(6);
        values.put(ItemsTable.COL_TOMB_BLOCK,tomb_block);
        values.put(ItemsTable.COL_TOMB_LOT_NO,tomb_lot_no);
        values.put(ItemsTable.COL_TOMB_LAT,tomb_lat);
        values.put(ItemsTable.COL_TOMB_LONG,tomb_long);
        values.put(ItemsTable.COL_TOMB_STAT,tomb_stat);
        values.put(ItemsTable.COL_TOMB_OWNER,tomb_owner);
        return values;
    }

    public TombItems(String tomb_block, int tomb_lot_no, double tomb_lat, double tomb_long, String tomb_stat, int tomb_owner) {
        this.tomb_block = tomb_block;
        this.tomb_lot_no = tomb_lot_no;
        this.tomb_lat = tomb_lat;
        this.tomb_long = tomb_long;
        this.tomb_stat = tomb_stat;
        this.tomb_owner = tomb_owner;
    }

    public TombItems(int tomb_id, String tomb_block, int tomb_lot_no, double tomb_lat, double tomb_long, String tomb_stat, int tomb_owner) {
        this.tomb_id = tomb_id;
        this.tomb_block = tomb_block;
        this.tomb_lot_no = tomb_lot_no;
        this.tomb_lat = tomb_lat;
        this.tomb_long = tomb_long;
        this.tomb_stat = tomb_stat;
        this.tomb_owner = tomb_owner;
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

    protected TombItems(Parcel in) {
        tomb_id = in.readInt();
        tomb_block = in.readString();
        tomb_lot_no = in.readInt();
        tomb_lat = in.readDouble();
        tomb_long = in.readDouble();
        tomb_stat = in.readString();
        tomb_owner = in.readInt();
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
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TombItems> CREATOR = new Creator<TombItems>() {
        @Override
        public TombItems createFromParcel(Parcel in) {
            return new TombItems(in);
        }

        @Override
        public TombItems[] newArray(int size) {
            return new TombItems[size];
        }
    };
}
