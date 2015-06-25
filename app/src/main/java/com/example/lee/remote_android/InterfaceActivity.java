package com.example.lee.remote_android;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;


public class InterfaceActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface);
        //TextView t1 = (TextView) findViewById(R.id.txtprova);
        ListView v1 = (ListView) findViewById(R.id.listDevices);
      /*  v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = v1.getSelectedItemPosition();
            }
        });*/


        HttpDevices elenco = new HttpDevices(HttpLogin.getLogin().getUser(),HttpLogin.getLogin().getPsw());
        elenco.execute();
        while(elenco.finish()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
        List<String> match = elenco.getStringa();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                match );
        v1.setAdapter(arrayAdapter);


    }



    @Override
    public void onDestroy()
    {
        Log.i("WEEELAAA", "STO CANCELLANDOOO");
        String ip = Utils.getIPAddress(true);
        String device = Devices.getDeviceName();
        HttpLogout out = new HttpLogout(ip,device);
        out.execute();
        while(out.finish()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
    }

    @Override
    public void onStop()
    {
        Log.i("WEE2222A", "STO CANCELLANDOOO");
        String ip = Utils.getIPAddress(true);
        String device = Devices.getDeviceName();
        HttpLogout out = new HttpLogout(ip,device);
        out.execute();
        while(out.finish()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
    }



    @Override
    public void onPause()
    {
        Log.i("WE3333A", "STO CANCELLANDOOO");
        String ip = Utils.getIPAddress(true);
        String device = Devices.getDeviceName();
        HttpLogout out = new HttpLogout(ip,device);
        out.execute();
        while(out.finish()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_interface, menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
