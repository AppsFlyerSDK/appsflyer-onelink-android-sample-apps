package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class ConversionDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion_data);
        Map<String, Object> conversionData = ((AppsflyerBasicApp) getApplication()).conversionData;
        displayConversionData(conversionData);
    }

    public void displayConversionData(Map<String, Object> conversionData){
        String conversionDataString = mapToSortedString(conversionData);
        TextView conversionDataTextView = findViewById(R.id.conversionDataTextView);
        conversionDataTextView.setMovementMethod(new ScrollingMovementMethod());
        conversionDataTextView.setText(conversionDataString);
    }

    public String mapToSortedString(Map<String, Object> map){
        String result = "";
        SortedSet<String> keys = new TreeSet<>(map.keySet());
        Object value;
        for (String key: keys){
            value = map.get(key);
            if (value == null) {
                value = "null";
            }
            result += String.format("%s : %s\n", key, value);
        }
        return result;
    }
}
