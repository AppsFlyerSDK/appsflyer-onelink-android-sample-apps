package com.appsflyer.onelink.appsflyeronelinkbasicapp

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.appsflyer.deeplink.DeepLink
import com.appsflyer.onelink.appsflyeronelinkbasicapp.AppsflyerBasicApp.Companion.DL_ATTRS
import com.appsflyer.onelink.appsflyeronelinkbasicapp.AppsflyerBasicApp.Companion.LOG_TAG
import com.appsflyer.share.LinkGenerator
import com.appsflyer.share.ShareInviteHelper
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject

abstract class FruitActivity: AppCompatActivity() {

    var dlAttrs: TextView? = null
    var dlTitleText: TextView? = null
    var goToConversionDataText: TextView? = null
    var fruitAmountStr: String? = null
    var fruitAmount: TextView? = null
    protected abstract fun getLayoutResourceId(): Int
    protected abstract val fruitName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())
        val copyShareLinkBtn: Button = findViewById(R.id.shareinvitesbtn) as Button
        copyShareLinkBtn.setOnClickListener({
            copyShareInviteLink()
        })

        setStaticAttributes()
        showFruitAmount()
        showDlData()
    }
    protected open fun setStaticAttributes(){
        try {
            val dlParamsId: String = fruitName + "_deeplinkparams"
            val dlTitleId: String = fruitName + "_deeplinktitle"
            val conversionDataBtnId: String = fruitName + "_getconversiondata"
            val fruitAmount: String = fruitName + "_fruitAmount"
            this.dlAttrs = findViewById(resources.getIdentifier(dlParamsId,"id",packageName))
            this.dlTitleText = findViewById(resources.getIdentifier(dlTitleId, "id", packageName))
            this.fruitAmountStr = "000"
            this.fruitAmount = findViewById(resources.getIdentifier("fruitAmount", "id", packageName))

        }catch (e : Exception){
            Log.d(LOG_TAG,"Error getting TextViews for " + fruitName + " Activity")
        }
//        //Go To Conversion Data button on click listener
//        /* goToConversionDataText.setOnClickListener(v -> {
//            Intent intent = new Intent(getApplicationContext(), ConversionDataActivity.class);
//            startActivity(intent);
//        });*/

    }
    protected open fun showFruitAmount() {
        val json = Gson()
        val dlObject = json.fromJson<DeepLink>(
            intent.getStringExtra(DL_ATTRS),
            DeepLink::class.java
        )
        var fruitAmount: String
        if (dlObject != null) {
            val dlData = dlObject.clickEvent
            fruitAmount = when {
                dlData.has("deep_link_value") && dlData.has("deep_link_sub1") ->
                    dlObject.getStringValue("deep_link_sub1")?:""
                dlData.has("fruit_name") && dlData.has("fruit_amount") ->
                    dlObject.getStringValue("fruit_amount")?:""
                else -> {
                    Log.d(LOG_TAG, "deep_link_sub1/fruit amount not found")
                    return
                }
            }
            if (TextUtils.isDigitsOnly(fruitAmount)) {
                fruitAmountStr = fruitAmount
                this.fruitAmount?.text = fruitAmount
            } else {
                Log.d(LOG_TAG, "Fruit amount is not a valid number")
            }
        }
    }
    fun showDlData() {
            val intent = intent
            val json = Gson()
            val dlData = json.fromJson(
                intent.getStringExtra(DL_ATTRS),
                DeepLink::class.java
            )
            if (dlData != null) {
                val jsonObject: JSONObject
                try {
                    jsonObject = JSONObject(dlData.toString())
                    dlAttrs?.movementMethod = ScrollingMovementMethod()
                    dlAttrs?.text = jsonObject.toString(4)
                        .replace("\\\\".toRegex(), "") // 4 is num of spaces for indent
                    dlTitleText?.text = "Deep Link happened. Parameters:"

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            } else {
                dlTitleText?.text = "No Deep Linking Happened"
            }
        }
    protected open fun copyShareInviteLink() {
        val currentCampaign = "user_invite"
        val currentChannel = "mobile_share"
        val currentReferrerId = "THIS_USER_ID"
        val linkGenerator = ShareInviteHelper.generateInviteUrl(applicationContext)
        linkGenerator.addParameter("deep_link_value", fruitName)
        linkGenerator.addParameter("deep_link_sub1", fruitAmountStr)
        linkGenerator.addParameter("deep_link_sub2", currentReferrerId)
        linkGenerator.campaign = currentCampaign
        linkGenerator.channel = currentChannel
        Log.d(LOG_TAG, "Link params:" + linkGenerator.userParams.toString())
        val listener: LinkGenerator.ResponseListener = object : LinkGenerator.ResponseListener {
            override fun onResponse(s: String) {
                Log.d(LOG_TAG, "Share invite link: $s")
                //Copy the share invite link to clipboard and indicate it with a toast
                runOnUiThread {
                    val clipboard =
                        getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Share invite link", s)
                    clipboard.setPrimaryClip(clip)
                    val toast = Toast.makeText(
                        applicationContext,
                        "Link copied to clipboard",
                        Toast.LENGTH_SHORT
                    )
                    toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 20)
                    toast.show()
                }
                val logInviteMap = HashMap<String, String>()
                logInviteMap["referrerId"] = currentReferrerId
                logInviteMap["campaign"] = currentCampaign
                ShareInviteHelper.logInvite(applicationContext, currentChannel, logInviteMap)
            }

            override fun onResponseError(s: String) {
                Log.d(LOG_TAG, "onResponseError called")
            }
        }
        linkGenerator.generateLink(applicationContext, listener)
    }


}