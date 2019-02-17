package com.example.johnnybahama.mapapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Templates;

public class LocationActivity extends AppCompatActivity {



    ArrayList<String> permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();
    Button toMapActivity;
    private double latitude;
    private double longitude;
    LocationManager lm;
    Location myLocation;
    EditText toSearchMapActivity;
    TextView ass;
    private String userEmail = "Empty";





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent retrieveEmail= getIntent();
        userEmail = retrieveEmail.getStringExtra("userEmail");
        Toast notLongError = Toast.makeText(getApplicationContext(), userEmail, Toast.LENGTH_SHORT);
                           notLongError.show();

        overridePendingTransition(17432576,17432576 );


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location2);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            // Permission already Granted
            //Do your work here
            //Perform operations here only which requires permission
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

       // LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //final Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);



        toMapActivity = findViewById(R.id.currentLocation);
        ass = findViewById(R.id.Or);


        toMapActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myLocation = getLastKnownLocation();
                longitude = myLocation.getLongitude();
                latitude =  myLocation.getLatitude();
                Intent toMyMap = new Intent(LocationActivity.this, MapsView.class);

                toMyMap.putExtra("Lat", String.valueOf(latitude));
                toMyMap.putExtra("Lng", String.valueOf(longitude));
                toMyMap.putExtra("userEmail", userEmail);
                startActivity(toMyMap);


            }
        });


        ass.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                Toast notLongError = Toast.makeText(getApplicationContext(), String.valueOf(latitude), Toast.LENGTH_SHORT);
                notLongError.show();


            }
        });

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                longitude = place.getLatLng().longitude;
                latitude =  place.getLatLng().latitude;
                Intent toMyMap = new Intent(LocationActivity.this, MapsView.class);
                toMyMap.putExtra("Lat", String.valueOf(latitude));
                toMyMap.putExtra("Lng", String.valueOf(longitude));
                toMyMap.putExtra("userEmail", userEmail);
                startActivity(toMyMap);

            }

            @Override
            public void onError(Status status) {

            }
        });

    }

    private Location getLastKnownLocation() {


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            // Permission already Granted
            //Do your work here
            //Perform operations here only which requires permission
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }



        lm = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = lm.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }






}