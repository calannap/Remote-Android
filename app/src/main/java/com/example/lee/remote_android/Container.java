package com.example.lee.remote_android;

import java.util.List;

/**
 * Created by Lee on 03/09/2015.
 */
public class Container {
    private static Container istanza = null;
    public String latc;
    public String logc;
    public List<String[]> cont;
    private Container() {
    }


    public static synchronized Container getMioSingolo() {
        if (istanza == null) {
            istanza = new Container();
        }
        return istanza;


    }

}
