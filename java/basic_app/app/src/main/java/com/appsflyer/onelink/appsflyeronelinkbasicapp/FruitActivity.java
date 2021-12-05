package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.appsflyer.CreateOneLinkHttpTask;
import com.appsflyer.deeplink.DeepLink;
import com.appsflyer.share.LinkGenerator;
import com.appsflyer.share.ShareInviteHelper;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import static com.appsflyer.onelink.appsflyeronelinkbasicapp.AppsflyerBasicApp.LOG_TAG;

public abstract class FruitActivity extends AppCompatActivity {
    TextView dlAttrs;
    TextView dlTitleText;
    TextView goToConversionDataText;
    String fruitName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        Button sharedInvitesBtn = (Button)findViewById(R.id.shareinvitesbtn);
        sharedInvitesBtn.setOnClickListener(v -> {
            copyShareInviteLink();
        });
    }

    protected abstract int getLayoutResourceId();

    protected void setStaticAttributes(String fruitName) {
        try {
            String dlParamsId = fruitName.concat("_deeplinkparams");
            String dlTitleId = fruitName.concat("_deeplinktitle");
            String conversionDataBtnId = fruitName.concat("_getconversiondata");
            this.dlAttrs = (TextView)findViewById(getResources().getIdentifier(dlParamsId, "id", getPackageName()));
            this.dlTitleText = (TextView)findViewById(getResources().getIdentifier(dlTitleId, "id", getPackageName()));
            this.goToConversionDataText = (TextView)findViewById(getResources().getIdentifier(conversionDataBtnId, "id", getPackageName()));
            this.fruitName = fruitName;
        }
        catch (Exception e){
            Log.d(LOG_TAG, "Error getting TextViews for " + fruitName + " Activity");
        }
        goToConversionDataText.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ConversionDataActivity.class);
            startActivity(intent);
        });
    }

    protected void showDlData() {
        Intent intent = getIntent();
        Gson json = new Gson();
        DeepLink dlData = json.fromJson(intent.getStringExtra(AppsflyerBasicApp.DL_ATTRS), DeepLink.class);
        if (dlData != null) {
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(dlData.toString());
                dlAttrs.setMovementMethod(new ScrollingMovementMethod());
                dlAttrs.setText(jsonObject.toString(4).replaceAll("\\\\", ""));// 4 is num of spaces for indent
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            dlTitleText.setText("No Deep Linking Happened");
        }
    }
    protected void copyShareInviteLink(){
        LinkGenerator linkGenerator = ShareInviteHelper.generateInviteUrl(getApplicationContext());
        linkGenerator.addParameter("deep_link_value", this.fruitName);
        linkGenerator.addParameter("deep_link_sub1", "user_referrer");
        linkGenerator.addParameter("af_campaign", "share_invite");

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
}
