package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import com.appsflyer.AppsFlyerLib;
import com.appsflyer.AFInAppEventType; // Predefined event names
import com.appsflyer.AFInAppEventParameterName; // Predefined parameter names

public class PeachesActivity extends FruitActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStaticAttributes("peaches");
        showFruitAmount();
        showDlData();
        sendEvent();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_peaches;
    }

    protected void sendEvent() {
       //TODO - Add event
    }

}
