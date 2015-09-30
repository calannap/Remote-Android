package com.example.lee.remote_android;

/**
 * Created by Lee on 25/06/2015.
 */
import android.content.Context;
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

public class HttpDevices extends AsyncTask<String, Void, String>  {

    public static interface Callback {
        public void onFinished();
    }

    public String usr="";
    public String pss="";
    public String id="";
    public String num="";
    public String aereo="";
    public boolean finito=true;
    public String lat="0";
    public String log="0";
    Context cont;
    private Callback callback;


    public HttpDevices(String s1, String s2,String s3,String s4, String s5, Callback callback){
        usr=s1;
        pss=s2;
        id=s3;
        lat = s4;
        log = s5;

        this.callback = callback;
    }




    public List<String[]> output = new ArrayList<String[]>();


    public List<String[]> getStringa(){
        return output;
    }

    public boolean finish(){
        return finito;
    }
    @Override
    protected String doInBackground(String... params) {
        Log.w("HttpDevices", "inBackground");
        output = inviaDati();
        finito=false;
        return null;
    }

    @Override
    protected void onPostExecute (String data) {
        if (callback != null) {
            callback.onFinished();
        }
    }

    protected List<String[]> inviaDati() {
        String result = "";
        List<String[]> stringaFinale= new ArrayList<String[]>();
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("idnomerichiesto", "1"));
        InputStream is = null;

        //http post
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://88.116.86.82/android/remote/connessione.php?user="+usr+"&pass="+pss+"&id="+id+"&lat="+lat+"&long="+log);

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
                  /*  Log.i("TESTONE", "id: " + json_data.getInt("id") +
                                    ", id_utenti: " + json_data.getString("id_utenti") +
                             ", ip: " + json_data.getString("ip") +
                             ", nome: " + json_data.getString("nome")
                    );*/
                    if(id.equals(json_data.getString("id"))){
                        if(json_data.getString("aereo").length()>2){
                            aereo=json_data.getString("aereo");
                        }else{
                            aereo="";
                        }
                        if(json_data.getString("num").length()>2){
                            num=json_data.getString("num");
                        }else{
                            num="";
                        }
                    }else{
                        stringaFinale.add(new String[]{json_data.getString("id") ,json_data.getString("id_utenti"),json_data.getString("ip"), json_data.getString("nome"),json_data.getString("lat"),json_data.getString("long"),json_data.getString("num") });

                    }

                }
            } catch (JSONException e) {
                Log.e("log_tag", "Error parsing data " + e.toString());
            }
        } else {  //is e null e non ho avuto risposta
        }

        return stringaFinale;
    }

}