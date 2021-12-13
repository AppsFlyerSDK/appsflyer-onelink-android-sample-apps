package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.os.Bundle;

public class PeachesActivity extends FruitActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStaticAttributes("peaches");
        showFruitAmount();
        showDeepLinkData();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_peaches;
    }
}
