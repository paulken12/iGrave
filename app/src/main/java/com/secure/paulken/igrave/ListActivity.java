package com.secure.paulken.igrave;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.secure.paulken.igrave.Adapter.ListAdapter;
import com.secure.paulken.igrave.DBHelper.DataSource;
import com.secure.paulken.igrave.Model.DataItems;
import com.secure.paulken.igrave.Model.TombItems;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    private ListView listView;
    DataSource mDataSource;
    private List<TombItems> tombItems;
    private List<DataItems> dataItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.tomb_list);

        mDataSource = new DataSource(this);
        mDataSource.open();

//        display();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                /*=====================================================================================

                if(status == empty){
                    open Add or Reserve Dialog
                }
                else{
                    open Update dialog
                }

e
                =====================================================================================*/

                final DataItems items = dataItems.get(i);

                if(items.getTomb_stat().equalsIgnoreCase("empty")){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ListActivity.this);
                    dialog.setCancelable(false);
                    dialog.setTitle("Add");
                    dialog.setMessage("Are you sure you want to add new record?");
                    dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(ListActivity.this,SettingActivity.class);
                            startActivity(intent);
                        }
                    })
                            .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    final AlertDialog alert = dialog.create();
                    alert.show();

                }
                else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ListActivity.this);
                    dialog.setCancelable(false);
                    dialog.setTitle("Update");
                    dialog.setMessage("Are you sure you want to update "+ items.getOwner_fname()+" "+items.getOwner_lname()+"?" );
                    dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(ListActivity.this,SettingActivity.class);
                            intent.putExtra("UPDATE_OWNER" ,items);
                            startActivity(intent);
                        }
                    })
                            .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    final AlertDialog alert = dialog.create();
                    alert.show();

                }


                return false;
            }
        });

    }

//    public void display()
//    {
//        dataItems = mDataSource.getAllItems();
//        ListAdapter listAdapter = new ListAdapter(this, dataItems);
//        listView.setAdapter(listAdapter);
//    }


    @Override
    protected void onResume() {
        super.onResume();
        mDataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDataSource.close();
    }
}
