package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import java.sql.SQLOutput;
import java.util.Map;

public class conversionDataActivity extends AppCompatActivity {
    static SharedPreferences appSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion_data);

        displayConversionData();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                System.out.println("OnSharedPreferenceChangeListener activated successfully"); //TODO - get rid of this print
                Map<String, Object> conversionData = (Map<String, Object>) sharedPreferences.getAll();
                displayConversionData();
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    public void displayConversionData(){
        appSharedPreferences = this.getSharedPreferences("CONVERSIONDATA", getApplicationContext().MODE_PRIVATE);
        Map<String, Object> conversionData = (Map<String, Object>) appSharedPreferences.getAll();
        TextView conversionDataText = findViewById(R.id.conversionDataText);
        if (conversionData != null) {
            conversionDataText.setText(conversionData.toString());
        }
    }

}
