package com.secure.paulken.igrave.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.secure.paulken.igrave.Model.DataItems;
import com.secure.paulken.igrave.Model.DeceaseItems;
import com.secure.paulken.igrave.Model.OwnerItems;
import com.secure.paulken.igrave.Model.TombItems;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private SQLiteOpenHelper mDbHelper;

    public DataSource(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void open() {
        mDatabase = mDbHelper.getWritableDatabase();
//        Toast.makeText(mContext, "created database", Toast.LENGTH_SHORT).show();
    }

    public void close() {
        mDbHelper.close();
    }


    public TombItems insertTomb(TombItems tombItems) {
        ContentValues contentValues = tombItems.tombValues();
        mDatabase.insert(ItemsTable.TABLE_TOMB, null, contentValues);
        return tombItems;
    }

    public OwnerItems insertOwner(OwnerItems ownerItems) {
        ContentValues contentValues = ownerItems.ownerValues();
        mDatabase.insert(ItemsTable.TABLE_OWNER, null, contentValues);
        return ownerItems;
    }

    public DeceaseItems insertDecease(DeceaseItems deceaseItems) {
        ContentValues contentValues = deceaseItems.deceaseValues();
        mDatabase.insert(ItemsTable.TABLE_DECEASE, null, contentValues);
        return deceaseItems;
    }


    public OwnerItems updateOwner(OwnerItems ownerItems) {
        ContentValues contentValues = ownerItems.ownerValues();
        mDatabase.update(ItemsTable.TABLE_OWNER, contentValues, ItemsTable.COL_OWNER_ID + " = ?"
                , new String[]{String.valueOf(ownerItems.getOwner_id())});
        return ownerItems;
    }

    public DeceaseItems updateDecease(DeceaseItems deceaseItems) {
        ContentValues contentValues = deceaseItems.deceaseValues();
        mDatabase.update(ItemsTable.TABLE_DECEASE, contentValues, ItemsTable.COL_DECEASE_ID + " = ?"
                , new String[]{String.valueOf(deceaseItems.getDecease_id())});
        return deceaseItems;
    }

    public TombItems updateTomb(TombItems tombItems) {
        ContentValues contentValues = tombItems.tombValues();
        mDatabase.update(ItemsTable.TABLE_TOMB, contentValues, ItemsTable.COL_TOMB_ID + " = ?"
                , new String[]{String.valueOf(tombItems.getTomb_id())});
        return tombItems;
    }

    public int lastIdDecease() {
        int id = 0;
        Cursor cursor = mDatabase.rawQuery("select owner_id from table_owner order by owner_id desc limit 1",null);
        if(cursor.moveToLast()){
            id = cursor.getInt(0);
        }

        return id;
    }

    public int lastIdOwner() {
        int id = 0;
        Cursor cursor = mDatabase.rawQuery("select decease_id from table_decease order by decease_id desc limit 1",null);
        if(cursor.moveToLast()){
            id = cursor.getInt(0);
        }

        return id;
    }

    public int occupied() {
        int occupied = 0;
        Cursor cursor = mDatabase.rawQuery("select count(tomb_id) from table_tomb where tomb_stat = 'occupied' limit 1",null);
        if(cursor.moveToLast()){
            occupied = cursor.getInt(0);
        }

        return occupied;
    }

    public int empty() {
        int occupied = 0;
        Cursor cursor = mDatabase.rawQuery("select count(tomb_id) from table_tomb where tomb_stat = 'empty' limit 1",null);
        if(cursor.moveToLast()){
            occupied = cursor.getInt(0);
        }

        return occupied;
    }

    public List<String> block() {
        List<String> block = new ArrayList<>();

        Cursor cursor = mDatabase.rawQuery("select distinct tomb_block from table_tomb order by tomb_block desc",null);
        if(cursor.moveToFirst()){
            do{
                block.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }

        return block;
    }


    public List<DataItems> getBlock() {
        List<DataItems> dataItems = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery("select distinct tomb_id,tomb_block from table_tomb ", null);
        while (cursor.moveToNext()) {
            DataItems items = new DataItems();

            items.setTomb_id(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_ID)));
            items.setTomb_block(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_TOMB_BLOCK)));
            dataItems.add(items);
        }
        cursor.close();
        return dataItems;
    }

    public boolean ifItemExist(String code) {
        return DatabaseUtils.longForQuery(mDatabase, "SELECT first_name FROM table_location WHERE first_name = ? LIMIT 1", new String[]{code}) > 0;
    }


    public long getItemCount() {
        return DatabaseUtils.queryNumEntries(mDatabase, ItemsTable.TABLE_TOMB);
    }

//    public List<TombItems> getAllTomb()
//    {
//        List<TombItems> tombItems = new ArrayList<>();
//        Cursor cursor = mDatabase.query(ItemsTable.TABLE_TOMB,ItemsTable.ALL_COLUMNS,null,null,null,null,ItemsTable.COL_TOMB_LOT_NO);
//        while (cursor.moveToNext())
//        {
//            TombItems items = new TombItems();
//
//            items.setTomb_block(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_TOMB_BLOCK)));
//            items.setTomb_lot_no(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_LOT_NO)));
//            items.setTomb_lat(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COL_TOMB_LAT)));
//            items.setTomb_long(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COL_TOMB_LONG)));
//            items.setTomb_stat(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_TOMB_STAT)));
//            items.setTomb_owner(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_OWNER)));
//            tombItems.add(items);
//        }
//        cursor.close();
//        return tombItems;
//    }

    public List<DataItems> getAllItems() {
        List<DataItems> dataItems = new ArrayList<>();
        Cursor cursor =  mDatabase.rawQuery("select * from table_tomb " +
                "left join table_decease " +
                "on table_tomb.tomb_owner = table_decease.decease_id " +
                "left join table_owner " +
                "on table_owner.owner_id = table_decease.decease_owner ", null);
        while (cursor.moveToNext()) {
            DataItems items = new DataItems();

            items.setTomb_id(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_ID)));
            items.setTomb_block(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_TOMB_BLOCK)));
            items.setTomb_lot_no(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_LOT_NO)));
            items.setTomb_lat(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COL_TOMB_LAT)));
            items.setTomb_long(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COL_TOMB_LONG)));
            items.setTomb_stat(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_TOMB_STAT)));
            items.setTomb_owner(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_OWNER)));
            items.setOwner_id(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_OWNER_ID)));
            items.setOwner_fname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_FNAME)));
            items.setOwner_mname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_MNAME)));
            items.setOwner_lname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_LNAME)));
            items.setOwner_address(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_ADDRESS)));
            items.setOwner_con_per(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_CON_PER)));
            items.setDecease_id(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_DECEASE_ID)));
            items.setDecease_fname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_DECEASE_FNAME)));
            items.setDecease_mname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_DECEASE_MNAME)));
            items.setDecease_lname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_DECEASE_LNAME)));
            items.setDecease_ddate(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_DECEASE_DDATE)));
            items.setDecease_bdate(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_DECEASE_DDATE)));
            items.setDecease_owner(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_DECEASE_OWNER)));
            dataItems.add(items);
        }
        cursor.close();
        return dataItems;
    }

//    public List<DataItems> getReserved() {
//        List<DataItems> dataItems = new ArrayList<>();
//        Cursor cursor = mDatabase.rawQuery("select * from table_tomb " +
//                "inner join table_owner " +
//                "on table_tomb.tomb_id = table_owner.owner_id " +
//                "where table_tomb.tomb_stat = 'reserved'", null);
//        while (cursor.moveToNext()) {
//            DataItems items = new DataItems();
//
//            items.setTomb_id(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_ID)));
//            items.setTomb_block(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_TOMB_BLOCK)));
//            items.setTomb_lot_no(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_LOT_NO)));
//            items.setTomb_lat(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COL_TOMB_LAT)));
//            items.setTomb_long(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COL_TOMB_LONG)));
//            items.setTomb_stat(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_TOMB_STAT)));
//            items.setTomb_owner(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_OWNER)));
//            items.setOwner_id(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_OWNER_ID)));
//            items.setOwner_fname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_FNAME)));
//            items.setOwner_mname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_MNAME)));
//            items.setOwner_lname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_LNAME)));
//            items.setOwner_bdate(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_BDATE)));
//            items.setOwner_ddate(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_DDATE)));
//            items.setOwner_con_per(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_CON_PER)));
//            dataItems.add(items);
//        }
//        cursor.close();
//        return dataItems;
//    }

    public List<DataItems> getEmpty(CharSequence sequence) {
        List<DataItems> dataItems = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery("select * from table_tomb where table_tomb.tomb_stat = 'empty'" +
                "and table_tomb.tomb_block = ?", new String[]{sequence.toString()});
        while (cursor.moveToNext()) {
            DataItems items = new DataItems();

            items.setTomb_id(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_ID)));
            items.setTomb_block(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_TOMB_BLOCK)));
            items.setTomb_lot_no(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_LOT_NO)));
            items.setTomb_lat(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COL_TOMB_LAT)));
            items.setTomb_long(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COL_TOMB_LONG)));
            items.setTomb_stat(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_TOMB_STAT)));
            items.setTomb_owner(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_OWNER)));
            dataItems.add(items);
        }
        cursor.close();
        return dataItems;
    }

    public List<DataItems> getEmptyLot(String lot, String block) {
        List<DataItems> dataItems = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery("select * from table_tomb where table_tomb.tomb_stat = 'empty'" +
                "and table_tomb.tomb_block = '"+block+"' " +
                "and table_tomb.tomb_lot_no  = "+ Integer.parseInt(lot),null);

        Log.e("test", ""+lot+" :"+block);
        while (cursor.moveToNext()) {
            DataItems items = new DataItems();

            items.setTomb_id(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_ID)));
            items.setTomb_block(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_TOMB_BLOCK)));
            items.setTomb_lot_no(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_LOT_NO)));
            items.setTomb_lat(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COL_TOMB_LAT)));
            items.setTomb_long(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COL_TOMB_LONG)));
            items.setTomb_stat(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_TOMB_STAT)));
            items.setTomb_owner(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_OWNER)));
            dataItems.add(items);
        }
        cursor.close();
        return dataItems;
    }

    public List<DataItems> getAll() {
        List<DataItems> dataItems = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery("select * from table_tomb " +
                "left join table_decease " +
                "on table_tomb.tomb_owner = table_decease.decease_id " +
                "left join table_owner " +
                "on table_owner.owner_id = table_decease.decease_owner " +
                "where table_tomb.tomb_stat = 'occupied'", null);

        while (cursor.moveToNext()) {
            DataItems items = new DataItems();

            items.setTomb_id(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_ID)));
            items.setTomb_block(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_TOMB_BLOCK)));
            items.setTomb_lot_no(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_LOT_NO)));
            items.setTomb_lat(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COL_TOMB_LAT)));
            items.setTomb_long(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COL_TOMB_LONG)));
            items.setTomb_stat(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_TOMB_STAT)));
            items.setTomb_owner(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_OWNER)));
            items.setOwner_id(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_OWNER_ID)));
            items.setOwner_fname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_FNAME)));
            items.setOwner_mname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_MNAME)));
            items.setOwner_lname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_LNAME)));
            items.setOwner_address(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_ADDRESS)));
            items.setOwner_con_per(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_CON_PER)));
            items.setDecease_id(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_DECEASE_ID)));
            items.setDecease_fname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_DECEASE_FNAME)));
            items.setDecease_mname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_DECEASE_MNAME)));
            items.setDecease_lname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_DECEASE_LNAME)));
            items.setDecease_ddate(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_DECEASE_DDATE)));
            items.setDecease_bdate(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_DECEASE_DDATE)));
            items.setDecease_owner(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_DECEASE_OWNER)));
            dataItems.add(items);
        }
        cursor.close();
        return dataItems;
    }


    public List<DataItems> searchListDeceased(CharSequence search) {
        CharSequence fSearch = "%"+search+"%";
        List<DataItems> dataItems = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery("select * ,table_decease.decease_fname || ' ' || table_decease.decease_lname as fullname from table_tomb " +
                "left join table_decease " +
                "on table_tomb.tomb_owner = table_decease.decease_id " +
                "left join table_owner " +
                "on table_owner.owner_id = table_decease.decease_owner " +
                "where table_tomb.tomb_stat = 'occupied' " +
                "and table_decease.decease_fname  like ? " +
                "or table_decease.decease_mname like ? " +
                "or table_decease.decease_lname like ? " +
                "or fullname like ?", new String[]{fSearch.toString(), fSearch.toString(), fSearch.toString(), fSearch.toString()});

        while (cursor.moveToNext()) {
            DataItems items = new DataItems();

            items.setTomb_id(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_ID)));
            items.setTomb_block(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_TOMB_BLOCK)));
            items.setTomb_lot_no(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_LOT_NO)));
            items.setTomb_lat(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COL_TOMB_LAT)));
            items.setTomb_long(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COL_TOMB_LONG)));
            items.setTomb_stat(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_TOMB_STAT)));
            items.setTomb_owner(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_OWNER)));
            items.setOwner_id(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_OWNER_ID)));
            items.setOwner_fname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_FNAME)));
            items.setOwner_mname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_MNAME)));
            items.setOwner_lname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_LNAME)));
            items.setOwner_address(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_ADDRESS)));
            items.setOwner_con_per(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_CON_PER)));
            items.setDecease_id(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_DECEASE_ID)));
            items.setDecease_fname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_DECEASE_FNAME)));
            items.setDecease_mname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_DECEASE_MNAME)));
            items.setDecease_lname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_DECEASE_LNAME)));
            items.setDecease_ddate(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_DECEASE_DDATE)));
            items.setDecease_bdate(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_DECEASE_DDATE)));
            items.setDecease_owner(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_DECEASE_OWNER)));
            dataItems.add(items);
        }
        cursor.close();
        return dataItems;
    }

    public List<DataItems> searchDeceasedInLot(String search) {
        List<DataItems> dataItems = new ArrayList<>();
        String fSearch = "%"+search+"%";
        String full = "%"+search;
        Cursor cursor = mDatabase.rawQuery("select * ,table_decease.decease_fname || ' ' || table_decease.decease_lname as fullname from table_tomb " +
                "left join table_decease " +
                "on table_tomb.tomb_owner = table_decease.decease_id " +
                "left join table_owner " +
                "on table_owner.owner_id = table_decease.decease_owner " +
                "where table_tomb.tomb_stat = 'occupied' " +
                "and table_decease.decease_fname  like ? " +
                "or table_decease.decease_mname like ? " +
                "or table_decease.decease_lname like ? " +
                "or fullname like ?", new String[]{fSearch, fSearch, fSearch, full});

        while (cursor.moveToNext()) {
            DataItems items = new DataItems();

            items.setTomb_id(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_ID)));
            items.setTomb_block(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_TOMB_BLOCK)));
            items.setTomb_lot_no(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_LOT_NO)));
            items.setTomb_lat(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COL_TOMB_LAT)));
            items.setTomb_long(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COL_TOMB_LONG)));
            items.setTomb_stat(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_TOMB_STAT)));
            items.setTomb_owner(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_TOMB_OWNER)));
            items.setOwner_id(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_OWNER_ID)));
            items.setOwner_fname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_FNAME)));
            items.setOwner_mname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_MNAME)));
            items.setOwner_lname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_LNAME)));
            items.setOwner_address(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_ADDRESS)));
            items.setOwner_con_per(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_OWNER_CON_PER)));
            items.setDecease_id(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_DECEASE_ID)));
            items.setDecease_fname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_DECEASE_FNAME)));
            items.setDecease_mname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_DECEASE_MNAME)));
            items.setDecease_lname(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_DECEASE_LNAME)));
            items.setDecease_ddate(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_DECEASE_DDATE)));
            items.setDecease_bdate(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_DECEASE_DDATE)));
            items.setDecease_owner(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_DECEASE_OWNER)));
            dataItems.add(items);
        }
        cursor.close();
        return dataItems;
    }
//    public List<TombItems> getItems() {
//        List<TombItems> dataItems = new ArrayList<>();
//        String selling = "SELECT * FROM " + ItemsTable.TABLE_LOCATION;
//
//        Cursor cursor = mDatabase.rawQuery(selling, null);
//        while (cursor.moveToNext()) {
//            TombItems items = new TombItems();
//            items.setBlock_name(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_BLOCK)));
//            items.setBlock_name(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_FIRST_NAME)));
//            items.setBlock_name(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_MIDDLE_NAME)));
//            items.setBlock_name(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_LAST_NAME)));
//            items.setBlock_name(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_BIRTH_DATE)));
//            items.setBlock_name(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_DEATH_DATE)));
//            items.setLot_num(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_LOT_NUM)));
//            items.setLatitude(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COL_LAT)));
//            items.setLongitude(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COL_LONG)));
//            dataItems.add(items);
//        }
//        cursor.close();
//        return dataItems;
//    }

//    public List<TombItems> getData(String code) {
//
//        List<TombItems> dataItems = new ArrayList<>();
//
//        Cursor  cursor = mDatabase.rawQuery("SELECT * FROM location_table WHERE first_name=?", new String[] {code + ""});
//        while (cursor.moveToNext()) {
//            TombItems items = new TombItems();
//            items.setBlock_name(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_BLOCK)));
//            items.setFirst_name(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_FIRST_NAME)));
//            items.setMiddle_name(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_MIDDLE_NAME)));
//            items.setLast_name(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_LAST_NAME)));
//            items.setBirth_date(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_BIRTH_DATE)));
//            items.setDeath_date(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_DEATH_DATE)));
//            items.setLot_num(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_LOT_NUM)));
//            items.setLatitude(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COL_LAT)));
//            items.setLongitude(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COL_LONG)));
//            dataItems.add(items);
//        }
//        cursor.close();
//
//        return dataItems;
//    }
//
//    public List<TombItems> searching(String table,String column,CharSequence code)
//    {
//        List<TombItems> dataItems = new ArrayList<>();
//        String searchItem = "SELECT first_name FROM  location_table ";
//        Cursor cursor = mDatabase.rawQuery(searchItem,null);
//        if(cursor.getCount() != 0)
//        {
//            if (cursor.moveToFirst()){
//                do{
//                    TombItems items = new TombItems();
//                    items.setFirst_name(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_FIRST_NAME)));
//                    items.setMiddle_name(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_MIDDLE_NAME)));
//                    items.setLast_name(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_LAST_NAME)));
//
//                    dataItems.add(items);
//                }while (cursor.moveToNext());
//            }
//        }
//        cursor.close();
//        return dataItems;
//    }

    public String getEmployeeName(String empNo) {
        Cursor cursor = null;
        String empName = "";
        try {
            cursor = mDatabase.rawQuery("SELECT first_name FROM table_location WHERE EmpNo=?", new String[]{empNo + ""});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                empName = cursor.getString(cursor.getColumnIndex("EmployeeName"));
            }
            return empName;
        } finally {
            cursor.close();
        }
    }
}
