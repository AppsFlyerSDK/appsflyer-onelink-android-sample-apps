package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class conversionDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion_data);
        displayConversionData();
    }

    public void displayConversionData(){
        SharedPreferences appSharedPreferences = this.getSharedPreferences("CONVERSIONDATA", getApplicationContext().MODE_PRIVATE);
        Map<String, Object> conversionData = (Map<String, Object>) appSharedPreferences.getAll();
        String conversionDataString = MapToSortedString(conversionData);
        TextView conversionDataTextView = findViewById(R.id.conversionDataTextView);
        conversionDataTextView.setMovementMethod(new ScrollingMovementMethod());
        conversionDataTextView.setText(conversionDataString);
    }

    public String MapToSortedString(Map<String, Object> map){
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
