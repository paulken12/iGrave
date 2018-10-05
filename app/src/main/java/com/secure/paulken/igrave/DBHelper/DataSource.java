package com.secure.paulken.igrave.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


import com.secure.paulken.igrave.Model.DataItems;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private SQLiteOpenHelper mDbHelper;

    public DataSource(Context context)
    {
        this.mContext = context;
        mDbHelper = new DBHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void open()
    {
        mDatabase = mDbHelper.getWritableDatabase();
        Toast.makeText(mContext, "created database", Toast.LENGTH_SHORT).show();
    }

    public void close()
    {
        mDbHelper.close();
    }


    public DataItems insertLocation(DataItems dataItems)
    {
        ContentValues contentValues = dataItems.locationValues();
        mDatabase.insert(ItemsTable.TABLE_LOCATION,null,contentValues);
        return dataItems;
    }


    public boolean ifItemExist(String code,String table)
    {
        return DatabaseUtils.longForQuery(mDatabase, "SELECT COUNT(*) FROM "+table+ " WHERE CODE = ? LIMIT 1", new String[]{code})>0;
    }


    public long getItemCount()
    {
        return DatabaseUtils.queryNumEntries(mDatabase,ItemsTable.TABLE_LOCATION);
    }

    public List<DataItems> getItems()
    {
        List<DataItems> dataItems = new ArrayList<>();
        String selling = "SELECT * FROM "+ItemsTable.TABLE_LOCATION;

        Cursor cursor = mDatabase.rawQuery(selling,null);
        while (cursor.moveToNext())
        {
            DataItems items = new DataItems();
            items.setBlock_name(cursor.getString(cursor.getColumnIndex(ItemsTable.COL_BLOCK)));
            items.setLot_num(cursor.getInt(cursor.getColumnIndex(ItemsTable.COL_LOT)));
            items.setLatitude(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COL_LAT)));
            items.setLongitude(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COL_LONG)));
            dataItems.add(items);
        }
        cursor.close();
        return dataItems;
    }
}
