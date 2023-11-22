package com.appsflyer.onelink.appsflyeronelinkbasicapp

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.compose.ui.text.toUpperCase
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.appsflyer.deeplink.DeepLink
import com.appsflyer.deeplink.DeepLinkListener
import com.appsflyer.deeplink.DeepLinkResult
import java.lang.Error

class AppsflyerBasicApp: Application() {

    //A global log tag value
    companion object {
        const val LOG_TAG: String = "Team3 Log Tag"
    }

    override fun onCreate(){
        super.onCreate();
        //Getting the SDK instance, through which you can access the methods in the af library.
        val appsFlyer: AppsFlyerLib = AppsFlyerLib.getInstance();

        //Initializing AppsFlyer SDK
        appsFlyer.init(AppsFlyerConstants.afDevKey,null,this);

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

        AppsFlyerLib.getInstance().subscribeForDeepLink(object : DeepLinkListener {
            override fun onDeepLinking(deepLinkResult: DeepLinkResult) {
                Log.d(LOG_TAG, "WORKING")
                when (deepLinkResult.status) {
                    DeepLinkResult.Status.FOUND -> {
                        Log.d(
                            LOG_TAG,"Deep link found"
                        )
                    }
                    DeepLinkResult.Status.NOT_FOUND -> {
                        Log.d(
                            LOG_TAG,"Deep link not found"
                        )
                        return
                    }
                    else -> {
                        // dlStatus == DeepLinkResult.Status.ERROR
                        val dlError = deepLinkResult.error
                        Log.d(
                            LOG_TAG,"There was an error getting Deep Link data: $dlError"
                        )
                        return
                    }
                }
                var deepLinkObj: DeepLink = deepLinkResult.deepLink
                try {
                    Log.d(
                        LOG_TAG,"The DeepLink data is: $deepLinkObj"
                    )
                } catch (e: Exception) {
                    Log.d(
                        LOG_TAG,"DeepLink data came back null"
                    )
                    return
                }

                // An example for using is_deferred
                if (deepLinkObj.isDeferred == true) {
                    Log.d(LOG_TAG, "This is a deferred deep link");
                } else {
                    Log.d(LOG_TAG, "This is a direct deep link");
                }

                try {
                    val fruitName = deepLinkObj.deepLinkValue
                    goToFruit(fruitName);
                    Log.d(LOG_TAG, "The DeepLink will route to: $fruitName")
                } catch (e:Exception) {
                    Log.d(LOG_TAG, "There's been an error: $e");
                    return;
                }
            }
        })





    }


    private fun goToFruit(fruitName: String?){
        if(fruitName == null){
            Log.d(AppsflyerBasicApp.LOG_TAG,"Fruit name is null!");
            return;
        }

        val fruitClassName = fruitName.substring(0,1).toUpperCase().plus(fruitName.substring(1)).plus("Activity");

        try {
            val fruitClass:Class<*> = Class.forName(packageName.plus(".").plus(fruitClassName));
            val intent: Intent = Intent(applicationContext, fruitClass);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent)
        }catch (err: Error){
            Log.d(AppsflyerBasicApp.LOG_TAG, err.message ?: "Error unknown");

        }

    }
}