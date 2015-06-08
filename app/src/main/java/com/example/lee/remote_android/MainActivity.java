package com.example.lee.remote_android;

import android.app.ListActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    private Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // codice "umano"
       // String [] s1 = {"Spegni device"};
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), android.R.layout.simple_expandable_list_item_1,s1);
       // getListView().setAdapter(adapter);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(mButtonStopListener);

    }
    View.OnClickListener mButtonStopListener = new View.OnClickListener() {
        public void onClick(View v) {
            try {
                //funziona solo su quelli con il root
                Runtime.getRuntime().exec(new String[]{ "su", "-c", "reboot -p" });
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.e("onPause", e.getMessage());
            }

        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
