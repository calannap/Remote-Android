package com.example.lee.remote_android;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class FragCoord extends Fragment {

    public int sesid=0;
    Handler mHandler = new Handler();
    TextView v1;
    TextView v2;
    String lat,log;
    Context ctx;
    public List<String[]> match1 = Container.getMioSingolo().cont;
    public void setID(int id){
        sesid=id;
    }

    public void setCoords(String s1, String s2){

        lat = s1;
        log = s2;
    }

    public void setCont(Context asd){
        ctx = asd;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myInflatedView = inflater.inflate(R.layout.fragment_frag_coord, container,false);

        v1= (TextView) myInflatedView.findViewById(R.id.textView3);
        v2= (TextView) myInflatedView.findViewById(R.id.textView4);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {

                    try {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                match1 = Container.getMioSingolo().cont;
                                for (int i=0;i<match1.size();i++){
                                    if(Integer.parseInt(match1.get(i)[0])==sesid) {
                                        v1.setText(match1.get(i)[4]);
                                        v2.setText(match1.get(i)[5]);
                                    }}
                            }
                        });
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

        return myInflatedView;
    }
}