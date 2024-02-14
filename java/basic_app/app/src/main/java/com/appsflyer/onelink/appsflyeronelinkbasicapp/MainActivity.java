package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.appsflyer.AppsFlyerLib;
import com.google.android.ump.ConsentDebugSettings;
import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.FormError;
import com.google.android.ump.UserMessagingPlatform;


import static com.appsflyer.onelink.appsflyeronelinkbasicapp.AppsflyerBasicApp.LOG_TAG;

public class MainActivity extends AppCompatActivity {

    private ConsentForm consentForm;

    private boolean consentRequired = true;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (consentRequired) {
            Log.d(LOG_TAG, "Consent information required.");
            initConsentCollection();
        }
        else {
            Log.d(LOG_TAG, "Consent information NOT required.");
            AppsFlyerLib.getInstance().start(this);
        }
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

    private void initConsentCollection() {
        ConsentDebugSettings debugSettings = new ConsentDebugSettings.Builder(this)
                .addTestDeviceHashedId("DB37B3529ADD7820A517A8C0EB32D7C8")
                .build();
        ConsentRequestParameters params = new ConsentRequestParameters.Builder()
                .setConsentDebugSettings(debugSettings)
                .build();

        ConsentInformation consentInformation = UserMessagingPlatform.getConsentInformation(this);
        if (consentInformation != null) {
            Log.d(LOG_TAG, "Consent information is NOT null");
            consentInformation.requestConsentInfoUpdate(
                    this,
                    params,
                    new ConsentInformation.OnConsentInfoUpdateSuccessListener() {
                        @Override
                        public void onConsentInfoUpdateSuccess() {
                            Log.d(LOG_TAG, "Consent info update success");
                            if (consentInformation.isConsentFormAvailable()) {
                                Log.d(LOG_TAG, "Consent form available");
                                UserMessagingPlatform.loadConsentForm(
                                        MainActivity.this,
                                        new UserMessagingPlatform.OnConsentFormLoadSuccessListener() {
                                            @Override
                                            public void onConsentFormLoadSuccess(ConsentForm form) {
                                                MainActivity.this.consentForm = form;
                                                if(consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.REQUIRED) {
                                                    Log.d(LOG_TAG, "Consent status is required");
                                                    consentForm.show(MainActivity.this, new ConsentForm.OnConsentFormDismissedListener() {
                                                        @Override
                                                        public void onConsentFormDismissed(FormError formError) {
                                                            // Handle the consent form dismissal.
                                                            if (formError != null) {
                                                                Log.e(LOG_TAG, "Consent form error: " + formError.getMessage());
                                                            } else {
                                                                Log.i(LOG_TAG, "Consent form was dismissed.");
                                                                // Continue with next steps after consent has been handled.
                                                            }
                                                            onConsentCollectionFinished();
                                                        }
                                                    });
                                                } else {
                                                    Log.d(LOG_TAG, "Consent status is NOT required");
                                                    onConsentCollectionFinished();
                                                }
                                            }
                                        },
                                        new UserMessagingPlatform.OnConsentFormLoadFailureListener() {
                                            @Override
                                            public void onConsentFormLoadFailure(FormError formError) {
                                                // Handle the consent form loading error here.
                                                Log.e(LOG_TAG, "Consent form load failure: " + formError.getMessage());
                                                onConsentCollectionFinished();
                                            }
                                        }
                                );
                            } else {
                                Log.d(LOG_TAG, "Consent form NOT available");
                                onConsentCollectionFinished();
                                // Consent form not available, continue with the rest of your logic.
                            }
                        }

                    },
                    new ConsentInformation.OnConsentInfoUpdateFailureListener() {
                        @Override
                        public void onConsentInfoUpdateFailure(FormError formError) {
                            // Handle the consent information update failure.
                            Log.e(LOG_TAG, "Consent information update failed: " + formError.getMessage());
                            onConsentCollectionFinished();
                        }
                    }
            );
        }
        else {
            Log.d(LOG_TAG, "consent info NULL");
            onConsentCollectionFinished();
        }
    }

    private void onConsentCollectionFinished() {
        Log.d(LOG_TAG, "consent connection flow finished");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.d(
                LOG_TAG,
                "key: IABTCF_CmpSdkID value: " + preferences.getInt("IABTCF_CmpSdkID", -1)
        );
        Log.d(
                LOG_TAG,
                "key: IABTCF_CmpSdkVersion value: " + preferences.getInt("IABTCF_CmpSdkVersion", -1)
        );
        Log.d(
                LOG_TAG,
                "key: IABTCF_gdprApplies value: " + preferences.getInt("IABTCF_gdprApplies", -1)
        );
        Log.d(
                LOG_TAG,
                "key: IABTCF_PolicyVersion value: " + preferences.getInt("IABTCF_PolicyVersion", -1)
        );
        Log.d(
                LOG_TAG,
                "key: IABTCF_TCString value: " + preferences.getString("IABTCF_TCString", "")
        );
        AppsFlyerLib.getInstance().start(this);
    }

}