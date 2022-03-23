package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import static com.appsflyer.onelink.appsflyeronelinkbasicapp.AppsflyerBasicApp.LOG_TAG;

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

    protected void showFruitAmount(){
        Gson json = new Gson();
    }

    protected void showDlData() {
        dlTitleText.setText("No Deep Linking Happened");
    }
}
