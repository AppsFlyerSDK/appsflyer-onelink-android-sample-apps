package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLOutput;
import java.util.Map;

public class conversionDataActivity extends AppCompatActivity {
    static SharedPreferences appSharedPreferences;
    private ListView conversionDataListView;
    MapToListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion_data);
        displayConversionData();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //TODO - Get the listener to work or get rid of it
        SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                System.out.println("OnSharedPreferenceChangeListener worked");
                Map<String, Object> conversionData = (Map<String, Object>) sharedPreferences.getAll();
                displayConversionData();
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    public void displayConversionData(){
        appSharedPreferences = this.getSharedPreferences("CONVERSIONDATA", getApplicationContext().MODE_PRIVATE);
        Map<String, Object> conversionData = (Map<String, Object>) appSharedPreferences.getAll();
        conversionDataListView = findViewById(R.id.conversionDataListView);
        MapToListViewAdapter adapter = new MapToListViewAdapter(conversionData);
        conversionDataListView.setAdapter(adapter);
    }
}
