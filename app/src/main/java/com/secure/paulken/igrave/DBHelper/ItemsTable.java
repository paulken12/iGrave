package com.secure.paulken.igrave.DBHelper;

public class ItemsTable {


    public static final String TABLE_TOMB="table_tomb";
    public static final String TABLE_OWNER="table_owner";


    public static final String COL_TOMB_ID ="tomb_id";
    public static final String COL_TOMB_BLOCK ="tomb_block";
    public static final String COL_TOMB_LOT_NO ="tomb_lot_no";
    public static final String COL_TOMB_LAT ="tomb_lat";
    public static final String COL_TOMB_LONG ="tomb_long";
    public static final String COL_TOMB_STAT ="tomb_stat";
    public static final String COL_TOMB_OWNER ="tomb_owner";

    public static final String COL_OWNER_ID ="owner_id";
    public static final String COL_OWNER_FNAME ="owner_fname";
    public static final String COL_OWNER_MNAME ="owner_mname";
    public static final String COL_OWNER_LNAME ="owner_lname";
    public static final String COL_OWNER_BDATE ="owner_bdate";
    public static final String COL_OWNER_DDATE ="owner_ddate";
    public static final String COL_OWNER_CON_PER ="owner_con_per";
    public static final String COL_OWNER_STATUS ="owner_status";
    
    public static final String[] ALL_COLUMNS = {COL_TOMB_BLOCK,COL_TOMB_LOT_NO,COL_TOMB_LAT,COL_TOMB_LONG,COL_TOMB_STAT,COL_TOMB_OWNER};

//    public static final String ALL_COLUMNS_TOMB =
//            TABLE_TOMB+"."+COL_BLOCK+","+
//                    TABLE_LOCATION+"."+COL_FIRST_NAME+","+
//                    TABLE_LOCATION+"."+COL_MIDDLE_NAME+","+
//                    TABLE_LOCATION+"."+COL_LAST_NAME+","+
//                    TABLE_LOCATION+"."+COL_BIRTH_DATE+","+
//                    TABLE_LOCATION+"."+COL_DEATH_DATE+","+
//                    TABLE_LOCATION+"."+COL_LOT_NUM+","+
//                    TABLE_LOCATION+"."+COL_LAT+","+
//                    TABLE_LOCATION+"."+COL_LONG;



    public static final String CREATE_TOMB ="CREATE TABLE "+TABLE_TOMB+ "("+
            COL_TOMB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_TOMB_BLOCK + " TEXT," +
            COL_TOMB_LOT_NO + " INTEGER," +
            COL_TOMB_LAT + " REAL," +
            COL_TOMB_LONG + " REAL," +
            COL_TOMB_STAT + " TEXT," +
            COL_TOMB_OWNER + " INTEGER," +
            " FOREIGN KEY("+ COL_TOMB_ID +") REFERENCES "+TABLE_TOMB+"("+ COL_TOMB_ID +"))";

    public static final String CREATE_OWNER ="CREATE TABLE "+TABLE_OWNER+ "("+
            COL_OWNER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_OWNER_FNAME+" TEXT," +
            COL_OWNER_MNAME+" TEXT," +
            COL_OWNER_LNAME+" TEXT," +
            COL_OWNER_BDATE+" TEXT," +
            COL_OWNER_DDATE+" TEXT," +
            COL_OWNER_CON_PER+" TEXT," +
            COL_OWNER_STATUS+" TEXT," +
            " FOREIGN KEY("+ COL_OWNER_ID +") REFERENCES "+TABLE_OWNER+"("+ COL_OWNER_ID +"))";


    public static final String  DROP_TOMB = "DROP TABLE "+TABLE_TOMB;
    public static final String  DROP_OWNER = "DROP TABLE "+TABLE_OWNER;


}
