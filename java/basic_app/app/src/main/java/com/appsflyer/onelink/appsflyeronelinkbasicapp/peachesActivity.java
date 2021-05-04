package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.os.Bundle;
import android.view.View;

public class peachesActivity extends FruitActivity {

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

    @Override
    public void goToConversionData(View view) {
        super.goToConversionData(view);
    }
}
