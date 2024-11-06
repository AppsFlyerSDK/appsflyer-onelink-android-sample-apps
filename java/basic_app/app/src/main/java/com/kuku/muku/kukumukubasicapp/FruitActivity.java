package com.kuku.muku.kukumukubasicapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kuku.muku.kukumukubasicapp.R;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import static com.kuku.muku.kukumukubasicapp.AppsflyerBasicApp.LOG_TAG;

import java.util.HashMap;

public abstract class FruitActivity extends AppCompatActivity {
    TextView dlAttrs;
    TextView dlTitleText;
    TextView goToConversionDataText;
    String fruitName;
    String fruitAmountStr;
    TextView fruitAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        Button sharedInvitesBtn = (Button)findViewById(R.id.shareinvitesbtn);
    }

    protected abstract int getLayoutResourceId();

    protected void setStaticAttributes(String fruitName) {
        try {
            String dlParamsId = fruitName.concat("_deeplinkparams");
            String dlTitleId = fruitName.concat("_deeplinktitle");
            String conversionDataBtnId = fruitName.concat("_getconversiondata");
            String fruitAmount = fruitName.concat("_fruitAmount");
            this.dlAttrs = (TextView)findViewById(getResources().getIdentifier(dlParamsId, "id", getPackageName()));
            this.dlTitleText = (TextView)findViewById(getResources().getIdentifier(dlTitleId, "id", getPackageName()));
            this.goToConversionDataText = (TextView)findViewById(getResources().getIdentifier(conversionDataBtnId, "id", getPackageName()));
            this.fruitName = fruitName;
            this.fruitAmountStr = "000";
            this.fruitAmount = (TextView)findViewById(getResources().getIdentifier(fruitAmount, "id", getPackageName()));
        }
        catch (Exception e){
            Log.d(LOG_TAG, "Error getting TextViews for " + fruitName + " Activity");
        }
        goToConversionDataText.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ConversionDataActivity.class);
            startActivity(intent);
        });
    }

}
