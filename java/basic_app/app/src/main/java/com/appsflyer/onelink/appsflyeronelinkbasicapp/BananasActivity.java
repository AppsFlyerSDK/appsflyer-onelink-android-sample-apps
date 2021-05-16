package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.os.Bundle;
import android.view.View;

public class BananasActivity extends FruitActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewVariables("bananas");
        showDlData();
    }
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_bananas;
    }

    @Override
    public void goToConversionData(View view) {
        super.goToConversionData(view);
    }
}
