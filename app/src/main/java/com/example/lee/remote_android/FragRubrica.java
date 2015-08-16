package com.example.lee.remote_android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



public class FragRubrica extends Fragment {

    Button chiama;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        chiama = (Button) findViewById(R.id.button4);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_rubrica, container, false);
    }
}