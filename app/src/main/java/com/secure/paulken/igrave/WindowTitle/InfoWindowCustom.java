package com.secure.paulken.igrave.WindowTitle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.secure.paulken.igrave.R;

public class InfoWindowCustom implements GoogleMap.InfoWindowAdapter {

    Context context;
    LayoutInflater inflater;
    public InfoWindowCustom(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // R.layout.echo_info_window is a layout in my
        // res/layout folder. You can provide your own
        View v = inflater.inflate(R.layout.echo_info_window, null);

        TextView title = v.findViewById(R.id.info_name);
        TextView bdate =  v.findViewById(R.id.info_birth_date);
        title.setText(ifNull(marker.getTitle()));
        bdate.setText(ifNull(marker.getSnippet()));
        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {
        if (marker != null
                && marker.isInfoWindowShown()) {
            marker.hideInfoWindow();
            marker.showInfoWindow();
        }
        return null;
    }

    public String ifNull(String items) {

        return items != null ? items : "-";
    }
}
