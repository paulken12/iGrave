package com.secure.paulken.igrave;

import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.location.Location;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.secure.paulken.igrave.DBHelper.DeceaseProvider;
import com.secure.paulken.igrave.DBHelper.OwnerProvider;
import com.secure.paulken.igrave.DBHelper.TombProvider;
import com.secure.paulken.igrave.DBHelper.DataSource;
import com.secure.paulken.igrave.Model.DataItems;
import com.secure.paulken.igrave.Model.DeceaseItems;
import com.secure.paulken.igrave.Model.OwnerItems;
import com.secure.paulken.igrave.Model.TombItems;
import com.secure.paulken.igrave.WindowTitle.InfoWindowCustom;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleMap.OnPolygonClickListener,
        GoogleMap.OnPolylineClickListener {

    private GoogleMap mMap;
    DataSource mDataSource;
    LatLng yourHere;
    double ur_lat = 16.375826;
    double ur_lon = 120.609025;

    int mark;

    ArrayList<Marker> markerArrayList = new ArrayList<>();
    ArrayList<Polyline> polylineArrayList = new ArrayList<>();

    Polyline line;
    Marker markers;

    List<TombItems> tombItems = TombProvider.getData();
    List<OwnerItems> ownerItems = OwnerProvider.getData();
    List<DeceaseItems> deceaseItems = DeceaseProvider.getData();
    List<DataItems> dataItems,deads;

    private long thisTime = 0;
    private long prevTime = 0;
    private int tapCount = 0;
    private static final int MAX_TAP_COUNT = 1;
    protected static final long DOUBLE_CLICK_MAX_DELAY = 500;

    private static final int COLOR_BLACK_ARGB = 0xff000000;
    private static final int COLOR_WHITE_ARGB = 0xffffffff;
    private static final int COLOR_GREEN_ARGB = 0xff388E3C;
    private static final int COLOR_PURPLE_ARGB = 0xff81C784;
    private static final int COLOR_ORANGE_ARGB = 0xffF57F17;
    private static final int COLOR_BLUE_ARGB = 0xffF9A825;

    EditText search;
    TextView distance;
    Button searchMe;
    EditText fname,mname,lname;
    Polygon block_6, block_7, block_3, block_4, block_9;

    private static final int POLYLINE_STROKE_WIDTH_PX = 12;
    private static final int POLYGON_STROKE_WIDTH_PX = 8;
    private static final int PATTERN_DASH_LENGTH_PX = 20;
    private static final int PATTERN_GAP_LENGTH_PX = 5;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);

    // Create a stroke pattern of a gap followed by a dot.
    private static final List<PatternItem> PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT);

    // Create a stroke pattern of a gap followed by a dash.
    private static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);

    // Create a stroke pattern of a dot followed by a gap, a dash, and another gap.
    private static final List<PatternItem> PATTERN_POLYGON_BETA =
            Arrays.asList(DOT, GAP, DASH, GAP);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        mDataSource = new DataSource(this);
        mDataSource.open();

        long numItems = mDataSource.getItemCount();


        if (numItems == 0) {
            for (TombItems tomb : tombItems) {
                try {
                    mDataSource.insertTomb(tomb);
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }
            }
            for (OwnerItems decease : ownerItems) {
                try {
                    mDataSource.insertOwner(decease);
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }
            }
            for (DeceaseItems decease : deceaseItems) {
                try {
                    mDataSource.insertDecease(decease);
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }
            }
        }



        search = findViewById(R.id.search);
        fname = findViewById(R.id.full_name);
        searchMe = findViewById(R.id.button1);
        distance = findViewById(R.id.distance);

        fname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0)
                {
                    search.setEnabled(true);
                    search.setHint("Search Lot");
                }
                else {
                    search.setEnabled(false);
                    search.setHint("Disabled");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
                    fname.setEnabled(true);
                    fname.setHint("Search Name");
                }
                else {
                    fname.setEnabled(false);
                    fname.setHint("Disabled");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {

        final LatLng one = new LatLng(16.375848, 120.609034);
        final LatLng two = new LatLng(16.375950, 120.609051);
        final LatLng three = new LatLng(16.376561, 120.608861);
        final LatLng four = new LatLng(16.376641, 120.608517);

        mMap = googleMap;
//        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMinZoomPreference(19);

        mMap.setInfoWindowAdapter(new InfoWindowCustom(this));

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.night));

            if (!success) {
                Log.e("MapsActivityRaw", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivityRaw", "Can't find style.", e);
        }


        MarkerOptions block6 = new MarkerOptions()
                .title("Block 6")
                .position(new LatLng(16.375772, 120.608836))
                .icon(BitmapDescriptorFactory.defaultMarker());

        MarkerOptions block7 = new MarkerOptions()
                .title("Block 7")
                .position(new LatLng(16.375990, 120.608550))
                .icon(BitmapDescriptorFactory.defaultMarker());

        MarkerOptions block4 = new MarkerOptions()
                .title("Block 4")
                .position(new LatLng(16.376826, 120.608690))
                .icon(BitmapDescriptorFactory.defaultMarker());

        final MarkerOptions block3 = new MarkerOptions()
                .title("Block 3")
                .position(new LatLng(16.376899, 120.608255))
                .icon(BitmapDescriptorFactory.defaultMarker());

        MarkerOptions block9 = new MarkerOptions()
                .title("Block 9")
                .position(new LatLng(16.376569, 120.608274))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.blk9));

        mMap.addMarker(block6);
        mMap.addMarker(block7);
        mMap.addMarker(block4);
        mMap.addMarker(block3);
        mMap.addMarker(block9);


        block_6 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(16.375961, 120.609022),
                        new LatLng(16.376560, 120.608829),
                        new LatLng(16.376575, 120.608690),
                        new LatLng(16.376367, 120.608699),
                        new LatLng(16.376009, 120.608659),
                        new LatLng(16.375691, 120.608583),
                        new LatLng(16.375405, 120.608584),
                        new LatLng(16.375159, 120.608709),
                        new LatLng(16.375141, 120.608821),
                        new LatLng(16.375201, 120.608831),
                        new LatLng(16.375209, 120.609322),
                        new LatLng(16.375451, 120.609162),
                        new LatLng(16.375749, 120.609070),
                        new LatLng(16.375800, 120.609015),
                        new LatLng(16.375883, 120.609011),
                        new LatLng(16.375967, 120.609023)
                ));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        block_6.setTag("alpha");
        // Style the polygon.
        stylePolygon(block_6);

        block_7 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(16.376467, 120.608671),
                        new LatLng(16.376457, 120.608539),
                        new LatLng(16.376231, 120.608541),
                        new LatLng(16.375988, 120.608499),
                        new LatLng(16.375743, 120.608390),
                        new LatLng(16.375707, 120.608264),
                        new LatLng(16.375409, 120.608263),
                        new LatLng(16.375373, 120.608457),
                        new LatLng(16.375235, 120.608510),
                        new LatLng(16.375199, 120.608553),
                        new LatLng(16.375227, 120.608638),
                        new LatLng(16.375508, 120.608545),
                        new LatLng(16.375818, 120.608586),
                        new LatLng(16.376177, 120.608662)

                ));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        block_7.setTag("alpha");
        // Style the polygon.
        stylePolygon(block_7);

        block_4 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(16.376663, 120.608882),
                        new LatLng(16.376893, 120.608827),
                        new LatLng(16.376973, 120.608569),
                        new LatLng(16.376764, 120.608553),
                        new LatLng(16.376726, 120.608757),
                        new LatLng(16.376673, 120.608752)
                ));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        block_4.setTag("alpha");
        // Style the polygon.
        stylePolygon(block_4);

        block_3 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(16.376690, 120.608500),
                        new LatLng(16.376972, 120.608541),
                        new LatLng(16.377068, 120.608274),
                        new LatLng(16.376993, 120.608047),
                        new LatLng(16.376760, 120.608055)
                ));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        block_3.setTag("alpha");
        // Style the polygon.
        stylePolygon(block_3);

        block_9 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(16.376666, 120.608479),
                        new LatLng(16.376324, 120.608480),
                        new LatLng(16.376383, 120.608054),
                        new LatLng(16.376736, 120.608088)
                ));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        block_9.setTag("alpha");
        // Style the polygon.
        stylePolygon(block_9);

        List<DataItems> items = mDataSource.getAllItems();

        for(DataItems dataItems: items){
            markers = mMap.addMarker(marker(String.valueOf(dataItems.getDecease_fname())+" "+String.valueOf(dataItems.getDecease_lname())
                    ,dataItems.getDecease_bdate()+"-"+dataItems.getDecease_ddate()
                    , dataItems.getTomb_lat(), dataItems.getTomb_long(),dataItems.getTomb_stat()));
        }

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(16.376066, 120.608751))
                .zoom(15)
                .bearing(250)
                .tilt(0)
                .build();

        yourHere = new LatLng(ur_lat, ur_lon);
        googleMap.addMarker(new MarkerOptions().position(yourHere).title("Your Here"));
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);



        searchMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nam = fname.getText().toString();
                List<DataItems> list = mDataSource.getAll();

                for (DataItems dataItems : list) {

                    int lot_num = dataItems.getTomb_lot_no();

                    if(!nam.equals(""))
                    {
                        List<DataItems> deceased  = mDataSource.searchDeceasedInLot(nam);

                        for(DataItems decease : deceased){
                            block(block_9);
                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(new LatLng(16.376621, 120.608347))
                                    .zoom(20)
                                    .bearing(250)
                                    .tilt(0)
                                    .build();
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                            markers = mMap.addMarker(marker(String.valueOf(decease.getDecease_fname())+" "+String.valueOf(decease.getDecease_lname())
                                    ,decease.getDecease_bdate()+" to "+decease.getDecease_ddate()
                                    , decease.getTomb_lat(), decease.getTomb_long(),decease.getTomb_stat()));
                            markerArrayList.add(markers);

                            MarkerOptions a = new MarkerOptions()
                                    .title("Pathway")
                                    .position(new LatLng(16.376665, 120.608495))
                                    .icon(BitmapDescriptorFactory.defaultMarker());
                            markers = mMap.addMarker(a);
                            markerArrayList.add(markers);

                            MarkerOptions mark = new MarkerOptions()
                                    .title(decease.getOwner_fname() + "," + decease.getOwner_fname())
                                    .position(new LatLng(decease.getTomb_lat(), decease.getTomb_long()))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                            markers = mMap.addMarker(mark);
                            markerArrayList.add(markers);

                            line = mMap.addPolyline(new PolylineOptions()
                                    .add(one, two, three, four, new LatLng(decease.getTomb_lat(), decease.getTomb_long()))
                                    .width(4)
                                    .pattern(PATTERN_POLYLINE_DOTTED)
                                    .color(Color.RED));

                            polylineArrayList.add(line);

                            distance.setText(CalculationByDistance(new LatLng(decease.getTomb_lat(), decease.getTomb_long()), new LatLng(16.376665, 120.608495)));

                        }
                    }
                    else
                    {
                        int lot_number = Integer.parseInt(search.getText().toString());

                        if (lot_number >= 1 & lot_number <= 32 ) {
                            block(block_9);
                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(new LatLng(16.376621, 120.608347))
                                    .zoom(20)
                                    .bearing(250)
                                    .tilt(0)
                                    .build();
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                            markers = mMap.addMarker(marker(String.valueOf(dataItems.getDecease_fname())+" "+String.valueOf(dataItems.getDecease_lname())
                                    ,dataItems.getDecease_bdate()+" to "+dataItems.getDecease_ddate()
                                    , dataItems.getTomb_lat(), dataItems.getTomb_long(),dataItems.getTomb_stat()));
                            MarkerOptions a = new MarkerOptions()
                                    .title("Pathway")
                                    .position(four)
                                    .icon(BitmapDescriptorFactory.defaultMarker());
                            markers = mMap.addMarker(a);

                            if (lot_number == lot_num) {

                                removeMarker();
                                removeLine();
                                MarkerOptions mark = new MarkerOptions()
                                        .title(dataItems.getOwner_fname()+","+dataItems.getOwner_fname())
                                        .position(new LatLng(dataItems.getTomb_lat(), dataItems.getTomb_long()))
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                                markers = mMap.addMarker(mark);

                                line = mMap.addPolyline(new PolylineOptions()
                                    .add(one,two,three,four,new LatLng(dataItems.getTomb_lat(), dataItems.getTomb_long()))
                                    .width(4)
                                    .pattern(PATTERN_POLYLINE_DOTTED)
                                    .color(Color.YELLOW));
                                polylineArrayList.add(line);

                                distance.setText(CalculationByDistance(new LatLng(dataItems.getTomb_lat(), dataItems.getTomb_long()), new LatLng(16.376665, 120.608495)));
                            }
                        } else {

                            Toast.makeText(MapsActivity.this, "You enter invalid information", Toast.LENGTH_SHORT).show();
                        }
                    }

                }



                search.setText("");

            }
        });

    }

    private void removeMarker() {
        if (markers != null) {
            markers.remove();
        }
    }

    private void removeLine() {
        if (line != null) {
            line.remove();
        }
    }

    private void removeFill() {
        block_3.setTag("beta");
        block_4.setTag("beta");
        block_6.setTag("beta");
        block_7.setTag("beta");
        block_9.setTag("beta");

        stylePolygon(block_3);
        stylePolygon(block_4);
        stylePolygon(block_6);
        stylePolygon(block_7);
        stylePolygon(block_9);
    }

    public String ifNull(String items) {

        return items != null ? items : "-";
    }

    public MarkerOptions marker(String title,String snippet, double lat, double lon, String stat) {

        if(stat.equalsIgnoreCase("occupied")){
            mark = R.drawable.m1;
        }
        if(stat.equalsIgnoreCase("reserved")){
            mark = R.drawable.m2;
        }
        if(stat.equalsIgnoreCase("empty")){
            mark = R.drawable.m3;
        }
        MarkerOptions a = new MarkerOptions()
                .title(ifNull(title))
                .snippet(ifNull(snippet))
                .position(new LatLng(lat, lon))
                .icon(BitmapDescriptorFactory.fromResource(mark));

        return a;
    }

    public MarkerOptions m(LatLng latLng) {


        MarkerOptions a = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.aa));

        return a;
    }


    private void stylePolyline(Polyline polyline) {
        String type = "";
        // Get the data object stored with the polyline.
        if (polyline.getTag() != null) {
            type = polyline.getTag().toString();
        }

        switch (type) {
            // If no type is given, allow the API to use the default.
            case "A":
                // Use a custom bitmap as the cap at the start of the line.
                polyline.setStartCap(
                        new CustomCap(
                                BitmapDescriptorFactory.fromResource(R.drawable.ic_arrow), 10));
                break;
            case "B":
                // Use a round cap at the start of the line.
                polyline.setStartCap(new RoundCap());
                break;
        }

        polyline.setEndCap(new RoundCap());
        polyline.setWidth(POLYLINE_STROKE_WIDTH_PX);
        polyline.setColor(COLOR_BLACK_ARGB);
        polyline.setJointType(JointType.ROUND);
    }

    /**
     * Styles the polygon, based on type.
     *
     * @param polygon The polygon object that needs styling.
     */
    private void stylePolygon(Polygon polygon) {
        String type = "";
        // Get the data object stored with the polygon.
        if (polygon.getTag() != null) {
            type = polygon.getTag().toString();
        }

        List<PatternItem> pattern = null;
        int strokeColor = COLOR_BLACK_ARGB;
        int fillColor = COLOR_WHITE_ARGB;

        switch (type) {
            // If no type is given, allow the API to use the default.
            case "alpha":
                // Apply a stroke pattern to render a dashed line, and define colors.
                pattern = PATTERN_POLYGON_ALPHA;
                strokeColor = COLOR_GREEN_ARGB;
                fillColor = COLOR_PURPLE_ARGB;
                break;
            case "beta":
                // Apply a stroke pattern to render a line of dots and dashes, and define colors.
                pattern = PATTERN_POLYGON_BETA;
                strokeColor = COLOR_ORANGE_ARGB;
                fillColor = COLOR_BLUE_ARGB;
                break;
        }

        polygon.setStrokePattern(pattern);
        polygon.setStrokeWidth(POLYGON_STROKE_WIDTH_PX);
        polygon.setStrokeColor(strokeColor);
        polygon.setStrokeWidth(1);
        polygon.setFillColor(0x7F00FF00);
    }

    /**
     * Listens for clicks on a polyline.
     *
     * @param polyline The polyline object that the user has clicked.
     */
    @Override
    public void onPolylineClick(Polyline polyline) {
        // Flip from solid stroke to dotted stroke pattern.
        if ((polyline.getPattern() == null) || (!polyline.getPattern().contains(DOT))) {
            polyline.setPattern(PATTERN_POLYLINE_DOTTED);
        } else {
            // The default pattern is a solid stroke.
            polyline.setPattern(null);
        }

    }


    @Override
    public void onPolygonClick(Polygon polygon) {
        // Flip the values of the red, green, and blue components of the polygon's color.
        int color = polygon.getStrokeColor() ^ 0x00ffffff;
        polygon.setStrokeColor(color);
        color = polygon.getFillColor() ^ 0x00ffffff;
        polygon.setFillColor(color);
    }


    public void block(Polygon polygon) {

        int color = polygon.getStrokeColor() ^ 0x00ffffff;
        polygon.setStrokeColor(color);
        color = polygon.getFillColor() ^ 0x00ffffff;
        polygon.setFillColor(color);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDataSource.close();
    }

    public void TapEvent(View view) {
        if (SystemClock.uptimeMillis() > thisTime) {
            if ((SystemClock.uptimeMillis() - thisTime) > DOUBLE_CLICK_MAX_DELAY * MAX_TAP_COUNT) {

                tapCount = 0;
            }
            if (tapCount()) {
                Intent list = new Intent(MapsActivity.this, MainActivity.class);
                startActivity(list);
            }
        }
    }

    public PolylineOptions drawLine(MarkerOptions marker1, MarkerOptions marker2) {

        return new PolylineOptions()
                .add(marker1.getPosition())
                .add(marker2.getPosition())
                .width(2)
                .color(Color.RED);
    }

    public PolylineOptions showLine(MarkerOptions marker1, MarkerOptions marker2,MarkerOptions marker3) {

        return new PolylineOptions()
                .add(marker1.getPosition())
                .add(marker2.getPosition())
                .add(marker3.getPosition())
                .width(4)
                .color(Color.RED);
    }

    private Boolean tapCount() {

        if (tapCount == 0) {
            thisTime = SystemClock.uptimeMillis();
            tapCount++;
        } else if (tapCount < (MAX_TAP_COUNT - 1)) {
            tapCount++;
        } else {
            prevTime = thisTime;
            thisTime = SystemClock.uptimeMillis();

            //just incase system clock reset to zero
            if (thisTime > prevTime) {

                //Check if times are within our max delay
                if ((thisTime - prevTime) <= DOUBLE_CLICK_MAX_DELAY * MAX_TAP_COUNT) {
                    //We have detected a multiple continuous tap!
                    //Once receive multiple tap, reset tap count to zero for consider next tap as new start
                    tapCount = 0;
                    return true;
                } else {
                    //Otherwise Reset tapCount
                    tapCount = 0;
                }
            } else {
                tapCount = 0;
            }
        }
        return false;

    }

    public String CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));

        int m = 1000;

        double meters = km*m;
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));

        DecimalFormat form = new DecimalFormat("####");
        int test = Integer.valueOf(form.format(meters));



        Log.i("Radius Value", "" + valueResult + "   KM  " + meter
                + " Meter   " );

        return test + " m";
    }

    public void clear_all(View view) {

        distance.setText("0 m");

        for (Marker marker : markerArrayList) {
            marker.remove();
        }
        markerArrayList.clear();

        for (Polyline polyline : polylineArrayList) {
            polyline.remove();
        }
        polylineArrayList.clear();
    }
}