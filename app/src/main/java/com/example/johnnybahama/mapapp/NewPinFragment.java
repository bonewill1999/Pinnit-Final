package com.example.johnnybahama.mapapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewPinFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewPinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewPinFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText editTitle;
    private EditText editBody;
    private Button confirmPin;
    private Pin placeholder;
    private Double newPinLat;
    private Double newPinLng;
    Spinner postTypeSpinner;
    DatabaseReference databasePins;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

  //  private OnFragmentInteractionListener mListener;

    public NewPinFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewPinFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewPinFragment newInstance(String param1, String param2) {
        NewPinFragment fragment = new NewPinFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        databasePins = FirebaseDatabase.getInstance().getReference("Pins");


    }
}
