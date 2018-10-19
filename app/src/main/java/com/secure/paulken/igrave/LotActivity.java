package com.secure.paulken.igrave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.secure.paulken.igrave.DBHelper.DataSource;
import com.secure.paulken.igrave.Model.TombItems;

public class LotActivity extends AppCompatActivity {

    EditText tomb_block,tomb_lot_no,tomb_lat,tomb_long ;
    DataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lot);

        dataSource = new DataSource(this);
        dataSource.open();

        tomb_block = findViewById(R.id.block_num);
        tomb_lot_no = findViewById(R.id.lot_num);
        tomb_lat = findViewById(R.id.lat_num);
        tomb_long = findViewById(R.id.long_num);
    }

    public void submit(View view) {

        String block = tomb_block.getText().toString();
        String lot = tomb_lot_no.getText().toString();
        String lat = tomb_lat.getText().toString();
        String lon = tomb_long.getText().toString();

        if(block.isEmpty()){
            toast("Block number cannot be empty!");
        }
        if(lot.isEmpty()){
            toast("Lot number cannot be empty!");
        }
        if(lat.isEmpty()){
            toast("Latitude cannot be empty!");
        }
        if(lon.isEmpty()){
            toast("Longitude cannot be empty!");
        }
        else{

            TombItems tombItems = new TombItems(block,Integer.parseInt(lot),Double.parseDouble(lat),Double.parseDouble(lon),"empty",0);
            dataSource.insertTomb(tombItems);

            clear();
            toast("Successfully Added");

            Intent intent = new Intent(LotActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void toast(String message){
        Toast.makeText(LotActivity.this,message,Toast.LENGTH_LONG).show();
    }

    public void clear(){

        tomb_block.setText("");
        tomb_lot_no.setText("");
        tomb_lat.setText("");
        tomb_long.setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(LotActivity.this,"Lot Backpressed", Toast.LENGTH_LONG).show();
    }
}

