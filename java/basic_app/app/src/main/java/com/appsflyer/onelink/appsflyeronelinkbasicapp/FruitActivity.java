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
            try {
                String dlParamsId = fruitName.concat("_deeplinkparams");
                String dlTitleId = fruitName.concat("_deeplinktitle");
                dlAttrs = (TextView)findViewById(getResources().getIdentifier(dlParamsId, "id", getPackageName()));
                dlTitleText = (TextView)findViewById(getResources().getIdentifier(dlTitleId, "id", getPackageName()));
            }
            catch (Exception e){
                Log.d(LOG_TAG, "******\nError creating the field\n *****");
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
                dlAttrs.setText(jsonObject.toString(4).replaceAll("\\\\", ""));// 4 is num of spaces for indent
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
