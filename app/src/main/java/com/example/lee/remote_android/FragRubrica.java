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


public class FragRubrica extends Fragment {

    Button chiama;
    TextView number;


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
                Log.e("AWEEWE",number.getText().toString());
            }
        });
        return view;
    }


}
