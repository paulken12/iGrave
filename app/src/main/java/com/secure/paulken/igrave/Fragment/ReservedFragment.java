package com.secure.paulken.igrave.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.secure.paulken.igrave.Adapter.ListAdapter;
import com.secure.paulken.igrave.DBHelper.DataSource;
import com.secure.paulken.igrave.Model.DataItems;
import com.secure.paulken.igrave.Model.TombItems;
import com.secure.paulken.igrave.R;
import com.secure.paulken.igrave.SettingActivity;

import java.util.List;
import java.util.Objects;

public class ReservedFragment extends Fragment {

    private ListView listView;
    DataSource dataSource;
    private List<DataItems> dataItems;


    public ReservedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new DataSource(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_reserved, container, false);

        listView = mView.findViewById(R.id.tomb_list);

//        display();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final DataItems items = dataItems.get(i);

                AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                dialog.setCancelable(false);
                dialog.setTitle("Update");
                dialog.setMessage("Are you sure you want to update "+ items.getOwner_fname()+" "+items.getOwner_lname()+"?" );
                dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getActivity(),SettingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(AllFragment.UPDATE_KEY ,items);
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



                return false;
            }
        });

        return mView;
    }

//    public void display()
//    {
//        dataSource.open();
//        dataItems = dataSource.getReserved();
//        ListAdapter listAdapter = new ListAdapter(getContext(), dataItems);
//        listView.setAdapter(listAdapter);
//    }


    @Override
    public void onResume() {
        super.onResume();
        dataSource.open();
//        display();
    }

    @Override
    public void onPause() {
        super.onPause();
        dataSource.close();
    }


}
