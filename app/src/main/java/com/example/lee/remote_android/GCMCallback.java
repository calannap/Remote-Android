package com.example.lee.remote_android;

/**
 * Created by Lee on 26/07/2015.
 */
import android.os.Bundle;


public interface GCMCallback {
    void onReady(String token);
    void onGettingData(String from, Bundle data);

    void onError(String error);
}
