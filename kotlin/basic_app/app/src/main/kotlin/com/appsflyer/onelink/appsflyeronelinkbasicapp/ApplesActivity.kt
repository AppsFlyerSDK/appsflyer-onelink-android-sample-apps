package com.appsflyer.onelink.appsflyeronelinkbasicapp

import android.os.Bundle
import android.util.Log
import com.appsflyer.AppsFlyerLib
import com.appsflyer.AFInAppEventType // Predefined event names
import com.appsflyer.AFInAppEventParameterName // Predefined parameter names
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.appsflyer.onelink.appsflyeronelinkbasicapp.AppsflyerBasicApp.Companion.LOG_TAG

class ApplesActivity : FruitActivity() {
    override val fruitName: String = "apples"
    override fun getLayoutResourceId(): Int = R.layout.activity_apples

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val eventValues = HashMap<String, Any>()
        eventValues[AFInAppEventParameterName.PRICE] = 1234.56
        eventValues[AFInAppEventParameterName.CONTENT_ID] = "1234567"

        AppsFlyerLib.getInstance().logEvent(
            applicationContext,
            AFInAppEventType.ADD_TO_WISH_LIST , eventValues)

        AppsFlyerLib.getInstance().logEvent(
            applicationContext,
            AFInAppEventType.PURCHASE,
            eventValues,
            object : AppsFlyerRequestListener {
                override fun onSuccess() {
                    Log.d(LOG_TAG, "Event sent successfully")
                }
                override fun onError(errorCode: Int, errorDesc: String) {
                    Log.d(LOG_TAG, "Event failed to be sent:\n" +
                            "Error code: " + errorCode + "\n"
                            + "Error description: " + errorDesc)
                }
            })
    }

}