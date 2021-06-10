package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.os.Bundle;

public class PeachesActivity extends FruitActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewVariables("peaches");
        showDlData();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_peaches;
    }
}
