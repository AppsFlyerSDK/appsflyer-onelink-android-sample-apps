package com.appsflyer.onelink.appsflyeronelinkbasicapp

import android.app.Application
import android.util.Log
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener

class AppsflyerBasicApp: Application() {
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
                Log.d("SDK START", "Launch sent successfully")
            }

            //Error Message
            override fun onError(errorCode: Int, errorDesc: String) {
                Log.d("SDK START", "Launch failed to be sent:\n" +
                        "Error code: " + errorCode + "\n"
                        + "Error description: " + errorDesc)
            }
        })

    }
}