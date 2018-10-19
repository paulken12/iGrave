package com.secure.paulken.igrave;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.secure.paulken.igrave.DBHelper.DataSource;
import com.secure.paulken.igrave.Fragment.AllFragment;
import com.secure.paulken.igrave.Fragment.EmptyFragment;
import com.secure.paulken.igrave.Model.DataItems;
import com.secure.paulken.igrave.Model.OwnerItems;
import com.secure.paulken.igrave.Model.TombItems;

import java.util.List;
import java.util.Objects;

public class SettingActivity extends AppCompatActivity {

    DataSource mDataSource;

    EditText block ;
    EditText first ;
    EditText mid;
    EditText last;
    EditText bday ;
    EditText dday ;
    EditText con;
    EditText lotLat;
    EditText lotLong ;
    RadioButton decease,reservee;
    String mFirst,mMiddle,mLast,mBDay,mDDay,mCon;


    Button submit;
    boolean checked;

    DataItems dataItems,addItems;

    String status = "decease";
    String tomb_status = "occupied";
    int owner_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        first  = findViewById(R.id.add_first);
        mid = findViewById(R.id.add_middle);
        last = findViewById(R.id.add_last);
        bday  = findViewById(R.id.add_bday);
        dday  = findViewById(R.id.add_dday);
        con = findViewById(R.id.add_con);
        submit = findViewById(R.id.add_submit);
        decease = findViewById(R.id.radio_decease);
        reservee = findViewById(R.id.radio_reserved);

        mDataSource = new DataSource(this);
        mDataSource.open();

        dataItems = Objects.requireNonNull(getIntent().getExtras()).getParcelable(AllFragment.UPDATE_KEY);
        if (dataItems != null) {
            toast("received from all/reserved");
            first.setText(dataItems.getOwner_fname());
            mid.setText(dataItems.getOwner_mname());
            last.setText(dataItems.getOwner_lname());
            bday.setText(dataItems.getOwner_bdate());
            dday.setText(dataItems.getOwner_ddate());
            con.setText(dataItems.getOwner_con_per());

            if(dataItems.getOwner_status().equalsIgnoreCase("decease"))
            {
                decease.toggle();
                reservee.setClickable(false);
            }
            else{
                reservee.toggle();
            }

            submit.setText("Update");
        }

        addItems = Objects.requireNonNull(getIntent().getExtras()).getParcelable(EmptyFragment.ADD_KEY);
        if (addItems != null) {
            toast("received from empty");
            submit.setText("Add");
        }
    }

    public void submit(View view) {


        mFirst = first.getText().toString();
        mMiddle = mid.getText().toString();
        mLast = last.getText().toString();
        mBDay = bday.getText().toString();
        mDDay = dday.getText().toString();
        mCon = con.getText().toString();


        if(dataItems != null){
            update();
        }
        else {
            add();
        }


    }

    public void update() {

        owner_id = dataItems.getOwner_id();

        OwnerItems info =  new OwnerItems(owner_id,mFirst,mMiddle,mLast,mBDay,mDDay,mCon,status);

        if(dataItems.getTomb_stat().equalsIgnoreCase("reserved")){

            TombItems tomb = new TombItems(dataItems.getTomb_id(),dataItems.getTomb_block()
                    ,dataItems.getTomb_lot_no()
                    ,dataItems.getTomb_lat()
                    ,dataItems.getTomb_long()
                    ,"occupied"
                    ,info.getOwner_id());

            mDataSource.updateOwner(info);
            mDataSource.updateTomb(tomb);
            clear();
        }else{

            mDataSource.updateOwner(info);
            clear();
        }

        toast("Successfully updated");

        goBack();
    }

    public void add(){

        OwnerItems info =  new OwnerItems(mFirst,mMiddle,mLast,mBDay,mDDay,mCon,status);
        mDataSource.insertOwner(info);
        int lastId = mDataSource.lastId();
        Log.d("hehe",""+lastId
                +" "+info.getOwner_fname()
                +" "+info.getOwner_mname()
                +" "+info.getOwner_lname()
                +" "+info.getOwner_bdate()
                +" "+info.getOwner_ddate()
                +" "+info.getOwner_con_per()
                +" "+info.getOwner_status());

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
        if(mCon.isEmpty()){
            toast("Contact person cannot be empty!");
            return;
        }
        if(!checked){
            alert("Decease or Reservation");
            return;
        }

        else
        {
            if(status.equalsIgnoreCase("reserved")) {
                tomb_status = "reserved";
            }

            TombItems tomb = new TombItems(addItems.getTomb_id(),addItems.getTomb_block()
                ,addItems.getTomb_lot_no()
                ,addItems.getTomb_lat()
                ,addItems.getTomb_long()
                ,tomb_status
                ,lastId);


                mDataSource.updateTomb(tomb);

            toast("Successfully added");
        }


        goBack();
    }

    public void onRadioButtonClicked(View view) {
        checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radio_decease:
                if (checked)
                    status = "decease";
                toast(status);
                    break;
            case R.id.radio_reserved:
                if (checked)
                    status = "reserved";
                toast(status);
                    break;
        }
    }

    public void goBack(){
        Intent list = new Intent(SettingActivity.this,MainActivity.class);
        startActivity(list);
    }

    public void toast(String message){
        Toast.makeText(SettingActivity.this, message, Toast.LENGTH_LONG).show();
    }

    public void clear(){
        owner_id = 0;
        first.setText("");
        mid.setText("");
        last.setText("");
        bday.setText("");
        dday.setText("");
        con.setText("");

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
}
