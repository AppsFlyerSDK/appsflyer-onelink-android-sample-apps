package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import com.appsflyer.AppsFlyerLib;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;

public class AppsflyerBasicApp extends Application {
    public static final String LOG_TAG = "AppsFlyerOneLinkSimApp";
    public static final String DL_ATTRS = "dl_attrs";
    Map<String, Object> conversionData = null;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;


    @Override
    public void onCreate() {
        super.onCreate();

        try {
            sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
            myEdit = sharedPreferences.edit();
            myEdit.clear();
            myEdit.apply();
        } catch (Exception e) {
            Log.d(LOG_TAG, "Could not clear previous Shared Preferences");
        }
    }
}
