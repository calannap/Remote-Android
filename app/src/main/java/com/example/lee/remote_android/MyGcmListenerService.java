package com.example.lee.remote_android;

/**
 * Created by Lee on 15/07/2015.
 */
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmListenerService;

public class MyGcmListenerService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        String message = "Getting data from: " + from;

        GCMService.instance.onGettingData(from, data);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
