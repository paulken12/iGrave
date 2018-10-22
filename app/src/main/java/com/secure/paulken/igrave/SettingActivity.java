package com.secure.paulken.igrave;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.secure.paulken.igrave.DBHelper.DataSource;
import com.secure.paulken.igrave.Fragment.AllFragment;
import com.secure.paulken.igrave.Fragment.EmptyFragment;
import com.secure.paulken.igrave.Model.DataItems;
import com.secure.paulken.igrave.Model.DeceaseItems;
import com.secure.paulken.igrave.Model.OwnerItems;
import com.secure.paulken.igrave.Model.TombItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SettingActivity extends AppCompatActivity {

    DataSource mDataSource;

    EditText block,lot_number , dec_first, dec_mid, dec_last,own_first, own_mid, own_last, dec_bdate, dec_ddate, dec_owner,own_add,own_con,lotLat,lotLong ;
    String mFirst,mMiddle,mLast,mBDay,mDDay,mCon;
    String oFirst,oMiddle,oLast,oAdd,oCon;
    String block_spin;
    Spinner block_spinner;
    TextView tv_block_num,tv_lot_num;
    Intent intent;


    Button submit;
    boolean checked;

    DataItems dataItems,addItems;

    String status = "decease";
    String tomb_status = "occupied";
    int owner_id, decease_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        dec_first = findViewById(R.id.add_first);
        dec_mid = findViewById(R.id.add_middle);
        dec_last = findViewById(R.id.add_last);
        dec_bdate = findViewById(R.id.add_bday);
        dec_ddate = findViewById(R.id.add_dday);

        own_first = findViewById(R.id.owner_first);
        own_mid = findViewById(R.id.owner_middle);
        own_last = findViewById(R.id.owner_last);
        own_add = findViewById(R.id.owner_address);
        own_con = findViewById(R.id.owner_con);

        tv_block_num = findViewById(R.id.block_text);
        tv_lot_num = findViewById(R.id.tomb_lot_num);

        block_spinner = findViewById(R.id.block_spinner);


        submit = findViewById(R.id.add_submit);

        mDataSource = new DataSource(this);
        mDataSource.open();



        //Add spinner from db

//        List<String> items = mDataSource.block();
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        block_spinner.setAdapter(adapter);


        intent = getIntent();

        dataItems = Objects.requireNonNull(intent.getExtras()).getParcelable(AllFragment.UPDATE_KEY);
        if (dataItems != null) {

            dec_first.setText(dataItems.getDecease_fname());
            dec_mid.setText(dataItems.getDecease_mname());
            dec_last.setText(dataItems.getDecease_lname());
            dec_bdate.setText(dataItems.getDecease_bdate());
            dec_ddate.setText(dataItems.getDecease_ddate());

            own_first.setText(dataItems.getOwner_fname());
            own_mid.setText(dataItems.getOwner_mname());
            own_last.setText(dataItems.getOwner_lname());
            own_add.setText(dataItems.getOwner_address());
            own_con.setText(dataItems.getOwner_con_per());

            tv_block_num.setText(" : "+dataItems.getTomb_block());
            tv_lot_num.setText("Lot : "+dataItems.getTomb_lot_no());

            submit.setText("Update");
        }

        addItems = Objects.requireNonNull(getIntent().getExtras()).getParcelable(EmptyFragment.ADD_KEY);
        if (addItems != null) {
            toast("received from empty");
            tv_block_num.setText(": "+addItems.getTomb_block());
            tv_lot_num.setText("Lot: "+addItems.getTomb_lot_no());
            submit.setText("Add");
        }

//        block_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                block_spin = adapterView.getItemAtPosition(i).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
    }

    public void submit(View view) {


        mFirst = dec_first.getText().toString();
        mMiddle = dec_mid.getText().toString();
        mLast = dec_last.getText().toString();
        mBDay = dec_bdate.getText().toString();
        mDDay = dec_ddate.getText().toString();

        oFirst = own_first.getText().toString();
        oMiddle = own_mid.getText().toString();
        oLast = own_last.getText().toString();
        oAdd = own_add.getText().toString();
        oCon = own_con.getText().toString();

        if(dataItems != null){
            update();
            intent.removeExtra(AllFragment.UPDATE_KEY);
            clearIntent();
        }
        else {
            add();
            intent.removeExtra(EmptyFragment.ADD_KEY);
            clearIntent();
        }


    }

    public void update() {

        owner_id = dataItems.getOwner_id();
        decease_id = dataItems.getDecease_id();

        Log.e("test", ""+owner_id+": owner "+decease_id+":deceased");

        DeceaseItems deceaseItems = new DeceaseItems(decease_id,mFirst,mMiddle,mLast,mBDay,mDDay,owner_id);

        Log.e("test", "fname:"+deceaseItems.getDecease_fname()+" mname"+dataItems.getDecease_lname()+":deceased");

        OwnerItems info =  new OwnerItems(owner_id,oFirst,oMiddle,oLast,oAdd,oCon);

        mDataSource.updateDecease(deceaseItems);
        mDataSource.updateOwner(info);
        clear();

        toast("Successfully updated");

        goBack();
    }

    public void add(){

        if(mFirst.isEmpty()){
            toast("First name cannot be empty!");
            return;
        }
        if(mMiddle.isEmpty()){
            toast("Middle name cannot be empty!");
            return;
        }
        if(mLast.isEmpty()){
            toast("Last name cannot be empty!");
            return;
        }
        if(mBDay.isEmpty()){
            toast("Birth date cannot be empty!");
            return;
        }
        if(mDDay.isEmpty()){
            toast("Death date cannot be empty!");
            return;
        }

        else
        {
            OwnerItems info =  new OwnerItems(oFirst,oMiddle,oLast,oAdd,oCon);
            mDataSource.insertOwner(info);
            int lastOwnerId = mDataSource.lastIdOwner() + 1;

            Log.e("ididid",""+lastOwnerId);

            DeceaseItems deceaseItems = new DeceaseItems(mFirst,mMiddle,mLast,mBDay,mDDay,lastOwnerId);
            mDataSource.insertDecease(deceaseItems);
            int lastDeceaseId = mDataSource.lastIdDecease();


            TombItems tomb = new TombItems(addItems.getTomb_id(),addItems.getTomb_block()
                ,addItems.getTomb_lot_no()
                ,addItems.getTomb_lat()
                ,addItems.getTomb_long()
                ,tomb_status
                ,lastDeceaseId);

                mDataSource.updateTomb(tomb);

            toast("Successfully added");
        }


        goBack();
    }

//    public void onRadioButtonClicked(View view) {
//        checked = ((RadioButton) view).isChecked();
//
//        switch(view.getId()) {
//            case R.id.radio_decease:
//                if (checked)
//                    status = "decease";
//                toast(status);
//                    break;
//            case R.id.radio_reserved:
//                if (checked)
//                    status = "reserved";
//                toast(status);
//                    break;
//        }
//    }

    public void goBack(){
        Intent list = new Intent(SettingActivity.this,MainActivity.class);
        startActivity(list);
    }

    public void toast(String message){
        Toast.makeText(SettingActivity.this, message, Toast.LENGTH_LONG).show();
    }

    public void clear(){
        owner_id = 0;
        dec_first.setText("");
        dec_mid.setText("");
        dec_last.setText("");
        dec_bdate.setText("");
        dec_ddate.setText("");

        own_first.setText("");
        own_mid.setText("");
        own_last.setText("");
        own_add.setText("");
        own_con.setText("");
    }

    public void alert(String message){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setTitle("Missing");
        dialog.setMessage(message+" cannot be empty!" );
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(SettingActivity.this,"Setting Backpressed", Toast.LENGTH_LONG).show();
    }

    public  void clearIntent(){
        intent.replaceExtras(new Bundle());
        intent.setAction("");
        intent.setData(null);
        intent.setFlags(0);
    }
}
