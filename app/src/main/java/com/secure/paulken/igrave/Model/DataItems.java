package com.secure.paulken.igrave.Model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.secure.paulken.igrave.DBHelper.ItemsTable;


/**
 * Created by _paulken on 08/11/2017.
 */

public class DataItems implements Parcelable {

    private String block_name;
    private int lot_num;
    private double latitude;
    private double longitude;

    public DataItems() {
    }

    public DataItems(String block_name, int lot_num, double latitude, double longitude) {
        this.block_name = block_name;
        this.lot_num = lot_num;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getBlock_name() {
        return block_name;
    }

    public void setBlock_name(String block_name) {
        this.block_name = block_name;
    }

    public int getLot_num() {
        return lot_num;
    }

    public void setLot_num(int lot_num) {
        this.lot_num = lot_num;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    protected DataItems(Parcel in) {
        block_name = in.readString();
        lot_num = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(block_name);
        dest.writeInt(lot_num);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
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

    public ContentValues locationValues()
    {
        ContentValues values = new ContentValues(4);
        values.put(ItemsTable.COL_BLOCK,block_name);
        values.put(ItemsTable.COL_LOT, lot_num);
        values.put(ItemsTable.COL_LAT,latitude);
        values.put(ItemsTable.COL_LONG,longitude);
        return values;
    }



}
