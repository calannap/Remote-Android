package com.example.lee.remote_android;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class HttpLogin extends AsyncTask<String, Void, String>  {

    public static String usr="";
    public static String pss="";
    public static String ip="";
    public static String id="";
    public static String lat="0";
    public static String log="0";


    public boolean finito=true;



    public HttpLogin(String s1,String s2){
        usr=s1;
        pss=s2;
    }

    public String getId(){
        return id;
    }

    public String getUser(){
        return usr;
    }

    public String getPsw(){
        return pss;
    }

    public String getIP() {return ip;}

    public void setId(String s){
        id = s;

    }


   public String output="";


    public String getStringa(){
        return output;
    }

    public boolean finish(){
        return finito;
    }
    @Override
    protected String doInBackground(String... params) {
        output = inviaDati();
        finito=false;
        return null;
    }

    protected String inviaDati() {
        String result = "";
        String stringaFinale= "";
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("idnomerichiesto", "1"));
        InputStream is = null;
        ip = Utils.getIPAddress(true);
        String device = Devices.getDeviceName();

        //http post
        try {

            device = device.replace(' ','+');
            HttpClient httpclient = new DefaultHttpClient();
            if (MyLocationListener.latitude!=0.0 && MyLocationListener.longitude!=0.0) {
                lat = String.valueOf(MyLocationListener.latitude);
                log = String.valueOf(MyLocationListener.longitude);
            }
            HttpPost httppost = new HttpPost("http://88.116.86.82/android/remote/controllouser.php?user="+usr+"&pass="+pss+"&device="+device+"&ip="+ip+"&lat="+lat+"&lon="+log);

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch (Exception e) {
            Log.e("TEST", "Errore nella connessione http " + e.toString());
        }
        if (is != null) {
            //converto la risposta in stringa
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
            } catch (Exception e) {
                Log.e("TEST", "Errore nel convertire il risultato " + e.toString());
            }


            //parsing dei dati arrivati in formato json
            try {
                JSONArray jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    Log.i("TEST", "max(id): " + json_data.getInt("max(id)")
                                   // ", user: " + json_data.getString("user") +
                                  //  ", pass: " + json_data.getString("pass") +
                                   // ", email: " + json_data.getString("email")
                    );
                    stringaFinale = json_data.getString("max(id)");
                    // + " " + json_data.getString("user") + " " + json_data.getString("pass")+ " " + json_data.getString("email") + "\n\n";
                }
            } catch (JSONException e) {
                Log.e("log_tag", "Error parsing data " + e.toString());
            }
        } else {  //is è null e non ho avuto risposta
        }
        return stringaFinale;
    }
}