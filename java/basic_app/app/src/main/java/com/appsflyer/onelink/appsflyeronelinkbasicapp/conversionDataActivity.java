package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

public class conversionDataActivity extends AppCompatActivity {

    Map<String, Object> conversionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion_data);
        displayConvesionData();
    }

    public void displayConvesionData(){
        //TODO - get conversion data
        TextView conversionDataText = findViewById(R.id.conversionDataText);
        if (conversionData != null) {
            conversionDataText.setText("Conversion data:");
        }
    }
}
