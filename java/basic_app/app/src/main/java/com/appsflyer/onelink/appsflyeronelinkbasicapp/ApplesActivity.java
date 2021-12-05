package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.os.Bundle;

public class ApplesActivity extends FruitActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStaticAttributes("apples");
        showDlData();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_apples;
    }
}