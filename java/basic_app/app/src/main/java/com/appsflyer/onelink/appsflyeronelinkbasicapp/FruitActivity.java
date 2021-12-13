package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.appsflyer.CreateOneLinkHttpTask;
import com.appsflyer.share.LinkGenerator;
import com.appsflyer.share.ShareInviteHelper;
import com.google.gson.Gson;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import static com.appsflyer.onelink.appsflyeronelinkbasicapp.AppsflyerBasicApp.LOG_TAG;

public abstract class FruitActivity extends AppCompatActivity {
    TextView dlParametersText;
    TextView dlTitleText;
    TextView goToConversionDataText;
    String fruitName;
    TextView fruitAmount;
    Map<String,Object> deepLinkParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        Button sharedInvitesBtn = (Button)findViewById(R.id.shareinvitesbtn);
        sharedInvitesBtn.setOnClickListener(v -> {
            copyShareInviteLink();
        });
        deepLinkParameters = (Map<String, Object>) getIntent().getSerializableExtra(AppsflyerBasicApp.DL_ATTRS);
    }

    protected abstract int getLayoutResourceId();

    protected void setStaticAttributes(String fruitName) {
        try {
            String dlParamsId = fruitName.concat("_deeplinkparams");
            String dlTitleId = fruitName.concat("_deeplinktitle");
            String conversionDataBtnId = fruitName.concat("_getconversiondata");
            String fruitAmount = fruitName.concat("_fruitAmount");
            this.dlParametersText = findViewById(getResources().getIdentifier(dlParamsId, "id", getPackageName()));
            this.dlTitleText = findViewById(getResources().getIdentifier(dlTitleId, "id", getPackageName()));
            this.goToConversionDataText = findViewById(getResources().getIdentifier(conversionDataBtnId, "id", getPackageName()));
            this.fruitName = fruitName;
            this.fruitAmount = findViewById(getResources().getIdentifier(fruitAmount, "id", getPackageName()));
        }
        catch (Exception e){
            Log.d(LOG_TAG, "Error getting TextViews for " + fruitName + " Activity");
        }
        goToConversionDataText.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ConversionDataActivity.class);
            startActivity(intent);
        });
    }
    protected void showFruitAmount() {
        String fruitAmount;
        if (deepLinkParameters != null) {
            if (deepLinkParameters.containsKey("deep_link_value") && deepLinkParameters.containsKey("deep_link_sub1")) {
                fruitAmount = deepLinkParameters.get("deep_link_sub1").toString();
            } else if (deepLinkParameters.containsKey("fruit_name") && deepLinkParameters.containsKey("fruit_amount")) {
                fruitAmount = deepLinkParameters.get("fruit_amount").toString();
            } else {
                Log.d(LOG_TAG, "deep_link_sub1/fruit amount not found");
                return;
            }
            Log.d(LOG_TAG, "deep_link_sub1/fruit amount found and is " + fruitAmount);
            if (TextUtils.isDigitsOnly(fruitAmount)) {
                this.fruitAmount.setText(fruitAmount);
            } else {
                Log.d(LOG_TAG, "Fruit amount is not a valid number");
            }
        }
    }
    protected void showDeepLinkData() {

        if (deepLinkParameters != null) {
            dlParametersText.setMovementMethod(new ScrollingMovementMethod());
            dlParametersText.setText(mapToSortedString(deepLinkParameters));
        }
        else {
            dlTitleText.setText("No Deep Linking Happened");
        }
    }
    protected void copyShareInviteLink(){
        LinkGenerator linkGenerator = ShareInviteHelper.generateInviteUrl(getApplicationContext());
        linkGenerator.addParameter("deep_link_value", this.fruitName);
        linkGenerator.addParameter("deep_link_sub1", "user_referrer");
        linkGenerator.setCampaign("share_invite");

        Log.d(LOG_TAG, "Link params:" + linkGenerator.getUserParams().toString());
        CreateOneLinkHttpTask.ResponseListener listener = new CreateOneLinkHttpTask.ResponseListener() {
            @Override
            public void onResponse(String s) {
                Log.d(LOG_TAG, "Share invite link: " + s);
                //Copy the share invite link to clipboard and indicate it with a toast
                runOnUiThread(() -> {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Share invite link", s);
                    clipboard.setPrimaryClip(clip);
                    Toast toast = Toast.makeText(getApplicationContext(), "Link copied to clipboard", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 20);
                    toast.show();
                });
            }

            @Override
            public void onResponseError(String s) {
                Log.d(LOG_TAG, "onResponseError called");
            }
        };
        linkGenerator.generateLink(getApplicationContext(), listener);
    }

    public String mapToSortedString(Map<String, Object> map){
        if (map == null){
            return "Data not available at the moment";
        }
        String result = "";
        SortedSet<String> keys = new TreeSet<>(map.keySet());
        Object value;
        for (String key: keys){
            value = map.get(key);
            if (value == null) {
                value = "null";
            }
            result += String.format("%s : %s\n", key, value);
        }
        return result;
    }
}
