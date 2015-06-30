package com.example.lee.remote_android;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class InterfaceActivity extends ActionBarActivity implements Runnable {

    Handler mHandler = new Handler();
    List<String[]> match1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface);
        this.setTitle("Remote Android");
        final ListView v1 = (ListView) findViewById(R.id.listDevices);

       v1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intlog = new Intent("com.example.lee.remote_android.InfoActivity");
               String selectedFromList = match1.get(position)[0];
               Log.i("PASSO UNO",selectedFromList);
               intlog.putExtra("Device", selectedFromList);
               startActivity(intlog);

           }
       });

        setValues(v1);
       Thread thread = new Thread(new Runnable() {
           @Override
           public void run() {
               while(true) {

                   try {
                       mHandler.post(new Runnable() {
                           @Override
                           public void run() {
                               setValues(v1);
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
    }

    public void setValues(ListView contenitore) {
        HttpDevices elenco = new HttpDevices(LoginIstance.getIst().getLog()[0],LoginIstance.getIst().getLog()[1],LoginIstance.getIst().getID());
        elenco.execute();
        while(elenco.finish()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
         match1 = elenco.getStringa();
        List<String> match = new ArrayList<String>();


        for (int i=0;i<match1.size();i++)
            match.add(match1.get(i)[3] +"   "+match1.get(i)[2] );


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                match );
        contenitore.setAdapter(arrayAdapter);

    }


    @Override
   public void run() {


        while(true){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(10000);
                                setValues((ListView) findViewById(R.id.listDevices));

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });

            }
        }




        @Override
    public void onPause()
    {


        String device = Devices.getDeviceName();
        device = device.replace(' ','+');
        HttpLogout out = new HttpLogout(LoginIstance.getIst().getIp(),device);
        out.execute();
        while(out.finish()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
        super.onPause();
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
