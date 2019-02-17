package com.example.johnnybahama.mapapp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.text.Editable;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import java.util.Calendar;


import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewPin extends AppCompatActivity {

    private EditText editTitle;
    private EditText editBody;
    private Button confirmPin;
    private Pin placeholder;
    private Double newPinLat;
    private Double newPinLng;
    private String newPinUserEmail;
    Spinner postTypeSpinner;
    DatabaseReference databasePins;
    private Date currentDate = Calendar.getInstance().getTime();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(17432576,17432576 );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pin);

        databasePins = FirebaseDatabase.getInstance().getReference("Pins");

        postTypeSpinner = (Spinner) findViewById(R.id.postTypeSpinner);
        Button confirmPin = (Button) findViewById(R.id.buttonChoose);




        final Pin placeholder = new Pin(currentDate, "random body", "random title", 90.2, 80.2, "civil", "tej20bone@gmail.com", 0);


        final Toast notLongError = Toast.makeText(getApplicationContext(), "Finish YOURE post", Toast.LENGTH_SHORT);

        final EditText titleText = (EditText) findViewById(R.id.titleText);
        final EditText bodyText = (EditText) findViewById(R.id.bodyText);
        TextView Title = (TextView)findViewById(R.id.Title);


        Intent retrieveLatLng= getIntent();
        newPinLat = Double.parseDouble(retrieveLatLng.getExtras().getString("Lat"));
        newPinLng = Double.parseDouble(retrieveLatLng.getExtras().getString("Lng"));
        newPinUserEmail = retrieveLatLng.getExtras().getString("userEmail");


        confirmPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                placeholder.editBody(bodyText.getText().toString());
                placeholder.editTitle(titleText.getText().toString());
                placeholder.setDate(currentDate);

                    String postType = postTypeSpinner.getSelectedItem().toString();
                    String realPostType = "";

                    if (postType.equals("Civil")){realPostType = "civil";}
                    if (postType.equals("Advertisement")){realPostType = "advertisement";}
                    if (postType.equals("Activity")){realPostType = "activity";}
                    if (postType.equals("General")){realPostType = "general";}


                    String title = placeholder.getTitle();
                    String body = placeholder.getBody();
                    int likes = 0;
                   // Integer votes = placeholder.getVotes();


                    if (title.length() > 2 && body.length() > 2) {

                        if(isNetworkConnected() == true) {
                            String pinID = databasePins.push().getKey();
                            Pin pin = new Pin(currentDate, body, title, newPinLat, newPinLng, realPostType, newPinUserEmail, likes, pinID);
                            databasePins.child(pinID).setValue(pin);

                            Toast.makeText(NewPin.this, "Pin added!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(NewPin.this, MapsView.class);
                            startActivity(intent);
                        }
                        else{
                            Toast notLongError = Toast.makeText(getApplicationContext(), "Check Your Wifi", Toast.LENGTH_SHORT);

                        }


                    }
                    else{
                        Toast notLongError = Toast.makeText(getApplicationContext(), "FINISH YOU'RE POST", Toast.LENGTH_SHORT);

                    }
                    notLongError.show();


            }
        });

    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
