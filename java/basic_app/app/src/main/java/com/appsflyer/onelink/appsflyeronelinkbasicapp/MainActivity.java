package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;
import com.appsflyer.AppsFlyerConsent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.appsflyer.onelink.appsflyeronelinkbasicapp.AppsflyerBasicApp.LOG_TAG;

public class MainActivity extends AppCompatActivity {

    private static final String afDevKey = AppsFlyerConstants.afDevKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        AppsFlyerConversionListener conversionListener = new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionDataMap) {
                for (String attrName : conversionDataMap.keySet())
                    Log.d(LOG_TAG, "Conversion attribute: " + attrName + " = " + conversionDataMap.get(attrName));
                String status = Objects.requireNonNull(conversionDataMap.get("af_status")).toString();
                if (status.equals("Non-organic")) {
                    if (Objects.requireNonNull(conversionDataMap.get("is_first_launch")).toString().equals("true")) {
                        Log.d(LOG_TAG, "Conversion: First Launch");
                    }
                } else {
                    Log.d(LOG_TAG, "Conversion: Not First Launch");
                }
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                Log.d(LOG_TAG, "error getting conversion data: " + errorMessage);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> attributionData) {
                Log.d(LOG_TAG, "onAppOpenAttribution: This is fake call.");
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d(LOG_TAG, "error onAttributionFailure : " + errorMessage);
            }
        };

        AppsFlyerLib.getInstance().init(afDevKey, conversionListener, this);
        AppsFlyerLib.getInstance().start(this);

        // Send in-app event
        Map<String, Object> eventValues = new HashMap<>();
        eventValues.put(AFInAppEventParameterName.CONTENT_ID, "myprod/1234");
        eventValues.put(AFInAppEventParameterName.CONTENT, "https://test_canonical_url");
        eventValues.put(AFInAppEventParameterName.CITY, "test city");
        eventValues.put(AFInAppEventParameterName.REGION, "test_state");
        eventValues.put(AFInAppEventParameterName.COUNTRY, "test_country");
        eventValues.put(AFInAppEventParameterName.LATITUDE, -151.67);
        eventValues.put(AFInAppEventParameterName.LONGITUDE, -124.0);
        eventValues.put(AFInAppEventParameterName.PRICE, 10.0);
        eventValues.put(AFInAppEventParameterName.CURRENCY, "USD");
        eventValues.put(AFInAppEventParameterName.QUANTITY, 1.5);
        eventValues.put(AFInAppEventParameterName.ORDER_ID, "test_sku");
        eventValues.put(AFInAppEventParameterName.COUPON_CODE, "Coupon Code");
        eventValues.put(AFInAppEventParameterName.DESCRIPTION, "Customer added item to cart");
        eventValues.put(AFInAppEventParameterName.REVENUE, 1.5);
        eventValues.put(AFInAppEventParameterName.SEARCH_STRING, "Test Search query");

        AppsFlyerLib.getInstance().logEvent(
                getApplicationContext(),
                AFInAppEventType.ADD_TO_CART,
                eventValues,
                new AppsFlyerRequestListener() {
                    @Override
                    public void onSuccess() {
                        Log.d("AppsFlyer", "***** Successfully logged the inapp event *****");
                    }

                    @Override
                    public void onError(int errorCode, String errorDesc) {
                        Log.e("AppsFlyer", "Event failed to be sent:\n" +
                                "Error code: " + errorCode + "\n" +
                                "Error description: " + errorDesc);
                    }
                });

        // Example for an EEA resident who has denied both ad personalization and data usage consent
        AppsFlyerConsent gdprUserConsent = AppsFlyerConsent.forGDPRUser(true, true);
        AppsFlyerLib.getInstance().setConsentData(gdprUserConsent);

        String MY_CUID = "replai";
        AppsFlyerLib.getInstance().setCustomerUserId(MY_CUID);
    }

    public void goToApples(View view) {
        goToFruit("Apples");
    }

    public void goToBananas(View view) {
        goToFruit("Bananas");
    }

    public void goToPeaches(View view) {
        goToFruit("Peaches");
    }

    private void goToFruit(String fruitName) {
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