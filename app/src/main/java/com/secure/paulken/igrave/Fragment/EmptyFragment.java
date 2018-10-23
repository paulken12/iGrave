package com.secure.paulken.igrave.Fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.secure.paulken.igrave.Adapter.ListAdapter;
import com.secure.paulken.igrave.DBHelper.DataSource;
import com.secure.paulken.igrave.ListActivity;
import com.secure.paulken.igrave.LotActivity;
import com.secure.paulken.igrave.Model.DataItems;
import com.secure.paulken.igrave.Model.TombItems;
import com.secure.paulken.igrave.R;
import com.secure.paulken.igrave.SettingActivity;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmptyFragment extends Fragment {

    private ListView listView;
    DataSource dataSource;
    private List<DataItems> dataItems;
    public static final String ADD_KEY = "ADD_KEY";
    Button add_lot;
    Spinner block_spinner;
    String block_spin = "9";
    EditText search_lot;

    public EmptyFragment() {
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
        View mView = inflater.inflate(R.layout.fragment_empty, container, false);

        listView = mView.findViewById(R.id.tomb_list);
        block_spinner = mView.findViewById(R.id.search_block);
        search_lot = mView.findViewById(R.id.search_lot);

        List<String> items = dataSource.block();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        block_spinner.setAdapter(adapter);

        display(block_spin);

        search_lot.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0)
                {
                    display(block_spin);
                }
                else {
                    searchMe(charSequence.toString(),block_spin);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        block_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                block_spin = adapterView.getItemAtPosition(i).toString();


                if(block_spin.equalsIgnoreCase("9"))
                {
                    display(block_spin);
                }
                else {
                    display(block_spin);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final DataItems items = dataItems.get(i);
                AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                dialog.setCancelable(false);
                dialog.setTitle("Add");
                dialog.setMessage("Are you sure you want to add new record?");
                dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getActivity(),SettingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra(ADD_KEY ,items);
                        startActivity(intent);
                        getActivity().finish();
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

        add_lot = mView.findViewById(R.id.add_lot);

        add_lot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LotActivity.class);
                startActivity(intent);
            }
        });

        return mView;
    }

    public void display(CharSequence sequence)
    {
        dataSource.open();
        dataItems = dataSource.getEmpty(sequence);
        ListAdapter listAdapter = new ListAdapter(getContext(), dataItems);
        listView.setAdapter(listAdapter);
    }

//    public void displaySearch()
//    {
//        dataSource.open();
//        dataItems = dataSource.getEmpty();
//        ListAdapter listAdapter = new ListAdapter(getContext(), dataItems);
//        listView.setAdapter(listAdapter);
//    }

    public void searchMe(String lot,String block)
    {

        dataSource.open();
        dataItems = dataSource.getEmptyLot(lot, block);
        ListAdapter listAdapter = new ListAdapter(getContext(), dataItems);
        listView.setAdapter(listAdapter);
    }


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

    public void toast(String message){
        Toast.makeText(getContext(), message,Toast.LENGTH_LONG).show();
    }


}
