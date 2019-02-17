package com.example.johnnybahama.mapapp;

import android.animation.FloatArrayEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.icu.text.IDNA;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.location.LocationManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;


import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;


import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
//import com.google.android.maps.GeoPoint;
//import com.google.android.maps.ItemizedOverlay;
//import com.google.android.maps.MapActivity;
//import com.google.android.maps.MapView;
//import com.google.android.maps.MyLocationOverlay;
//import com.google.android.maps.OverlayItem;

public class MapsView extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private Marker ass;
    private boolean droppingPin = false;
    private LatLng tempPin;
    private double currentLat;
    private double currentLng;
    private String userEmail;
    private LatLng editablePin;
    private SharedPreferences currentPrefs;
    private Date currentDate = Calendar.getInstance().getTime();
    private ArrayList<Pin> modelPins = new ArrayList<Pin>();
    private ArrayList<Marker> modelMarkers = new ArrayList<Marker>();
    public int testInt = 50;
    private Pin Placeholder = new Pin(currentDate, "a", "a", 40.2, -76.2, "civil", "rayan", 0,"a");
    private String viewSort = "All";
    private LatLng currentLatLng;
    private Marker placeHolderMarker;
    private LatLng currentLatLngOnScreen;
    private propertyValue markerProperty;
    private RelativeLayout mainToolbar;

    final private int mapRadius = 2500;
    private CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(this);





    private GoogleMap.InfoWindowAdapter DefaultInfoWindoGoogleMap = new GoogleMap.InfoWindowAdapter() {
        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }
        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    };

    Spinner viewSorts;
    DatabaseReference databasePins;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        markerProperty = new propertyValue("a");

        overridePendingTransition(17432576,17432576 );












        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_view);
        final Button newPin = (Button) findViewById(R.id.NewPin);
        final Button otherButton = (Button) findViewById(R.id.otherButton);

        viewSorts = (Spinner) findViewById(R.id.ViewSorts);
        databasePins = FirebaseDatabase.getInstance().getReference("Pins");
        currentPrefs = getSharedPreferences("prefID", Context.MODE_PRIVATE);
        mainToolbar = (RelativeLayout) findViewById(R.id.mainToolbar) ;
        SharedPreferences.Editor currenPrefEditor = currentPrefs.edit();




        Intent retrieveLatLng= getIntent();
        if(retrieveLatLng.getExtras() != null){
            currentLat = Double.parseDouble(retrieveLatLng.getExtras().getString("Lat"));
            currentLng = Double.parseDouble(retrieveLatLng.getExtras().getString("Lng"));
            userEmail = retrieveLatLng.getExtras().getString("userEmail");

            currenPrefEditor.putString("currentLat", String.valueOf(currentLat));
            currenPrefEditor.putString("currentLng", String.valueOf(currentLng));
            currenPrefEditor.apply();
            currentLatLng = new LatLng(currentLat, currentLng);
           // currenPrefEditor.commit();
        }
        else{
            currentLat = Double.parseDouble(currentPrefs.getString("currentLat","NO LAT"));
            currentLng = Double.parseDouble(currentPrefs.getString("currentLng","NO LNG"));
            userEmail = currentPrefs.getString("userEmail", "NO EMAIL");
            Toast notLongError = Toast.makeText(getApplicationContext(), "Loaded Previous location", Toast.LENGTH_SHORT);
            notLongError.show();
            currentLatLng = new LatLng(currentLat, currentLng);
        }


        List<String> categories = new ArrayList<String>();
        categories.add("All");
        categories.add("General");
        categories.add("Activity");
        categories.add("Advertisement");
        categories.add("Civil");

        viewSorts.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                // TODO Auto-generated method stub
                viewSort = String.valueOf(viewSorts.getSelectedItem());

                loadPins(mMap);
             //   placeMarker(mMap,"-LVaL8wrd1npVMyMF1Zm");
            //    placeAllMarkers(mMap);


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });

        ArrayAdapter<String> spin_adapter = new ArrayAdapter<String>(MapsView.this, android.R.layout.simple_spinner_item, categories);
        viewSorts.setAdapter(spin_adapter);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        newPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentLatLngOnScreen = mMap.getProjection().fromScreenLocation(new Point(540, 920));

                if (droppingPin == false) {
                    if (calculateDistance(currentLatLngOnScreen.latitude, currentLatLngOnScreen.longitude) < mapRadius) {
                        newPin.setText("Remove");
                        mMap.setInfoWindowAdapter(DefaultInfoWindoGoogleMap);

                        placeHolderMarker = mMap.addMarker(new MarkerOptions().position(currentLatLngOnScreen).title("Click Here to add details").draggable(true).icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("temppin", 200, 200))));

                        dropPinEffect(placeHolderMarker);

                        droppingPin = true;
                    }
                    else{
                        Toast notLongError = Toast.makeText(getApplicationContext(), "Not Within Your Region", Toast.LENGTH_SHORT);
                        notLongError.show();
                    }
                }

                else if(droppingPin == true){
                    placeHolderMarker.remove();
                    mMap.setInfoWindowAdapter(customInfoWindow);


                    //placeHolderMarker.remove();
                    droppingPin = false;
                    newPin.setText("Newpin");

                }


            }
        });


        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                Date weewee = new Date();
                weewee.setHours(11);
                String test = String.valueOf(Calendar.getInstance().getTime().getMinutes());
                Toast notLongError = Toast.makeText(getApplicationContext(), test, Toast.LENGTH_SHORT);
                notLongError.show();











            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();

        if(isNetworkConnected() == false) {
            Toast notLongError = Toast.makeText(getApplicationContext(), "Please Reconect wifi", Toast.LENGTH_LONG);
            notLongError.show();
        }



//        Toast notLongError = Toast.makeText(getApplicationContext(), "Just Resumed", Toast.LENGTH_SHORT);
//        notLongError.show();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        Circle circle = mMap.addCircle(new CircleOptions()
//                .center(new LatLng(currentLat, currentLng))
//                .radius(10000)
//                .strokeWidth(200.0f)
//                .strokeColor(Color.RED)
//                .fillColor(Color.RED));














        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setOnInfoWindowClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));



        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

                marker.setAlpha(0.5f);

            }

            @Override
            public void onMarkerDrag(Marker marker) {

                marker.setAlpha(0.5f);

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

                if (calculateDistance(placeHolderMarker.getPosition().latitude, placeHolderMarker.getPosition().longitude) < mapRadius) {
                    marker.setAlpha(1.0f);
                  //  editablePin = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
                    Toast notLongError = Toast.makeText(getApplicationContext(), "Pin Dragged", Toast.LENGTH_SHORT);
                    notLongError.show();
                }
                else{
                    marker.setPosition(new LatLng(currentLat,currentLng));
                    marker.setAlpha(1.0f);
                 //   editablePin = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);

                    Toast notLongError = Toast.makeText(getApplicationContext(), "Out of region", Toast.LENGTH_SHORT);
                    notLongError.show();

                }




            }
        });

    }






    @Override
    public void onInfoWindowClick(Marker marker) {
        editablePin = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
        Intent goToPinView = new Intent(MapsView.this, PinView.class);
        goToPinView.putExtra("Title", marker.getTitle());
        goToPinView.putExtra("Body", marker.getSnippet());

        //goToPinView.putExtra("pinLatLng", editablePin);







        if (droppingPin == true){



            Intent goToNewPin = new Intent(MapsView.this, NewPin.class);
            goToNewPin.putExtra("Lat", String.valueOf(editablePin.latitude));
            goToNewPin.putExtra("Lng", String.valueOf(editablePin.longitude));
            goToNewPin.putExtra("userEmail", userEmail);
            startActivity(goToNewPin);
            droppingPin = false;

        }
        else{

            startActivity(goToPinView);




        }


    }


    public void upDateDataBase(){

    }



    public void loadPins (final GoogleMap gmap){
        gmap.clear();
        mMap.setInfoWindowAdapter(customInfoWindow);
        mMap.addCircle(new CircleOptions().center(new LatLng(currentLat, currentLng))
                .radius(mapRadius)
                .strokeWidth(100.0f)
                .fillColor(0x220000FF)
                .strokeColor(Color.GRAY));

        FirebaseDatabase.getInstance().getReference().child("Pins")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            LatLng MLatLng = new LatLng(Double.parseDouble(snapshot.child("lat").getValue().toString()), Double.parseDouble(snapshot.child("lng").getValue().toString()));
                            String MTitle = snapshot.child("body").getValue().toString();
                            String MBody = snapshot.child("title").getValue().toString();
                            String MPostType = snapshot.child("postType").getValue().toString();
                            String MPoster = snapshot.child("originalPoster").getValue().toString();
                            Date MDate = new Date();
                            MDate.setHours(Integer.valueOf(snapshot.child("date").child("hours").getValue().toString()));
//                            MDate.setDateInteger.valueOf(snapshot.child("date").child("hours").getValue().toString()));
//                            MDate.setHours(Integer.valueOf(snapshot.child("date").child("hours").getValue().toString()));
                            int MLikes = Integer.parseInt(snapshot.child("likes").getValue().toString());
                            String MID = snapshot.getKey();
                          //  String MDate = snapshot.child("date").getValue().toString();
                         //   int MWidth = generateWidth(new dateClass("32012019"),new dateClass(MDate), MLikes, "likes");

                       //     int daysSince = new dateClass(MDate).getOldness(new dateClass("32012019"));
                            Pin dummyPin = new Pin(MDate, MBody, MTitle, MLatLng.latitude, MLatLng.longitude, MPostType, MPoster, MLikes, MID);
                            if(MPostType.equals(viewSort) || viewSort.equals("All") ) {

                                Marker placeHolderPin = gmap.addMarker(new MarkerOptions().position(MLatLng).title(MTitle).snippet(MBody).icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(MPostType,200, 200))));
                                modelMarkers.add(placeHolderPin);
                                if(calculateDistance(MLatLng.latitude,MLatLng.longitude) > mapRadius){
                                    placeHolderPin.setAlpha(0.4f);
                                }
                                dropPinEffect(placeHolderPin);
                                placeHolderPin.setTag(dummyPin);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
         }





//    public void onMarkerDragEnd (Marker marker){
//
//
//
//        editablePin = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
//        Toast notLongError = Toast.makeText(getApplicationContext(), "Pin Dragged", Toast.LENGTH_SHORT);
//        notLongError.show();
//
//    }

    public Bitmap resizeMapIcons(String iconName,int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    private void dropPinEffect(final Marker marker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = Math.max(
                        1 - interpolator.getInterpolation((float) elapsed
                                / duration), 0);
                marker.setAnchor(0.5f, 1.0f + 14 * t);

                if (t > 0.0) {
                    // Post again 15ms later.
                    handler.postDelayed(this, 15);
                } else {
                    //marker.showInfoWindow();

                }
            }
        });
    }

    public void changeStringProperty(String property, String  newProperty, String ID){
        for(Marker m : modelMarkers){
            Pin markerInfo = (Pin) m.getTag();

//            Toast notLongError = Toast.makeText(getApplicationContext(), String.valueOf(markerInfo.getID().length()), Toast.LENGTH_SHORT);
//            notLongError.show();
            if(markerInfo.getID().equals(ID)){
//                Toast notLongError = Toast.makeText(getApplicationContext(), "Id located", Toast.LENGTH_SHORT);
//                notLongError.show();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Pins").child(ID).child(property);
                ref.setValue(newProperty);
            }

        }
        loadPins(mMap);
    }


    public float calculateDistance(double lat, double lng){

        float distanceFloats[] = new float[] {1};
        Location.distanceBetween(currentLat,currentLng, lat, lng, distanceFloats);
        return distanceFloats[0];


    }

    public boolean isInRegion(double lat, double lng, int radius){
//        if(thisproperty == false)return false

        if(calculateDistance(lat,lng) < radius ){
            return true;
        }
        else{
            return false;
        }

    }


    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }




    public int generateWidth(dateClass currentDate,dateClass postDate, int likes, String sortPref) {

        int ratio = 1;

        if (sortPref == "likes") {

            if (likes < 0) {
                return (100 + likes);
            } else {
                return (100) + (likes * ratio);
            }


        }
        if (sortPref == "date") {

            int daysSince = postDate.getOldness(currentDate);

            if (daysSince == 1)return 230;
            if (daysSince > 1 && daysSince <= 3)return 200;
            if (daysSince > 3 && daysSince <= 6)return 170;
            if (daysSince > 6 && daysSince <= 10)return 130;
            if (daysSince > 10 && daysSince <= 16)return 100;
            if (daysSince > 16 && daysSince <= 26)return 50;
            return 50;


        }

        return 200;

    }

//    public boolean isInRegion(LatLng middleScreen, int radius){
////        if(thisproperty == false)return false
//
//        float distanceFloats[] = new float[] {1};
//        Location.distanceBetween(middleScreen.latitude,middleScreen.latitude, la, lng, distanceFloats);
//
//        if(distanceFloats[0] < radius) {
//            return true;
//        }
//        return false;
//
//    }

}
