package com.example.lee.remote_android;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class FragCoord extends Fragment {

    public int sesid=0;
    Handler mHandler = new Handler();
    TextView v1;
    TextView v2;
    Button mon;
    EditText edt;
    String lat,log,lattmp="0.0",logtmp="0.0";
    Context ctx;
    public List<String[]> match1 = Container.getMioSingolo().cont;
    public void setID(int id){
        sesid=id;
    }

    public void setCoords(String s1, String s2){

        lat = s1;
        log = s2;
        lattmp=s1;
        logtmp=s2;
    }

    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
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
        mon = (Button) myInflatedView.findViewById((R.id.button3));
        edt = (EditText) myInflatedView.findViewById((R.id.editText4));
        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("WTF?","mah non funziona ma che spacchio e");
                lattmp=lat;
                logtmp=log;

            }
        });
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                lattmp=lat;
                logtmp=log;
                while(true) {

                    try {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                match1 = Container.getMioSingolo().cont;
                                for (int i=0;i<match1.size();i++){
                                    if(Integer.parseInt(match1.get(i)[0])==sesid) {
                                        lat = match1.get(i)[4];
                                        log = match1.get(i)[5];
                                        v1.setText(lat);
                                        v2.setText(log);
                                        if(!lattmp.equals("0.0") && !logtmp.equals(0.0))
                                        edt.setText(String.valueOf(distance( Double.parseDouble(lat), Double.parseDouble(lattmp), Double.parseDouble(log), Double.parseDouble(logtmp), 0.0,0.0)));

                                        Log.i("GGWP","lat= "+lat+"lattmp= "+lattmp+" log= "+log+ " logtmp= "+logtmp);
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