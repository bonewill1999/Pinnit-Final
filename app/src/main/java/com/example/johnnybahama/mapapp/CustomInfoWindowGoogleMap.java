package com.example.johnnybahama.mapapp;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowGoogleMap(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.map_custom_infowindo, null);

        TextView name_tv = view.findViewById(R.id.name);
        TextView details_tv = view.findViewById(R.id.details);
        ImageView img = view.findViewById(R.id.pic);

        TextView likes_tv = view.findViewById(R.id.likes);
        TextView date_tv = view.findViewById(R.id.date);
        TextView poster_tv = view.findViewById(R.id.poster);

        name_tv.setText(marker.getTitle());
        details_tv.setText(marker.getSnippet());

        Pin infoPin = (Pin) marker.getTag();
        String date = "POOPPPEEEEEDOOOPOEEE";
//
//        int imageId = context.getResources().getIdentifier(infoPin.getImage().toLowerCase(),
//                "drawable", context.getPackageName());
//        img.setImageResource(imageId);

        likes_tv.setText("Number of likes is " + String.valueOf(infoPin.getLikes()));
        date_tv.setText(String.valueOf(infoPin.getDate().getHours()));
        poster_tv.setText("Posted By "+ infoPin.getOriginalPoster());

        return view;
    }
}