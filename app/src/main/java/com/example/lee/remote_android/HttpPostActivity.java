import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lee.remote_android.R;

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

public class HttpPostActivity extends Activity {

        @Override
        public  void  onCreate(Bundle savedInstanceState) {
        super  .onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final TextView textviewDatiRicevuti = (TextView) findViewById(R.id.datiRicevuti);
        Button buttonInviaDati = (Button) findViewById(R.id.buttonInviaDati);
        buttonInviaDati.setOnClickListener(  new  View.OnClickListener() {
        public  void  onClick(View view) {
        //invio richiesta
        textviewDatiRicevuti.setText(inviaDati());
        }
        });
        }
        public  String inviaDati(){
        String result =   ""  ;
        String stringaFinale =   ""  ;
        ArrayList<NameValuePair> nameValuePairs =   new ArrayList <NameValuePair>();
        nameValuePairs.add(  new BasicNameValuePair(  "idnomerichiesto"  ,  "1"  ));
        InputStream is =   null  ;
        //http post
        try  {
        HttpClient httpclient =   new DefaultHttpClient();
        HttpPost httppost =   new  HttpPost(  "http://10.1.1.1:3306/miosito/connectDb.php"  );
        httppost.setEntity(  new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        is = entity.getContent();
        }  catch  (Exception e){
        Log.e("TEST", "Errore nella connessione http " + e.toString());
        }
        if  (is !=   null  ){
        //converto la risposta in stringa
        try  {
        BufferedReader reader =   new  BufferedReader(  new InputStreamReader(is,  "iso-8859-1"  ),  8  );
        StringBuilder sb =   new  StringBuilder();
        String line =   null  ;
        while  ((line = reader.readLine()) !=   null  ) {
        sb.append(line +   "\n"  );
        }
        is.close();
        result=sb.toString();
        }  catch  (Exception e){
        Log.e(  "TEST"  ,   "Errore nel convertire il risultato "  +e.toString());
        }
        //parsing dei dati arrivati in formato json
        try  {
        JSONArray jArray =   new  JSONArray(result);
        for  (  int  i=  0  ;i&lt;jArray.length();i++){
        JSONObject json_data = jArray.getJSONObject(i);
        Log.i(  "TEST"  ,  "id: "  +json_data.getInt(  "id"  )+
        ", cognome: "  +json_data.getString(  "cognome"  )+
        ", nascita: "  +json_data.getInt(  "anno"  )
        );
        stringaFinale = json_data.getInt(  "id"  ) +   " "  + json_data.getString(  "cognome"  ) +   " "  + json_data.getInt(  "anno"  ) +   "\n\n"  ;
        }
        }
        catch  (JSONException e){
        Log.e(  "log_tag"  ,   "Error parsing data "  +e.toString());
        }
        }
        else  {  //is è null e non ho avuto risposta
        }
        return  stringaFinale;
        }
        }