package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;                                 // new
import androidx.appcompat.app.AppCompatActivity;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.share.AFInAppEventType;                        // new
import com.appsflyer.share.AFInAppEventParameterName;               // new
import com.appsflyer.share.attribution.AppsFlyerRequestListener;    // new
import java.util.HashMap;                                           // new
import java.util.Map;                                               // new

import static com.appsflyer.onelink.appsflyeronelinkbasicapp.AppsflyerBasicApp.LOG_TAG;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppsFlyerLib.getInstance().collectDataFromLauncherActivity(this);
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
        // Send in-app event
        Map<String, Object> eventValues = new HashMap<>();
        eventValues.put(AFInAppEventParameterName.CONTENT_ID, fruitName);
        eventValues.put(AFInAppEventParameterName.CONTENT_TYPE, "fruit");

        AppsFlyerLib.getInstance().logEvent(getApplicationContext(),
                AFInAppEventType.CONTENT_VIEW, eventValues,
                new AppsFlyerRequestListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(LOG_TAG, "Event sent successfully");
                    }
                    @Override
                    public void onError(int code, @NonNull String desc) {
                        Log.d(LOG_TAG, "Event failed: " + code + " " + desc);
                    }
                });
        String fruitClassName = fruitName.concat("Activity");
        try {
            Class fruitClass = Class.forName(MainActivity.class.getPackage().getName().concat(".").concat(fruitClassName));
            Log.d(LOG_TAG, "Looking for class " + fruitClass);
            Intent intent = new Intent(getApplicationContext(), fruitClass);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            Log.d(LOG_TAG, "Failed to start activity for " + fruitName);
            e.printStackTrace();
        }
    }
}