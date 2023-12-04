package com.appsflyer.onelink.appsflyeronelinkbasicapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.appsflyer.deeplink.DeepLink
import com.appsflyer.onelink.appsflyeronelinkbasicapp.AppsflyerBasicApp.Companion.DL_ATTRS
import com.appsflyer.onelink.appsflyeronelinkbasicapp.AppsflyerBasicApp.Companion.LOG_TAG
import com.google.gson.Gson

abstract class FruitActivity : AppCompatActivity() {

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
        setStaticAttributes()
        showFruitAmount()
    }
    protected open fun setStaticAttributes() {
        try {
            val dlParamsId: String = fruitName + "_deeplinkparams"
            val dlTitleId: String = fruitName + "_deeplinktitle"
            val conversionDataBtnId: String = fruitName + "_getconversiondata"
            val fruitAmount: String = fruitName + "_fruitAmount"
            this.dlAttrs = findViewById(resources.getIdentifier(dlParamsId, "id", packageName))
            this.dlTitleText = findViewById(resources.getIdentifier(dlTitleId, "id", packageName))
            this.fruitAmountStr = "000"
            this.fruitAmount = findViewById(resources.getIdentifier(fruitAmount, "id", packageName))
            this.goToConversionDataText =
                findViewById(resources.getIdentifier(conversionDataBtnId, "id", packageName))
        } catch (e: Exception) {
            Log.d(LOG_TAG, "Error getting TextViews for " + fruitName + " Activity")
        }
        //Go To Conversion Data button on click listener
        goToConversionDataText?.setOnClickListener {
            val intent = Intent(applicationContext, ConversionDataActivity::class.java)
            startActivity(intent)
        }

    }
    protected open fun showFruitAmount() {
        val json = Gson()
        val dlObject = json.fromJson(
            intent.getStringExtra(DL_ATTRS),
            DeepLink::class.java
        )
        var fruitAmount: String
        if (dlObject != null) {
            val dlData = dlObject.clickEvent
            fruitAmount = when {
                dlData.has("deep_link_value") && dlData.has("deep_link_sub1") ->
                    dlObject.getStringValue("deep_link_sub1") ?: ""
                //In case there is no "deep_link_sub1" we will take the fruitAmount from "fruit_amount"
                dlData.has("fruit_name") && dlData.has("fruit_amount") ->
                    dlObject.getStringValue("fruit_amount") ?: ""

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

}