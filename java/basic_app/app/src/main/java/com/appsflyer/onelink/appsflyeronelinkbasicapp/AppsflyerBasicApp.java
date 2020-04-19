package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.app.Application;

import com.appsflyer.AppsFlyerLib;
import com.appsflyer.onelink.appsflyeronelinkbasicapp.AppsFlyerConstants;

public class AppsflyerBasicApp extends Application {
    public static final String LOG_TAG = "AppsFlyerFeedMeApp";
    @Override
    public void onCreate() {
        super.onCreate();
        //noinspection SpellCheckingInspection
        String afDevKey = AppsFlyerConstants.afDevKey;
        AppsFlyerLib appsflyer = AppsFlyerLib.getInstance();
        appsflyer.setMinTimeBetweenSessions(0);
        appsflyer.init(afDevKey, null, this);
        appsflyer.startTracking(this, afDevKey);
        appsflyer.setDebugLog(true);
    }
}