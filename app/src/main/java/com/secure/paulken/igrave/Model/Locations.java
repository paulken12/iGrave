package com.secure.paulken.igrave.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by _paulken on 23/10/2017.
 */

public class Locations implements Parcelable {


    private String code;
    private String coordinate;


    public Locations() {

    }

    public Locations(String code, String coordinate) {
        this.code = code;
        this.coordinate = coordinate;
    }


    public String getCode() {
        return code;
    }

    public String getCoordinate() {
        return coordinate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.coordinate);
    }

    private Locations(Parcel in) {
        this.code = in.readString();
        this.coordinate = in.readString();
    }

    public static final Creator<Locations> CREATOR = new Creator<Locations>() {
        @Override
        public Locations createFromParcel(Parcel source) {
            return new Locations(source);
        }

        @Override
        public Locations[] newArray(int size) {
            return new Locations[size];
        }
    };
}
