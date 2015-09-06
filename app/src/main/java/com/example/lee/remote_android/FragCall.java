package com.example.lee.remote_android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FragCall extends Fragment {

    public int sesid=0;
    Button aereo;
    public void setID(int id){
        sesid=id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_rubrica, container, false);

        aereo=(Button) view.findViewById(R.id.button5);

        aereo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HttpAereo asd = new HttpAereo(sesid+"","123456789");
                asd.execute();

            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}