package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import com.google.gson.Gson;
import org.json.JSONObject;
import androidx.annotation.NonNull;
import com.appsflyer.AppsFlyerLib;

import java.util.Map;
import java.util.Objects;

import com.appsflyer.share.deeplink.DeepLink;
import com.appsflyer.share.deeplink.DeepLinkListener;
import com.appsflyer.share.deeplink.DeepLinkResult;

public class AppsflyerBasicApp extends Application {
    public static final String LOG_TAG = "AppsFlyerOneLinkSimApp";
    public static final String DL_ATTRS = "dl_attrs";
    Map<String, Object> conversionData = null;

    public void onCreate() {
        super.onCreate();
        AppsFlyerLib.getInstance().init("DzWhTw4F78vGTeTuYAucoF", null, this);

        AppsFlyerLib.getInstance().setDebugLog(true);

        // Deep linking: subscribe after init() and before start()
        AppsFlyerLib.getInstance().subscribeForDeepLink(new DeepLinkListener() {
            @Override
            public void onDeepLinking(@NonNull DeepLinkResult deepLinkResult) {
                DeepLinkResult.Status dlStatus = deepLinkResult.getStatus();
                if (dlStatus == DeepLinkResult.Status.NOT_FOUND) {
                    Log.d(LOG_TAG, "Deep link not found");
                    return;
                } else if (dlStatus == DeepLinkResult.Status.ERROR) {
                    Log.d(LOG_TAG, "Deep link error: " + deepLinkResult.getError());
                    return;
                }
                DeepLink deepLinkObj = deepLinkResult.getDeepLink();
                Log.d(LOG_TAG, "Deep link found: " + deepLinkObj);
                Log.d(LOG_TAG, Boolean.TRUE.equals(deepLinkObj.isDeferred())
                        ? "This is a deferred deep link" : "This is a direct deep link");

                String fruitName = deepLinkObj.getDeepLinkValue();
                if (fruitName == null || fruitName.isEmpty()) {
                    Log.d(LOG_TAG, "deep_link_value is missing");
                    return;
                }
                goToFruit(fruitName, deepLinkObj);
            }
        });


        AppsFlyerLib.getInstance().registerSessionReadyListener(() -> {AppsFlyerLib.getInstance().start();
        });
    }

    private void goToFruit(String fruitName, DeepLink deepLinkObj) {
        String fruitClassName = fruitName.substring(0, 1).toUpperCase()
                + fruitName.substring(1) + "Activity";
        try {
            Class<?> fruitClass = Class.forName(
                    AppsflyerBasicApp.class.getPackage().getName() + "." + fruitClassName);
            Intent intent = new Intent(getApplicationContext(), fruitClass);
            intent.putExtra(DL_ATTRS, deepLinkObj.toString());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            Log.d(LOG_TAG, "Deep linking failed looking for " + fruitName);
        }
    }

}
