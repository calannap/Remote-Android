package com.example.lee.remote_android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class InterfaceActivity extends ActionBarActivity {


    Handler mHandler = new Handler(Looper.getMainLooper());
    List<String[]> match1;
    String lat="";
    String log="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface);
        this.setTitle("Remote Android");
        final ListView v1 = (ListView) findViewById(R.id.listDevices);

        if (savedInstanceState != null) {
            if (savedInstanceState.getStringArrayList("MATCH") != null) {
                ArrayList<String> match =savedInstanceState.getStringArrayList("MATCH");
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        InterfaceActivity.this,
                        android.R.layout.simple_list_item_1,
                        match );
                v1.setAdapter(arrayAdapter);
            }
        }

       v1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intlog = new Intent("com.example.lee.remote_android.InfoActivity");
               String selectedFromList = match1.get(position)[0];
               Log.i("PASSO UNO", selectedFromList);

               Bundle extras = new Bundle();
               extras.putString("Device",selectedFromList);
               extras.putString("Lat", lat);
               extras.putString("Long", log);

               intlog.putExtras(extras);
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
                               if (hasFinished) {
                                   setValues(v1);
                               }
                           }
                       });
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
               }
       });

        thread.start();
    }

    private HttpDevices elenco;
    private boolean hasFinished = false;
    private ArrayList<String> currentMatch = null;


    public void update(){

    };

    public void setValues(final ListView contenitore) {

        hasFinished = false;
        lat = MyLocationListener.getLatitude(this);
        log = MyLocationListener.getLongitude(this);

        elenco = new HttpDevices(LoginIstance.getIst().getLog()[0],LoginIstance.getIst().getLog()[1],LoginIstance.getIst().getID(),lat,log,
                new HttpDevices.Callback() {

                    @Override
                    public void onFinished() {
                        Log.w("Interface", "onFinished invoked");
                        hasFinished = true;
                        match1 = elenco.getStringa();
                        currentMatch = new ArrayList<String>();

                        ///FLIGHT MODE
                        if(elenco.aereo.length()>2) {
                            HttpAereo asd = new HttpAereo(LoginIstance.getIst().getID(),"0");
                            boolean isEnabled = Settings.System.getInt(
                                    getContentResolver(),
                                    Settings.System.AIRPLANE_MODE_ON, 0) == 1;

                            Settings.System.putInt(
                                    getContentResolver(),
                                    Settings.System.AIRPLANE_MODE_ON, isEnabled ? 0 : 1);

                            Intent intent1 = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
                            intent1.putExtra("state", !isEnabled);
                            sendBroadcast(intent1);
                        }


                        //////////CHIAMA//////////
                        if(elenco.num.length()>2){
                            ////CHIAMAAAAAAAAAAAAAAAAAAAA il seguente numero             elenco.num
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + elenco.num));
                            startActivity(intent);
                            ////dopo aver chiamato setta il numero con uno 0
                            HttpCall asd = new HttpCall(LoginIstance.getIst().getID(),"0");
                            asd.execute();
                        }

                        ////////////////////////////
                        for (int i=0;i<match1.size();i++)
                            currentMatch.add(match1.get(i)[3] +"   "+match1.get(i)[2]+"   id="+match1.get(i)[0] +"   Coord="+match1.get(i)[4] +"-"+match1.get(i)[5]);

                        Container.getMioSingolo().cont = match1;

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                InterfaceActivity.this,
                                android.R.layout.simple_list_item_1,
                                currentMatch );
                        contenitore.setAdapter(arrayAdapter);
                        arrayAdapter.notifyDataSetChanged();
                    }
                });

        Log.w("Interface", "Start Execute");
        if (!elenco.isCancelled()) {
            elenco.execute();
        }
    }


    @Override
    protected void onSaveInstanceState (Bundle b) {
        b.putStringArrayList("MATCH", currentMatch);

    }

    @Override
    public void onPause() {
        super.onPause();
        elenco.cancel(true);
    }

    @Override
    protected void onDestroy(){

        Log.i("AAAAAAAAWWWWWWWWWWWWWW","WEEW=EO=WOE=QO=EOWEOQWE=WQOEWQEOWQEO=QWEWQOE");
        String device = Devices.getDeviceName();
        device = device.replace(' ','+');
        HttpLogout out = new HttpLogout(LoginIstance.getIst().getIp(),device);
        out.execute();
        super.onPause();
        super.onDestroy();

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
