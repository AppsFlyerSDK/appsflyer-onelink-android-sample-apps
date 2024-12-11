package com.kuku.muku.kukumukubasicapp;

import android.os.Bundle;

public class BananasActivity extends FruitActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStaticAttributes("bananas");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_bananas;
    }
}
