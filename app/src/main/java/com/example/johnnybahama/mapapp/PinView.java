package com.example.johnnybahama.mapapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;


public class PinView extends AppCompatActivity {
    TextView postTitle, postBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_view);

        getIntent(); //data of pin sent from previous activity to display in postView
        String title = getIntent().getStringExtra("Title");
        String body = getIntent().getStringExtra("Body");

        postTitle = (TextView) findViewById(R.id.Title);
        postBody = (TextView) findViewById(R.id.Body);

        postTitle.setText(title);
        postBody.setText(body); //sets body and title to match that of the chosen pin

    }


}
