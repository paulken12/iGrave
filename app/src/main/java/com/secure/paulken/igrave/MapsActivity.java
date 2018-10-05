package com.secure.paulken.igrave;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.secure.paulken.igrave.DBHelper.DataProvider;
import com.secure.paulken.igrave.DBHelper.DataSource;
import com.secure.paulken.igrave.Model.DataItems;

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

    Polyline line;
    Marker markers;

    List<DataItems> dataItems = DataProvider.getData();

    private long thisTime = 0;
    private long prevTime = 0;
    private int tapCount = 0;
    private static final int MAX_TAP_COUNT = 5;
    protected static final long DOUBLE_CLICK_MAX_DELAY = 500;

    private static final int COLOR_BLACK_ARGB = 0xff000000;
    private static final int COLOR_WHITE_ARGB = 0xffffffff;
    private static final int COLOR_GREEN_ARGB = 0xff388E3C;
    private static final int COLOR_PURPLE_ARGB = 0xff81C784;
    private static final int COLOR_ORANGE_ARGB = 0xffF57F17;
    private static final int COLOR_BLUE_ARGB = 0xffF9A825;

    EditText search;
    Button searchMe;
    Polygon block_6, block_7, block_3, block_4, block_9;

    private static final int POLYLINE_STROKE_WIDTH_PX = 12;
    private static final int POLYGON_STROKE_WIDTH_PX = 8;
    private static final int PATTERN_DASH_LENGTH_PX = 20;
    private static final int PATTERN_GAP_LENGTH_PX = 20;
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
            for (DataItems items : dataItems) {
                try {
                    mDataSource.insertLocation(items);
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }
            }
        }


        search = findViewById(R.id.search);
        searchMe = findViewById(R.id.button1);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {


        mMap = googleMap;
//        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMinZoomPreference(19);


        MarkerOptions block6 = new MarkerOptions()
                .title("Block 6")
                .position(new LatLng(16.375772, 120.608836))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.block_6));

        MarkerOptions block7 = new MarkerOptions()
                .title("Block 7")
                .position(new LatLng(16.375990, 120.608550))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.block_7));

        MarkerOptions block4 = new MarkerOptions()
                .title("Block 4")
                .position(new LatLng(16.376826, 120.608690))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.block_4));

        final MarkerOptions block3 = new MarkerOptions()
                .title("Block 3")
                .position(new LatLng(16.376899, 120.608255))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.block_3));

        MarkerOptions block9 = new MarkerOptions()
                .title("Block 9")
                .position(new LatLng(16.376569, 120.608274))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.block_9));

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
                        new LatLng(16.376324, 120.608452),
                        new LatLng(16.376383, 120.608054),
                        new LatLng(16.376736, 120.608088)
                ));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        block_9.setTag("alpha");
        // Style the polygon.
        stylePolygon(block_9);


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(16.376066, 120.608751))
                .zoom(15)
                .bearing(250)
                .tilt(0)
                .build();

        yourHere = new LatLng(ur_lat, ur_lon);
        googleMap.addMarker(new MarkerOptions().position(yourHere).title("Your Here"));
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//        googleMap.getUiSettings().setScrollGesturesEnabled(false);
//        googleMap.getUiSettings().setCompassEnabled(false);
//        googleMap.getUiSettings().setRotateGesturesEnabled(false);
//        googleMap.getUiSettings().setZoomGesturesEnabled(false);



        /*------------------------ FUNCTIONS MAP

        show heavens garden
        search lot
        zoom searched block
        show location
        show red navigation


        ----------------------------*/


        searchMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = Integer.parseInt(search.getText().toString());
                List<DataItems> list = mDataSource.getItems();

                removeFill();
                removeLine();
                removeMarker();

                for (DataItems dataItems : list) {

                    int lot_num = dataItems.getLot_num();

                    if (number >= 1 & number <= 5) {


                        block(block_3);
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(16.376899, 120.608255))
                                .zoom(20)
                                .bearing(250)
                                .tilt(0)
                                .build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                        if (number == lot_num) {
                            markers = mMap.addMarker(marker(dataItems.getBlock_name(), dataItems.getLatitude(), dataItems.getLongitude()));
                            line = mMap.addPolyline(drawLine(marker("",dataItems.getLatitude(),dataItems.getLongitude()),marker("",ur_lat,ur_lon)));
                        }


                    }
                    else if (number >= 6 & number <= 19) {


                        block(block_4);
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(16.376826, 120.608690))
                                .zoom(20)
                                .bearing(250)
                                .tilt(0)
                                .build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                        if (number == lot_num) {
                            markers = mMap.addMarker(marker(dataItems.getBlock_name(), dataItems.getLatitude(), dataItems.getLongitude()));
                            line = mMap.addPolyline(drawLine(marker("",dataItems.getLatitude(),dataItems.getLongitude()),marker("",ur_lat,ur_lon)));

                        }


                    }
                    else if (number >= 20 & number <= 28) {

                        block(block_6);
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(16.375772, 120.608836))
                                .zoom(20)
                                .bearing(250)
                                .tilt(0)
                                .build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                        if (number == lot_num) {
                            markers = mMap.addMarker(marker(dataItems.getBlock_name(), dataItems.getLatitude(), dataItems.getLongitude()));
                            line = mMap.addPolyline(drawLine(marker("",dataItems.getLatitude(),dataItems.getLongitude()),marker("",ur_lat,ur_lon)));

                        }


                    }
                    else if (number >= 29 & number <= 31) {

                        block(block_7);
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(16.375990, 120.608550))
                                .zoom(20)
                                .bearing(250)
                                .tilt(0)
                                .build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                        if (number == lot_num) {
                            markers = mMap.addMarker(marker(dataItems.getBlock_name(), dataItems.getLatitude(), dataItems.getLongitude()));
                            line = mMap.addPolyline(drawLine(marker("",dataItems.getLatitude(),dataItems.getLongitude()),marker("",ur_lat,ur_lon)));

                        }


                    }
                    else {
                        removeLine();
                        removeFill();
                        Toast.makeText(MapsActivity.this, "You enter invalid no. (1 to 31 only for beta testing) ", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        // Set listeners for click events.
//        googleMap.setOnPolylineClickListener(this);
//        googleMap.setOnPolygonClickListener(this);

    }

    private void removeMarker(){
        if(markers != null)
        {
            markers.remove();
        }
    }

    private void removeLine(){
        if(line != null)
        {
            line.remove();
        }
    }

    private void removeFill(){
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

    public MarkerOptions marker(String title, double lat, double lon) {
        MarkerOptions a = new MarkerOptions()
                .title(title)
                .position(new LatLng(lat, lon))
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
        polygon.setFillColor(fillColor);
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

        Toast.makeText(this, "Route type " + polyline.getTag().toString(),
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPolygonClick(Polygon polygon) {
        // Flip the values of the red, green, and blue components of the polygon's color.
        int color = polygon.getStrokeColor() ^ 0x00ffffff;
        polygon.setStrokeColor(color);
        color = polygon.getFillColor() ^ 0x00ffffff;
        polygon.setFillColor(color);

        Toast.makeText(this, "Area type " + polygon.getTag().toString(), Toast.LENGTH_SHORT).show();
    }


    public void block(Polygon polygon) {

        int color = polygon.getStrokeColor() ^ 0x00ffffff;
        polygon.setStrokeColor(color);
        color = polygon.getFillColor() ^ 0x00ffffff;
        polygon.setFillColor(color);

        Toast.makeText(this, "Area type " + polygon.getTag().toString(), Toast.LENGTH_SHORT).show();

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
                Intent setting = new Intent(MapsActivity.this,SettingActivity.class);
                startActivity(setting);
            }
        }
    }

    public PolylineOptions drawLine(MarkerOptions marker1,MarkerOptions marker2){

        return new PolylineOptions()
                .add(marker1.getPosition())
                .add(marker2.getPosition())
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
}