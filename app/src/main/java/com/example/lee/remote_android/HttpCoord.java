package com.example.lee.remote_android;

/**
 * Created by Lee on 25/06/2015.
 */
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
import java.util.List;

public class HttpCoord extends AsyncTask<String, Void, String>  {


    public String idses="";
    public boolean finito=true;

    public HttpCoord(String s1){
        idses=s1;
    }




    public List<String> output = new ArrayList<String>();


    public List<String> getStringa(){
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

    protected List<String> inviaDati() {
        String result = "";
        List<String> stringaFinale= new ArrayList<String>();
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("idnomerichiesto", "1"));
        InputStream is = null;

        //http post
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://88.116.86.82/android/remote/cord.php?id="+idses);
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
                    Log.i("0MGDGFG", "lat: " + json_data.getString("lat") +
                                    ", lon: " + json_data.getString("lon")

                    );

                    stringaFinale.add(json_data.getString("lat")  + " " + json_data.getString("lon")+ "\n\n");
                }
            } catch (JSONException e) {
                Log.e("log_tag", "Error parsing data " + e.toString());
            }
        } else {  //is � null e non ho avuto risposta
        }
        return stringaFinale;
    }
}