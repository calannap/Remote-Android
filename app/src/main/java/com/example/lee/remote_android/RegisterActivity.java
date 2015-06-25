package com.example.lee.remote_android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class RegisterActivity extends ActionBarActivity {


    TextView user,password,email;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        onClickListener();
    }

    public void onClickListener() {

        register = (Button) findViewById(R.id.regbut);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = (TextView) findViewById(R.id.edituser);
                password = (TextView) findViewById(R.id.editpass);
                email = (TextView) findViewById(R.id.editemail);
                String match="";
                HttpRegister connection = new HttpRegister(user.getText().toString(), password.getText().toString(),email.getText().toString());
                connection.execute();

                while(connection.finish()){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                }
                match = connection.getStringa();





                if(match.equals("1")) {
                    Context context = getApplicationContext();
                    CharSequence text = "Account creato";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else{
                    Context context = getApplicationContext();
                    CharSequence text = "Username gia' esistente!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
