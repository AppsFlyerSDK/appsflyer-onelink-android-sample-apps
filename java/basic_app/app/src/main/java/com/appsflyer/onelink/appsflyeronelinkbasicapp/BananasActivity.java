package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.os.Bundle;

public class BananasActivity extends FruitActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStaticAttributes("bananas");
        showDlData();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_bananas;
    }
}
