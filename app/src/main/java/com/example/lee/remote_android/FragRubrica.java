package com.example.lee.remote_android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;


public class FragRubrica extends Fragment {

    public int sesid=0;
    Button chiama;
    TextView number;

    public void setID(int id){
        sesid=id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_rubrica, container, false);

        chiama=(Button) view.findViewById(R.id.button4);
        number=(TextView)view.findViewById(R.id.editText3);

        chiama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HttpCall asd = new HttpCall(sesid+"",number.getText().toString());
                asd.execute();

            }
        });
        return view;
    }


}
