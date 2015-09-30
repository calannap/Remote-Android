package com.example.lee.remote_android;

/**
 * Created by Lee on 26/07/2015.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Ilya Gazman on 7/24/2015.
 */
public enum  GCMService {

    instance;

    public static final String TOKEN = "TOKEN";
    private final GCMCallback DEFAULT_CALLBACK = new GCMCallback() {
        @Override
        public void onReady(String token) {

        }

        @Override
        public void onGettingData(String from, Bundle data) {

        }

        @Override
        public void onError(String error) {
            Toast.makeText(context, error, Toast.LENGTH_LONG).show();
        }
    };
    private GCMCallback callback = DEFAULT_CALLBACK;
    private ExecutorService executorService = Executors.newFixedThreadPool(1);
    private Handler handler = new Handler(Looper.getMainLooper());
    private Context context;


    public void setCallback(GCMCallback callback) {
        this.callback = callback;
    }

    public void clearCallback(){
        callback = DEFAULT_CALLBACK;
    }

    public void init(Context c){
        this.context = c.getApplicationContext();

        SharedPreferences sharedPreferences = getSharedPreferences();
        String token = sharedPreferences.getString(TOKEN, null);
        if(token != null){
            callback.onReady(token);
            return;
        }

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                String token = null;
                InstanceID instanceID = InstanceID.getInstance(context);
                String senderId = "";//context.getString(R.string.gcm_defaultSenderId);
                try {
                    token = instanceID.getToken(senderId,
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (token == null) {
                    sendError(" Failed obtaining Registration token");
                }
                else{
                    getSharedPreferences().edit().putString(TOKEN, token).apply();
                    final String finalToken = token;
                    if(!sendTokenToMyServer()){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onError("Failed connecting to my Server");
                            }
                        });
                        return;
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onReady(finalToken);
                        }
                    });

                }
            }
        });
    }

    private boolean sendTokenToMyServer() {
        // TODO: Implement your server logic at here, You need to make HTTP call to your server(in order to pass the token) and return true or false if it was successful.
        return false;
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences("GCM", Context.MODE_PRIVATE);
    }

    public void deleteToken(){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                InstanceID instanceID = InstanceID.getInstance(context);
                String senderId = "";//context.getString(R.string.gcm_defaultSenderId);
                try {
                    instanceID.deleteToken(senderId,
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE);
                } catch (IOException e) {
                    e.printStackTrace();
                    sendError("Failed deleting token");
                    return;
                }
                getSharedPreferences().edit().remove(TOKEN).apply();
            }
        });
    }

    private void sendError(final String error){
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(error);
            }
        });
    }

    void onGettingData(String from, Bundle data) {
        callback.onGettingData(from, data);
    }
}
