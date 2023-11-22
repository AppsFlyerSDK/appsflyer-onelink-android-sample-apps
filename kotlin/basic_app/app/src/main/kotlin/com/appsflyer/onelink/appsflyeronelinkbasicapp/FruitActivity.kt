package com.appsflyer.onelink.appsflyeronelinkbasicapp

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.appsflyer.deeplink.DeepLink
import com.appsflyer.share.LinkGenerator
import com.appsflyer.share.ShareInviteHelper
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject

abstract class FruitActivity: AppCompatActivity() {

    var dlAttrs: TextView? = null
    var dlTitleText: TextView? = null
    var goToConversionDataText: TextView? = null
    var fruitName: String? = null
    var fruitAmountStr: String? = null
    var fruitAmount: TextView? = null
    protected abstract fun getLayoutResourceId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId());
    }
    protected open fun setStaticAttributes(fruitName: String?){
        try {
            val dlParamsId: String = fruitName + "_deeplinkparams"
            val dlTitleId: String = fruitName + "_deeplinktitle"
            val conversionDataBtnId: String = fruitName + "_getconversiondata"
            val fruitAmount: String = fruitName + "_fruitAmount"
            this.dlAttrs = findViewById(resources.getIdentifier(dlParamsId, "id", packageName)) as TextView
            this.dlTitleText = findViewById(resources.getIdentifier(dlTitleId, "id", packageName)) as TextView
            this.fruitName = fruitName
            this.fruitAmountStr = "000"
            this.fruitAmount = findViewById(resources.getIdentifier("fruitAmount", "id", packageName)) as TextView


        }catch (e : Exception){
            Log.d("LOG_TAG","Error getting TextViews for " + fruitName + " Activity")
        }
    }
    protected open fun showFruitAmount() {
        val json = Gson()
        val dlObject = json.fromJson<DeepLink>(
            intent.getStringExtra("dl_attrs"),
            // "dl_attrs" supposed to be AppsflyerBasicApp.DL_ATTRS need change after adding the method
            DeepLink::class.java
        )
        var fruitAmount: String
        if (dlObject != null) {
            val dlData = dlObject.clickEvent
            fruitAmount = if (dlData.has("deep_link_value") && dlData.has("deep_link_sub1")) {
                dlObject.getStringValue("deep_link_sub1")!!
            } else if (dlData.has("fruit_name") && dlData.has("fruit_amount")) {
                dlObject.getStringValue("fruit_amount")!!
            } else {
                Log.d(AppsflyerBasicApp.LOG_TAG, "deep_link_sub1/fruit amount not found")
                return
            }
            if (TextUtils.isDigitsOnly(fruitAmount)) {
                fruitAmountStr = fruitAmount
                this.fruitAmount!!.text = fruitAmount
            } else {
                Log.d(AppsflyerBasicApp.LOG_TAG, "Fruit amount is not a valid number")
            }
        }
    }
}