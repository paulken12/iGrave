package com.secure.paulken.igrave.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.secure.paulken.igrave.Model.DataItems;
import com.secure.paulken.igrave.R;

import java.util.List;


public class OwnerAdapter extends ArrayAdapter {
    private final List<DataItems> mlist;

    public OwnerAdapter(Context context, List<DataItems> list) {
        super(context, R.layout.owner_item);
        mlist = list;
    }

    static class LayoutHandler {
        TextView block, lot_num, name,status, deceased, address, contact;
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
            mview = layoutInflater.inflate(R.layout.owner_item, parent, false);
            layoutHandler = new LayoutHandler();

            layoutHandler.block = mview.findViewById(R.id.fam_block);
            layoutHandler.lot_num = mview.findViewById(R.id.fam_lot);
            layoutHandler.name = mview.findViewById(R.id.fam_name);
            layoutHandler.status = mview.findViewById(R.id.fam_status);
            layoutHandler.deceased = mview.findViewById(R.id.fam_deceased);
            layoutHandler.address = mview.findViewById(R.id.fam_address);
            layoutHandler.contact = mview.findViewById(R.id.fam_contact);


            mview.setTag(layoutHandler);
        } else {
            layoutHandler = (LayoutHandler) mview.getTag();
        }

        final DataItems items = (DataItems) this.getItem(position);


        layoutHandler.block.setText("Block: " + items.getTomb_block());
        layoutHandler.lot_num.setText(" Tomb: " + String.valueOf(items.getTomb_lot_no()));
        layoutHandler.status.setText("LOT " + items.getTomb_stat());
        String name = ifNull(items.getOwner_fname()) + " " + ifNull(items.getOwner_mname()) + " " + ifNull(items.getOwner_lname());
        String decease = ifNull(items.getDecease_fname()) + " " + ifNull(items.getDecease_mname()) + " " + ifNull(items.getDecease_lname());
        layoutHandler.name.setText(name);
        layoutHandler.deceased.setText("Family/Relative : " + decease);
        layoutHandler.address.setText("Address : " + ifNull(items.getOwner_address()));
        layoutHandler.contact.setText(ifNull(items.getOwner_con_per()));


        return mview;
    }

    public String ifNull(String items) {

        return items != null ? items : "-";
    }
}

