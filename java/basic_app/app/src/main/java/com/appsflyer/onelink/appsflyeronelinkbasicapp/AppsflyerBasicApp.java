package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.onelink.appsflyeronelinkbasicapp.AppsFlyerConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AppsflyerBasicApp extends Application {
    public static final String LOG_TAG = "AppsFlyerFeedMeApp";
    public static final String DL_ATTRS = "dl_attrs";
    @Override
    public void onCreate() {
        super.onCreate();
        //noinspection SpellCheckingInspection
        String afDevKey = AppsFlyerConstants.afDevKey;
        AppsFlyerLib appsflyer = AppsFlyerLib.getInstance();
        appsflyer.setMinTimeBetweenSessions(0);
        appsflyer.init(afDevKey, null, this);
        appsflyer.startTracking(this, afDevKey);
        appsflyer.setDebugLog(true);

        AppsFlyerLib.getInstance().registerConversionListener(this, new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionData) {
                for (String attrName : conversionData.keySet())
                    Log.d(LOG_TAG, "Conversion attribute: " + attrName + " = " + conversionData.get(attrName));
                //TODO - remove this
                String status = Objects.requireNonNull(conversionData.get("af_status")).toString();
                if(status.equals("Non-organic")){
                    if( Objects.requireNonNull(conversionData.get("is_first_launch")).toString().equals("true")){
                        Log.d(LOG_TAG,"Conversion: First Launch");
                        if (conversionData.containsKey("fruit_name")){
                            Log.d(LOG_TAG,"Conversion: This is deferred deep linking.");
                            //  TODO SDK in future versions - match the input types
                            Map<String,String> newMap = new HashMap<>();
                            for (Map.Entry<String, Object> entry : conversionData.entrySet()) {
                                newMap.put(entry.getKey(), String.valueOf(entry.getValue()));
                            }
                            onAppOpenAttribution(newMap);
                        }
                    } else {
                        Log.d(LOG_TAG,"Conversion: Not First Launch");
                    }
                } else {
                    Log.d(LOG_TAG,"Conversion: This is an organic install.");
                }
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                Log.d(LOG_TAG, "error getting conversion data: " + errorMessage);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> attributionData) {
                if (!attributionData.containsKey("is_first_launch"))
                    Log.d(LOG_TAG, "onAppOpenAttribution: This is NOT deferred deep linking");
                for (String attrName : attributionData.keySet()) {
                    String deepLinkAttrStr = attrName + " = " + attributionData.get(attrName);
                    Log.d(LOG_TAG, "Deeplink attribute: " + deepLinkAttrStr);
                }
                Log.d(LOG_TAG, "onAppOpenAttribution: Deep linking into " + attributionData.get("fruit_name"));
                goToFruit(attributionData.get("fruit_name"), attributionData);
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d(LOG_TAG, "error onAttributionFailure : " + errorMessage);
            }
        });
    }

    private void goToFruit(String fruitName, Map<String, String> dlData) {
        String fruitClassName = fruitName.concat("Activity");
        try {
            Class fruitClass = Class.forName(this.getPackageName().concat(".").concat(fruitClassName));
            Log.d(LOG_TAG, "Looking for class " + fruitClass);
            Intent intent = new Intent(getApplicationContext(), fruitClass);
            if (dlData != null) {
                // Map is casted HashMap since it is easier to pass serializable data to an intent
                HashMap<String, String> copy = new HashMap<String, String>(dlData);
                intent.putExtra(DL_ATTRS, copy);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            Log.d(LOG_TAG, "Deep linking failed looking for " + fruitName);
            e.printStackTrace();
        }
    }

}