package com.example.lee.remote_android;


import com.google.android.gms.iid.InstanceIDListenerService;


public class MyInstanceIDListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        GCMService.instance.init(this);
        GCMService.instance.deleteToken();
        GCMService.instance.init(this);

    }
}
