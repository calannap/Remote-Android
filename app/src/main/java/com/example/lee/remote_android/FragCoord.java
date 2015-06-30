package com.example.lee.remote_android;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragCoord extends Fragment {

    public int sesid=0;
    Handler mHandler = new Handler();
    TextView v1;
    TextView v2;

    public void setID(int id){
        sesid=id;
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
                                v1.setText(""+sesid++);
                                v2.setText(""+sesid);
                            }
                        });
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
        v1.setText("Text to Display");

        return myInflatedView;
    }
}