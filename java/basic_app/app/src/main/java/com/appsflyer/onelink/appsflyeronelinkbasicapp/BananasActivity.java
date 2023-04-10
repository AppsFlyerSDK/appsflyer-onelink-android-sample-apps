package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import androidx.annotation.NonNull;

import com.appsflyer.AppsFlyerLib;
import com.appsflyer.AFInAppEventType; // Predefined event names
import com.appsflyer.AFInAppEventParameterName; // Predefined parameter names
import com.appsflyer.attribution.AppsFlyerRequestListener;

import static com.appsflyer.onelink.appsflyeronelinkbasicapp.AppsflyerBasicApp.LOG_TAG;


public class BananasActivity extends FruitActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStaticAttributes("bananas");
        showFruitAmount();
        showDlData();
        sendEventAsync();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_bananas;
    }

    protected void sendEventAsync() {
        Map<String, Object> eventValues = new HashMap<String, Object>();
        eventValues.put(AFInAppEventParameterName.PRICE, 1234.56);
        eventValues.put(AFInAppEventParameterName.CURRENCY, "USD");
        eventValues.put(AFInAppEventParameterName.CONTENT_ID, this.fruitName);
        eventValues.put(AFInAppEventParameterName.CONTENT_TYPE, "fruits");

        AppsFlyerLib.getInstance().logEvent(getApplicationContext(),
                AFInAppEventType.CONTENT_VIEW,
                eventValues,
                new AppsFlyerRequestListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(LOG_TAG, "Event sent successfully");
                    }
                    @Override
                    public void onError(int i, @NonNull String s) {
                        Log.d(LOG_TAG, "Event failed to be sent:\n" +
                                "Error code: " + i + "\n"
                                + "Error description: " + s);
                    }
                });
    }
}
