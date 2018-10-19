package com.secure.paulken.igrave.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.secure.paulken.igrave.Model.DataItems;
import com.secure.paulken.igrave.R;
import com.secure.paulken.igrave.SettingActivity;

import java.util.List;


public class ListAdapter extends ArrayAdapter {
    private final List<DataItems> mlist;

    public ListAdapter(Context context, List<DataItems> list) {
        super(context, R.layout.row_item);
        mlist = list;
    }

    static class LayoutHandler {
        TextView block, lot_num, status, owner, bdate, ddate, contact;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, final View convertView, @NonNull ViewGroup parent) {
        View mview = convertView;
        final LayoutHandler layoutHandler;
        if (mview == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mview = layoutInflater.inflate(R.layout.row_item, parent, false);
            layoutHandler = new LayoutHandler();

            layoutHandler.block = mview.findViewById(R.id.tv_block);
            layoutHandler.lot_num = mview.findViewById(R.id.tv_lot);
            layoutHandler.status = mview.findViewById(R.id.tv_status);
            layoutHandler.owner = mview.findViewById(R.id.tv_name);
            layoutHandler.bdate = mview.findViewById(R.id.tv_bdate);
            layoutHandler.ddate = mview.findViewById(R.id.tv_ddate);
            layoutHandler.contact = mview.findViewById(R.id.tv_contact);


            mview.setTag(layoutHandler);
        } else {
            layoutHandler = (LayoutHandler) mview.getTag();
        }

        final DataItems items = (DataItems) this.getItem(position);


        layoutHandler.block.setText("Block: " + items.getTomb_block());
        layoutHandler.lot_num.setText(" Tomb: " + String.valueOf(items.getTomb_lot_no()));
        layoutHandler.status.setText("LOT " + items.getTomb_stat());
        String name = ifNull(items.getOwner_fname()) + " " + ifNull(items.getOwner_mname()) + " " + ifNull(items.getOwner_lname());
        layoutHandler.owner.setText(name);
        layoutHandler.bdate.setText("Birth Date: " + ifNull(items.getOwner_bdate()));
        layoutHandler.ddate.setText("Death Date: " + ifNull(items.getOwner_ddate()));
        layoutHandler.contact.setText(ifNull(items.getOwner_con_per()));


        return mview;
    }

    public String ifNull(String items) {

        return items != null ? items : "-";
    }
}

