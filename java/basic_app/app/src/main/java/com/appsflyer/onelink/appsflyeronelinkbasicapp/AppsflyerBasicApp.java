package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.app.Application;

import java.util.Map;

import io.branch.referral.Branch;

public class AppsflyerBasicApp extends Application {
    public static final String LOG_TAG = "AppsFlyerOneLinkSimApp";
    public static final String DL_ATTRS = "dl_attrs";
    Map<String, Object> conversionData = null;

    // This boolean flag signals between the UDL and GCD callbacks that this deep_link was
    // already processed, and the callback functionality for deep linking can be skipped.
    // When GCD or UDL finds this flag true it MUST set it to false before skipping.
    boolean deferred_deep_link_processed_flag = false;

    @Override
    public void onCreate() {
        super.onCreate();

        // Branch logging for debugging
        Branch.enableLogging();

        // Branch object initialization
        Branch.getAutoInstance(this);

    }
}
