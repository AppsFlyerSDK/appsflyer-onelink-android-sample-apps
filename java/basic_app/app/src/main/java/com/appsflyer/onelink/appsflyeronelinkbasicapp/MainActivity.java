package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.appsflyer.onelink.appsflyeronelinkbasicapp.AppsflyerBasicApp.LOG_TAG;

import com.appsflyer.onelink.appsflyeronelinkbasicapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.ServerRequestGetLATD;
import io.branch.referral.util.BRANCH_STANDARD_EVENT;
import io.branch.referral.util.BranchContentSchema;
import io.branch.referral.util.BranchEvent;
import io.branch.referral.util.ContentMetadata;
import io.branch.referral.util.CurrencyType;
import io.branch.referral.util.LinkProperties;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Branch.sessionBuilder(this).withCallback(new Branch.BranchUniversalReferralInitListener() {
            @Override
            public void onInitFinished(BranchUniversalObject branchUniversalObject, LinkProperties linkProperties, BranchError error) {
                if (error != null) {
                    Log.e("BranchSDK_Tester", "branch init failed. Caused by -" + error.getMessage());
                } else {
                    Log.i("BranchSDK_Tester", "branch init complete!");
                    if (branchUniversalObject != null) {
                        Log.i("BranchSDK_Tester", "title " + branchUniversalObject.getTitle());
                        Log.i("BranchSDK_Tester", "CanonicalIdentifier " + branchUniversalObject.getCanonicalIdentifier());
                        Log.i("BranchSDK_Tester", "metadata " + branchUniversalObject.getContentMetadata().convertToJson());
                        JSONObject sessionParams = branchUniversalObject.getContentMetadata().convertToJson();
                        try {
                            goToFruit(sessionParams.getString("fruit_name"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        Log.i("BranchSDK_Tester", "@@@@ branchUniversalObject came back null");
                    }

                    if (linkProperties != null) {
                        Log.i("BranchSDK_Tester", "Channel " + linkProperties.getChannel());
                        Log.i("BranchSDK_Tester", "control params " + linkProperties.getControlParams());
                    } else {
                        Log.i("BranchSDK_Tester", "@@@@ linkProperties came back null");
                    }
                }
            }
        }).withData(this.getIntent().getData()).init();
        // init the LATD call from inside the session initialization callback
        Branch.getInstance().getLastAttributedTouchData(
                new ServerRequestGetLATD.BranchLastAttributedTouchDataListener() {
                    @Override
                    public void onDataFetched(JSONObject jsonObject, BranchError error) {
                        // read the data from the jsonObject
                        Log.i("BranchSDK_Tester", "@@@@ 1234 @@@@" + jsonObject.toString());
                    }
                }, 7);


        //send inapp event

        // Create a BranchUniversalObject with your content data
        BranchUniversalObject buo = new BranchUniversalObject()
                .setCanonicalIdentifier("myprod/1234")
                .setCanonicalUrl("https://test_canonical_url")
                .setContentMetadata(
                        new ContentMetadata()
                                .setAddress("Street_Name", "test city", "test_state", "test_country", "test_postal_code")
                                .setLocation(-151.67, -124.0)
                                .setPrice(10.0, CurrencyType.USD)
                                .setQuantity(1.5)
                                .setSku("test_sku")
                                .setContentSchema(BranchContentSchema.COMMERCE_PRODUCT));

//  Create an event
        new BranchEvent(BRANCH_STANDARD_EVENT.ADD_TO_CART)
                .setCoupon("Coupon Code")
                .setCurrency(CurrencyType.USD)
                .setDescription("Customer added item to cart")
                .setRevenue(1.5)
                .setSearchQuery("Test Search query")
                .addContentItems(buo) // Add a BranchUniversalObject to the event (cannot be empty)
                .logEvent(MainActivity.this, new BranchEvent.BranchLogEventCallback() {
                    @Override
                    public void onSuccess(int responseCode) {
                        Log.d("BranchSDK_Tester", "sent the inapp");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d("BranchSDK_Tester", e.toString());
                    }
                });

        // Example for an EEA resident who has denied both ad personalization and data usage consent
        Branch.getInstance().setDMAParamsForEEA(true,true,true);

        String MY_CUID = "replai";
        Branch.getInstance().setIdentity(MY_CUID, new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(@Nullable JSONObject referringParams, @Nullable BranchError error) {
                Log.i("BranchSDK_Tester", "install params = " + referringParams.toString());
            }
        });
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