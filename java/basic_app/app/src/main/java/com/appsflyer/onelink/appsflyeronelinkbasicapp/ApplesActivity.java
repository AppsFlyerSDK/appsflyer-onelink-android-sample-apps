package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.os.Bundle;
import android.view.View;

public class ApplesActivity extends FruitActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewVariables("apples");
        showDlData();
    }
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_apples;
    }

    @Override
    public void goToConversionData(View view) {
        super.goToConversionData(view);
    }
}