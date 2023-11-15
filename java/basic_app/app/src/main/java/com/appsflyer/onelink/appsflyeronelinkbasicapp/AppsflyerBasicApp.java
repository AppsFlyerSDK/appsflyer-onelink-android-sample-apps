package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import com.appsflyer.AppsFlyerLib;
import com.appsflyer.deeplink.DeepLink;
import com.appsflyer.deeplink.DeepLinkListener;
import com.appsflyer.deeplink.DeepLinkResult;
import com.appsflyer.AppsFlyerConversionListener;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import com.google.gson.Gson;
import org.json.JSONObject;
import androidx.annotation.NonNull;

import java.util.Map;
import java.util.Objects;

public class AppsflyerBasicApp extends Application {
    public static final String LOG_TAG = "AppsFlyerOneLinkSimApp";
    public static final String DL_ATTRS = "dl_attrs";
    Map<String, Object> conversionData = null;

    // This boolean flag signals between the UDL and GCD callbacks that this deep_link was
    // already processed, and the callback functionality for deep linking can be skipped.
    // When GCD or UDL finds this flag true it MUST set it to false before skipping.
    boolean deferred_deep_link_processed_flag = false;

    @Override
    public void onCreate() {
        super.onCreate();
        String afDevKey = AppsFlyerConstants.afDevKey;
        AppsFlyerLib appsflyer = AppsFlyerLib.getInstance();
        // Make sure you remove the following line when building to production
        appsflyer.setDebugLog(true);
        appsflyer.setMinTimeBetweenSessions(0);
        //set the OneLink template id for share invite links
        AppsFlyerLib.getInstance().setAppInviteOneLink("H5hv");

        appsflyer.subscribeForDeepLink(new DeepLinkListener(){
            @Override
            public void onDeepLinking(@NonNull DeepLinkResult deepLinkResult) {
                DeepLinkResult.Status dlStatus = deepLinkResult.getStatus();
                if (dlStatus == DeepLinkResult.Status.FOUND) {
                    Log.d(LOG_TAG, "Deep link found");
                } else if (dlStatus == DeepLinkResult.Status.NOT_FOUND) {
                    Log.d(LOG_TAG, "Deep link not found");
                    return;
                } else {
                    // dlStatus == DeepLinkResult.Status.ERROR
                    DeepLinkResult.Error dlError = deepLinkResult.getError();
                    Log.d(LOG_TAG, "There was an error getting Deep Link data: " + dlError.toString());
                    return;
                }
                DeepLink deepLinkObj = deepLinkResult.getDeepLink();
                try {
                    Log.d(LOG_TAG, "The DeepLink data is: " + deepLinkObj.toString());
                } catch (Exception e) {
                    Log.d(LOG_TAG, "DeepLink data came back null");
                    return;
                }
                // An example for using is_deferred
                if (deepLinkObj.isDeferred()) {
                    Log.d(LOG_TAG, "This is a deferred deep link");
                    if (deferred_deep_link_processed_flag == true) {
                        Log.d(LOG_TAG, "Deferred deep link was already processed by GCD. This iteration can be skipped.");
                        deferred_deep_link_processed_flag = false;
                        return;
                    }
                } else {
                    Log.d(LOG_TAG, "This is a direct deep link");
                }
                // An example for getting deep_link_value
                String fruitName = "";
                try {
                    fruitName = deepLinkObj.getDeepLinkValue();

                    String referrerId = "";
                    JSONObject dlData = deepLinkObj.getClickEvent();

                    // ** Next if statement is optional **
                    // Our sample app's user-invite carries the referrerID in deep_link_sub2
                    // See the user-invite section in FruitActivity.java
                    if (dlData.has("deep_link_sub2")){
                        referrerId = deepLinkObj.getStringValue("deep_link_sub2");
                        Log.d(LOG_TAG, "The referrerID is: " + referrerId);
                    }  else {
                        Log.d(LOG_TAG, "deep_link_sub2/Referrer ID not found");
                    }

                    if (fruitName == null || fruitName.equals("")){
                        Log.d(LOG_TAG, "deep_link_value returned null");
                        fruitName = deepLinkObj.getStringValue("fruit_name");
                        if (fruitName == null || fruitName.equals("")) {
                            Log.d(LOG_TAG, "could not find fruit name");
                            return;
                        }
                        Log.d(LOG_TAG, "fruit_name is " + fruitName + ". This is an old link");
                    }
                    Log.d(LOG_TAG, "The DeepLink will route to: " + fruitName);
                    // This marks to GCD that UDL already processed this deep link.
                    // It is marked to both DL and DDL, but GCD is relevant only for DDL
                    deferred_deep_link_processed_flag = true;
                } catch (Exception e) {
                    Log.d(LOG_TAG, "There's been an error: " + e.toString());
                    return;
                }
                goToFruit(fruitName, deepLinkObj);
            }
        });

        AppsFlyerConversionListener conversionListener =  new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionDataMap) {
                for (String attrName : conversionDataMap.keySet())
                    Log.d(LOG_TAG, "Conversion attribute: " + attrName + " = " + conversionDataMap.get(attrName));
                String status = Objects.requireNonNull(conversionDataMap.get("af_status")).toString();
                if(status.equals("Non-organic")){
                    if( Objects.requireNonNull(conversionDataMap.get("is_first_launch")).toString().equals("true")){
                        Log.d(LOG_TAG,"Conversion: First Launch");
                        //Deferred deep link in case of a legacy link
                        if (deferred_deep_link_processed_flag == true) {
                            Log.d(LOG_TAG, "Deferred deep link was already processed by UDL. The DDL processing in GCD can be skipped.");
                            deferred_deep_link_processed_flag = false;
                        } else {
                            deferred_deep_link_processed_flag = true;
                            if(conversionDataMap.containsKey("fruit_name")) {
                                conversionDataMap.put("deep_link_value", conversionDataMap.get("fruit_name"));
                            }
                            String fruitNameStr = (String) conversionDataMap.get("deep_link_value");
                            DeepLink deepLinkData = mapToDeepLinkObject(conversionDataMap);
                            goToFruit(fruitNameStr, deepLinkData);
                        }
                    } else {
                        Log.d(LOG_TAG,"Conversion: Not First Launch");
                    }
                } else {
                    Log.d(LOG_TAG, "Conversion: This is an organic install.");
                }
                conversionData = conversionDataMap;
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
        appsflyer.init(afDevKey, conversionListener, this);
        appsflyer.start(this);
    }

    private void goToFruit(String fruitName, DeepLink dlData) {
        String fruitClassName = (fruitName.substring(0, 1).toUpperCase() + fruitName.substring(1)).concat("Activity");
        try {
            Class fruitClass = Class.forName(this.getPackageName().concat(".").concat(fruitClassName));
            Log.d(LOG_TAG, "Looking for class " + fruitClass);
            Intent intent = new Intent(getApplicationContext(), fruitClass);
            if (dlData != null) {
                // TODO - make DeepLink Parcelable
                String objToStr = new Gson().toJson(dlData);
                intent.putExtra(DL_ATTRS, objToStr);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            Log.d(LOG_TAG, "Deep linking failed looking for " + fruitName);
            e.printStackTrace();
        }
    }

    public DeepLink mapToDeepLinkObject(Map <String, Object> conversionDataMap){
        try {
            String objToStr = new Gson().toJson(conversionDataMap);
            DeepLink deepLink = DeepLink.AFKeystoreWrapper(new JSONObject(objToStr));
            return deepLink;
        }
        catch (org.json.JSONException e ){
            Log.d(LOG_TAG, "Error when converting map to DeepLink object: " + e.toString());
        }
        return null;
    }
}
