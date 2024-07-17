package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.os.Bundle;

import com.appsflyer.adrevenue.AppsFlyerAdRevenue;
import com.appsflyer.adrevenue.adnetworks.AppsFlyerAdNetworkEventType;
import com.appsflyer.adrevenue.adnetworks.generic.MediationNetwork;
import com.appsflyer.adrevenue.adnetworks.generic.Scheme;

import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BananasActivity extends FruitActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStaticAttributes("bananas");
        showFruitAmount();
        showDlData();

        Map<String, String> customParams = new HashMap<>();
        customParams.put(Scheme.COUNTRY, "US");
        customParams.put(Scheme.AD_UNIT, "89b8c0159a50ebd1");
        customParams.put(Scheme.AD_TYPE, "Banner");
        customParams.put(Scheme.PLACEMENT, "place");
        customParams.put(Scheme.ECPM_PAYLOAD, "encrypt");
        customParams.put("foo", "test1");
        customParams.put("bar", "test2");

// Record a single impression
        AppsFlyerAdRevenue.logAdRevenue(
                "ironsource",
                MediationNetwork.googleadmob,
                Currency.getInstance(Locale.US),
                0.99,
                customParams
        );

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_bananas;
    }
}
