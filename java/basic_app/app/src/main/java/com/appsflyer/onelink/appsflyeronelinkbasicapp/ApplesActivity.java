package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.os.Bundle;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.AFInAppEventType; // Predefined event names
import com.appsflyer.AFInAppEventParameterName; // Predefined parameter names

import java.util.HashMap;
import java.util.Map;

public class ApplesActivity extends FruitActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStaticAttributes("apples");
        showFruitAmount();
        showDlData();

        Map<String, Object> eventValues = new HashMap<String, Object>();
        eventValues.put(AFInAppEventParameterName.CONTENT_TYPE, "fruit");
        eventValues.put(AFInAppEventParameterName.SCORE, 123);
        AppsFlyerLib.getInstance().logEvent(getApplicationContext(),
                AFInAppEventType.ADD_TO_CART, eventValues);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_apples;
    }
}