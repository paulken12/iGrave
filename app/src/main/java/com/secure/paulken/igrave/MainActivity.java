package com.secure.paulken.igrave;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.secure.paulken.igrave.Adapter.ViewPagerAdapter;
import com.secure.paulken.igrave.DBHelper.DataSource;
import com.secure.paulken.igrave.Fragment.AllFragment;
import com.secure.paulken.igrave.Fragment.EmptyFragment;
import com.secure.paulken.igrave.Fragment.ReservedFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    DataSource mDataSource;

    TextView total_count,occupied,empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        mDataSource = new DataSource(this);
        mDataSource.open();

        total_count = findViewById(R.id.total_count);
        occupied = findViewById(R.id.occupied_count);
        empty = findViewById(R.id.empty_count);

        int occu = mDataSource.occupied();
        int emp = mDataSource.empty();
        long tot_count = mDataSource.getItemCount();

        total_count.setText("Total No. of Lots : "+tot_count);
        occupied.setText("Total Occupied Lots : "+occu);
        empty.setText("Total Available : "+emp);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AllFragment(), "Occupied");
//        adapter.addFragment(new ReservedFragment(), "Reserved");
        adapter.addFragment(new EmptyFragment(), "Available");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Toast.makeText(MainActivity.this,"Main Backpressed", Toast.LENGTH_LONG).show();
    }
}
