package com.example.lee.remote_android;

/**
 * Created by Lee on 30/06/2015.
 */
public class LoginIstance {

    String usps[] = new String[2];
    String id;
    String ip=Utils.getIPAddress(true);
    static  LoginIstance ist = new LoginIstance();

    private LoginIstance(){

    }

    public String getIp(){
        return ip;
    }
    public void setID(String s){
        id=s;
    }

    public String getID(){
        return id;
    }
    public String[] getLog(){

        return usps;
    }

    public static LoginIstance getIst(){
        return ist;
    }

    public void setLog(String s1,String s2){
        usps[0] = s1;
        usps[1] = s2;
    }
}
