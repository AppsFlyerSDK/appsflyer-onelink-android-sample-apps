package com.appsflyer.onelink.appsflyeronelinkbasicapp

import android.app.Application
import android.content.Intent
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.appsflyer.deeplink.DeepLink
import com.appsflyer.deeplink.DeepLinkListener
import com.appsflyer.deeplink.DeepLinkResult
import com.google.gson.Gson
import org.json.JSONObject

class AppsflyerBasicApp: Application(){

    //Global Values
    companion object {
        const val LOG_TAG: String = "AppsFlyerOneLinkSimApp"
        const val DL_ATTRS: String = "dl_attrs"

    }

    var conversionData: Map<String, Any>? = null

    // This boolean flag signals between the UDL and GCD callbacks that this deep_link was
    // already processed, and the callback functionality for deep linking can be skipped.
    // When GCD or UDL finds this flag true it MUST set it to false before skipping.

    var deferred_deep_link_processed_flag = false

    override fun onCreate() {
        super.onCreate()
        //Getting the SDK instance, which helps you access the methods in the af library.
        val appsFlyer: AppsFlyerLib = AppsFlyerLib.getInstance()

        appsFlyer.setDebugLog(true)
        appsFlyer.setMinTimeBetweenSessions(0)

        //Setting OneLink template ID
        appsFlyer.setAppInviteOneLink("H5hv")

        //Deep Linking Handling
        appsFlyer.subscribeForDeepLink(object : DeepLinkListener {
            override fun onDeepLinking(deepLinkResult: DeepLinkResult) {
                when (deepLinkResult.status) {
                    DeepLinkResult.Status.FOUND -> {
                        Log.d(
                            LOG_TAG, "Deep link found"
                        )
                    }

                    DeepLinkResult.Status.NOT_FOUND -> {
                        Log.d(
                            LOG_TAG, "Deep link not found"
                        )
                        return
                    }

                    else -> {
                        // dlStatus == DeepLinkResult.Status.ERROR
                        val dlError = deepLinkResult.error
                        Log.d(
                            LOG_TAG, "There was an error getting Deep Link data: $dlError"
                        )
                        return
                    }
                }
                val deepLinkObj: DeepLink = deepLinkResult.deepLink
                try {
                    Log.d(
                        LOG_TAG, "The DeepLink data is: $deepLinkObj"
                    )
                } catch (e: Exception) {
                    Log.d(
                        LOG_TAG, "DeepLink data came back null"
                    )
                    return
                }

                // An example for using is_deferred
                if (deepLinkObj.isDeferred == true) {
                    Log.d(LOG_TAG, "This is a deferred deep link")

                    if (deferred_deep_link_processed_flag == true) {
                        Log.d(
                            LOG_TAG,
                            "Deferred deep link was already processed by GCD. This iteration can be skipped."
                        )
                        deferred_deep_link_processed_flag = false
                        return
                    }
                } else {
                    Log.d(LOG_TAG, "This is a direct deep link")
                }
                Log.d(LOG_TAG, deepLinkObj.toString())
                try {
                    var fruitName = deepLinkObj.deepLinkValue

                    if (fruitName == null || fruitName == "") {
                        Log.d(LOG_TAG, "deep_link_value returned null")
                        fruitName = deepLinkObj.getStringValue("fruit_name")
                        if (fruitName == null || fruitName == "") {
                            Log.d(LOG_TAG, "could not find Fruit name!")
                            return
                        }
                        Log.d(LOG_TAG, "fruit_name is $fruitName. This is an old link")

                    }

                    Log.d(LOG_TAG, "The DeepLink will route to: $fruitName")

                    // This marks to GCD that UDL already processed this deep link.
                    // It is marked to both DL and DDL, but GCD is relevant only for DDL
                    deferred_deep_link_processed_flag = true
                    Log.d(LOG_TAG, "Fruit: " + fruitName)

//                    goToFruit(fruitName, deepLinkObj)
                } catch (e: Exception) {
                    Log.d(LOG_TAG, "There's been an error: $e")
                    return
                }
            }
        })


        //Conversion Data Handling
        val conversionListener: AppsFlyerConversionListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                data?.let {
                    val status: Any? = data["af_status"]
                    Log.d(LOG_TAG, "::$status");
                    Log.d(LOG_TAG, "Conversion Data: " + data.toString())
                    if (status.toString() == "Non-organic") {
                        if (data["is_first_launch"] == true) {
                            Log.d(LOG_TAG, "First time launching")
                            //Deferred deep link in case of a legacy link
                            if (deferred_deep_link_processed_flag == true) {
                                Log.d(LOG_TAG, "Deferred deep link was already processed by UDL. The DDL processing in GCD can be skipped.")
                                deferred_deep_link_processed_flag = false
                            } else {
                                deferred_deep_link_processed_flag = true

                                if (data.containsKey("fruit_name")) {
                                    data.put("deep_link_value", data["fruit_name"] as String)
                                }
                                val string: String = data.get("deep_link_value").toString()
                                Log.d(LOG_TAG, "Fruit: " + string)
                            }
                        } else {
                            Log.d(LOG_TAG, "Conversion: Not First Launch")
                        }
                    } else {
                        Log.d(LOG_TAG, "Conversion: This is an organic install.")
                    }
                        ?: run {

                            Log.d(LOG_TAG, "Conversion Failed: ");

                        }
                    conversionData = data;
                }
            }





            override fun onConversionDataFail(errorMessage: String?) {
                // Your implementation for onConversionDataFail
                if (errorMessage != null) {
                    Log.d(LOG_TAG,errorMessage)
                };

            }

            override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {
                Log.d(LOG_TAG, "onAppOpenAttribution: This is fake call.");
            }

            override fun onAttributionFailure(errorMessage: String?) {
                Log.d(LOG_TAG, "error onAttributionFailure : " + errorMessage);
            }

        }
        //Initializing AppsFlyer SDK
        appsFlyer.init(AppsFlyerConstants.afDevKey,conversionListener,this)


        //Starts the SDK and logs a message if the SDK started or not
        appsFlyer.start(this, AppsFlyerConstants.afDevKey, object :
            AppsFlyerRequestListener {

            //Success Message
            override fun onSuccess() {
                Log.d(LOG_TAG, "Launch sent successfully")
            }

            //Error Message
            override fun onError(errorCode: Int, errorDesc: String) {
                Log.d(LOG_TAG, "Launch failed to be sent:\n" +
                        "Error code: " + errorCode + "\n"
                        + "Error description: " + errorDesc)
            }
        })
    }
}