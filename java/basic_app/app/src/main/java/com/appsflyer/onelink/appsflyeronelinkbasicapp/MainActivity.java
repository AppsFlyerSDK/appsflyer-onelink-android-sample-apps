package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.AndroidException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.appsflyer.onelink.appsflyeronelinkbasicapp.AppsflyerBasicApp.LOG_TAG;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToApples(View view) {
        goToFruit("apples");
    }

    public void goToBananas(View view) {
        goToFruit("bananas");
    }

    public void goToPeaches(View view) {
        goToFruit("peaches");
    }

    private void goToFruit(String fruitName) {
        goToFruit(fruitName, null);
    }

    private void goToFruit(String fruitName, Map<String, String> dlData) {
        String fruitClassName = fruitName.concat("Activity");
        try {
            Class fruitClass = Class.forName(this.getPackageName().concat(".").concat(fruitClassName));
            Log.d(LOG_TAG, "Looking for class " + fruitClass);
            Intent intent = new Intent(getApplicationContext(), fruitClass);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            Log.d(LOG_TAG, "Failed to start activity for " + fruitName);
            e.printStackTrace();
        }
    }

}