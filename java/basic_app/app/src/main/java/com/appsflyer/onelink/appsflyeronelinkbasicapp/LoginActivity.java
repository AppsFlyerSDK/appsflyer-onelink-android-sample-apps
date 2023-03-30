package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import static com.appsflyer.onelink.appsflyeronelinkbasicapp.AppsflyerBasicApp.DL_ATTRS;
import static com.appsflyer.onelink.appsflyeronelinkbasicapp.AppsflyerBasicApp.LOG_TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;



public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
            myEdit = sharedPreferences.edit();
        } catch (Exception e) {
            Log.d(LOG_TAG, "Shared preferences not present.");
        }

        loginBtn = (Button)findViewById(R.id.login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markRegistered();
                finish();
            }
        });
    }

    private void markRegistered() {
        Log.d(LOG_TAG, "Registering user");
        myEdit.putString("userRegistered", "true");
        myEdit.apply();
    }
}