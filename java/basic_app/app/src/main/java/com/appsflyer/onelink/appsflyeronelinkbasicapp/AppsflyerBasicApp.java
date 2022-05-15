package com.appsflyer.onelink.appsflyeronelinkbasicapp;


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

    @Override
    public void onCreate() {
        super.onCreate();
    }

//    private void goToFruit(String fruitName, DeepLink dlData) {
//        String fruitClassName = (fruitName.substring(0, 1).toUpperCase() + fruitName.substring(1)).concat("Activity");
//        try {
//            Class fruitClass = Class.forName(this.getPackageName().concat(".").concat(fruitClassName));
//            Log.d(LOG_TAG, "Looking for class " + fruitClass);
//            Intent intent = new Intent(getApplicationContext(), fruitClass);
//            if (dlData != null) {
//                // TODO - make DeepLink Parcelable
//                String objToStr = new Gson().toJson(dlData);
//                intent.putExtra(DL_ATTRS, objToStr);
//            }
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        } catch (ClassNotFoundException e) {
//            Log.d(LOG_TAG, "Deep linking failed looking for " + fruitName);
//            e.printStackTrace();
//        }
//    }
//
//    public DeepLink mapToDeepLinkObject(Map <String, Object> conversionDataMap){
//        try {
//            String objToStr = new Gson().toJson(conversionDataMap);
//            DeepLink deepLink = DeepLink.AFKeystoreWrapper(new JSONObject(objToStr));
//            return deepLink;
//        }
//        catch (org.json.JSONException e ){
//            Log.d(LOG_TAG, "Error when converting map to DeepLink object: " + e.toString());
//        }
//        return null;
//    }
}
