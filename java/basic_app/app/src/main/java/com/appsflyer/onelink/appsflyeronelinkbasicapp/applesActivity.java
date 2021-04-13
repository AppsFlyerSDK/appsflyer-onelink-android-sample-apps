package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.appsflyer.deeplink.DeepLink;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.appsflyer.onelink.appsflyeronelinkbasicapp.AppsflyerBasicApp.LOG_TAG;

public class applesActivity extends AppCompatActivity {
    Utilities utilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apples);

        Intent intent = getIntent();
        Gson json = new Gson();
        DeepLink dlDataJ = json.fromJson(intent.getStringExtra(AppsflyerBasicApp.DL_ATTRS), DeepLink.class);
        Map dlData = json.fromJson(String.valueOf(dlDataJ), Map.class);

        if (dlData != null) {
            MapToListViewAdapter adapter = new MapToListViewAdapter(dlData);
            ListView dlAttrsList = findViewById(R.id.apples_deeplinkparamslist);
            dlAttrsList.setAdapter(adapter);
        } else {
            TextView dlTitleText = findViewById(R.id.apples_deeplinktitle);
            dlTitleText.setText("No Deep Linking Happened");
        }

    }

    public void goToConversionData(View view) {
        utilities = new Utilities();
        //TODO - move redirection to the Utilities class
        Utilities.goToConversionData(view);
        //TODO - add log actions
        Intent intent = new Intent(getApplicationContext(), conversionDataActivity.class);
        startActivity(intent);
    }


}