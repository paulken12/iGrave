package com.secure.paulken.igrave;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.secure.paulken.igrave.Adapter.ViewPagerAdapter;
import com.secure.paulken.igrave.Fragment.AllFragment;
import com.secure.paulken.igrave.Fragment.EmptyFragment;
import com.secure.paulken.igrave.Fragment.ReservedFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

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
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AllFragment(), "Occupied");
        adapter.addFragment(new ReservedFragment(), "Reserved");
        adapter.addFragment(new EmptyFragment(), "Empty");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Toast.makeText(MainActivity.this,"Main Backpressed", Toast.LENGTH_LONG).show();
    }
}
