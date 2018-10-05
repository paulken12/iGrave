package com.secure.paulken.igrave.DBHelper;

public class ItemsTable {

    public static final String TABLE_LOCATION = "location_table";


    public static final String COL_ID = "ID";
    public static final String COL_BLOCK = "BLOCK_NAME";
    public static final String COL_LOT = "LOT_NAME";
    public static final String COL_LAT = "LOT_LAT";
    public static final String COL_LONG = "LOT_LONG";

    public static final String[] ALL_COLUMNS = {COL_BLOCK,COL_LOT,COL_LAT,COL_LONG};
    public static final String ALL_COLUMNS_LOCATION =
            TABLE_LOCATION+"."+COL_BLOCK+","+
                    TABLE_LOCATION+"."+COL_LOT+","+
                    TABLE_LOCATION+"."+COL_LAT+","+
                    TABLE_LOCATION+"."+COL_LONG;



    public static final String CREATE_LOCATION ="CREATE TABLE "+TABLE_LOCATION+ "("+
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_BLOCK + " TEXT," +
            COL_LOT+" INTEGER," +
            COL_LAT+" REAL," +
            COL_LONG+" REAL," +
            " FOREIGN KEY("+ COL_ID +") REFERENCES "+TABLE_LOCATION+"("+ COL_ID +"))";

    public static final String  DROP_LOCATION = "DROP TABLE "+TABLE_LOCATION;

}
