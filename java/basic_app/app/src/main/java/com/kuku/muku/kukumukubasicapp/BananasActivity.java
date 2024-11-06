package com.kuku.muku.kukumukubasicapp;

import android.os.Bundle;

import com.kuku.muku.kukumukubasicapp.R;

import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
