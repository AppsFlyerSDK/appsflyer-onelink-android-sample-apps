package com.kuku.muku.kukumukubasicapp;

import android.os.Bundle;

import com.kuku.muku.kukumukubasicapp.R;

public class ApplesActivity extends FruitActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStaticAttributes("apples");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_apples;
    }
}