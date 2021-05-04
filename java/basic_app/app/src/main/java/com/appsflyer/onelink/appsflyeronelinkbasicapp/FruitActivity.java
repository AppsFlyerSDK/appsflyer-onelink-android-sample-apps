package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.appsflyer.deeplink.DeepLink;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import static com.appsflyer.onelink.appsflyeronelinkbasicapp.AppsflyerBasicApp.LOG_TAG;

public abstract class FruitActivity extends AppCompatActivity {
    TextView dlAttrs;
    TextView dlTitleText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
    }

    protected abstract int getLayoutResourceId();

    protected void setViewVariables(String fruitName) {
        if (fruitName.equals("apples")) {
            dlAttrs = findViewById(R.id.apples_deeplinkparams);
            dlTitleText = findViewById(R.id.apples_deeplinktitle);
        } else if (fruitName.equals("bananas")) {
            dlAttrs = findViewById(R.id.bananas_deeplinkparams);
            dlTitleText = findViewById(R.id.bananas_deeplinktitle);
        } else if (fruitName.equals("peaches")) {
            dlAttrs = findViewById(R.id.peaches_deeplinkparams);
            dlTitleText = findViewById(R.id.peaches_deeplinktitle);
        } else {
            Log.d(LOG_TAG, "Fruit activity: fruitName is invalid");
        }
    }

    protected void showDlData() {
        Intent intent = getIntent();
        Gson json = new Gson();
        DeepLink dlData = json.fromJson(intent.getStringExtra(AppsflyerBasicApp.DL_ATTRS), DeepLink.class);

        if (dlData != null) {
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(dlData.toString());
                dlAttrs.setMovementMethod(new ScrollingMovementMethod());
                dlAttrs.setText(jsonObject.toString(4).replaceAll("\\\\", ""));// 4 is no of spaces for indent
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
                dlTitleText.setText("No Deep Linking Happened");
        }

    }

        public void goToConversionData(View view){
            Intent intent = new Intent(getApplicationContext(), conversionDataActivity.class);
            startActivity(intent);
        }

    }
