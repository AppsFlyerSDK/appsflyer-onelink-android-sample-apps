package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.os.Bundle;

import java.util.HashMap;

public class BananasActivity extends FruitActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewVariables("bananas");
        this.shareInviteLinkParams = new HashMap(){{
            put("deep_link_value", "bananas");
            put("deep_link_sub1", "This is a shared link from 'Bananas' activity");
            put("af_campaign", "shared link");
            put("fruit_amount", "20");
        }};
        showDlData();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_bananas;
    }
}
