package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import androidx.annotation.NonNull;

import com.appsflyer.AppsFlyerLib;
import com.appsflyer.AFInAppEventType; // Predefined event names
import com.appsflyer.AFInAppEventParameterName; // Predefined parameter names
import com.appsflyer.attribution.AppsFlyerRequestListener;

import static com.appsflyer.onelink.appsflyeronelinkbasicapp.AppsflyerBasicApp.LOG_TAG;


public class BananasActivity extends FruitActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStaticAttributes("bananas");
        showFruitAmount();
        showDlData();
        sendEventAsync();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_bananas;
    }

    protected void sendEventAsync() {
       //TODO - Add Async Event
    }
}
