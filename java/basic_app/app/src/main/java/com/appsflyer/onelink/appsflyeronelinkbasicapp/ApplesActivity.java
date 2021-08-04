package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.os.Bundle;

import java.util.HashMap;

public class ApplesActivity extends FruitActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewVariables("apples");
        this.shareInviteLinkParams = new HashMap(){{
            put("deep_link_value", "apples");
            put("af_sub1", "This is a shared link from 'Apples' activity");
            put("af_campaign", "shared link");
            put("fruit_amount", "20");
        }};
        showDlData();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_apples;
    }
}