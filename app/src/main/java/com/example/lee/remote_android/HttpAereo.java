package com.example.lee.remote_android;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by Lee on 16/08/2015.
 */
public class HttpAereo extends AsyncTask<String, Void, String> {

    String id="";
    String num="";
    public HttpAereo(String s1, String s2){
        id=s1;
        num=s2;
    }



    @Override
    protected String doInBackground(String... params) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://88.116.86.82/android/remote/aereo.php?id="+id+"&num="+num);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
        } catch (Exception e) {
            Log.e("TEST", "Errore nella connessione http " + e.toString());
        }
        return null;
    }
}
