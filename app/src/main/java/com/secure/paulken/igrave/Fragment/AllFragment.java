package com.secure.paulken.igrave.Fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.secure.paulken.igrave.Adapter.OwnerAdapter;
import com.secure.paulken.igrave.DBHelper.DataSource;
import com.secure.paulken.igrave.ListActivity;
import com.secure.paulken.igrave.Model.DataItems;
import com.secure.paulken.igrave.Model.DeceaseItems;
import com.secure.paulken.igrave.Model.TombItems;
import com.secure.paulken.igrave.R;
import com.secure.paulken.igrave.SettingActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends Fragment {

    private ListView listView;
    private List<TombItems> tombItems;
    DataSource dataSource;
    private List<DataItems> dataItems;
    Spinner type_spinner;
    String type;
    String deceased = "Deceased Information";
    String family = "Family/Relative Info";
    EditText search;
    Button clear;

    public static final String UPDATE_KEY = "UPDATE_OWNER";

    public AllFragment() {
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
        View mView = inflater.inflate(R.layout.fragment_all, container, false);

        listView = mView.findViewById(R.id.tomb_list);
        type_spinner = mView.findViewById(R.id.type_spinner);
        search = mView.findViewById(R.id.search_all);
        clear = mView.findViewById(R.id.btn_search_all);

        List<String> list = new ArrayList<>();
        list.add(deceased);
        list.add(family);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spinner.setAdapter(adapter);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setText("");
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.length() == 0)
                {
                    displayDeceased();
                }
                else {
                    searchMe(charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = adapterView.getItemAtPosition(i).toString();

                if(type.equalsIgnoreCase(deceased))
                {
                    displayDeceased();
                }
                else {
                    displayOwner();
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
                dialog.setTitle("Update");
                dialog.setMessage("Are you sure you want to update "+ items.getDecease_fname()+" "+items.getDecease_lname()+"?" );
                dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getActivity(),SettingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra(UPDATE_KEY ,items);
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

        return mView;
    }


    public void displayDeceased()
    {

        dataSource.open();
        dataItems = dataSource.getAll();
        ListAdapter listAdapter = new ListAdapter(getContext(), dataItems);
        listView.setAdapter(listAdapter);
    }

    public void displayOwner()
    {

        dataSource.open();
        dataItems = dataSource.getAll();
        OwnerAdapter ownerAdapter = new OwnerAdapter(getContext(),dataItems);
        listView.setAdapter(ownerAdapter);
    }

    public void searchMe(CharSequence sequence)
    {

        dataSource.open();
        dataItems = dataSource.searchListDeceased(sequence);
        ListAdapter listAdapter = new ListAdapter(getContext(),dataItems);
        listView.setAdapter(listAdapter);
    }


    public void toast(String message){
        Toast.makeText(getContext(), message,Toast.LENGTH_LONG).show();
    }


    @Override
    public void onResume() {
        super.onResume();
        dataSource.open();
    }

    @Override
    public void onPause() {
        super.onPause();
        dataSource.close();
    }
}
