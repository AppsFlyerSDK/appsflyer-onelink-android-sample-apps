package com.appsflyer.onelink.appsflyeronelinkbasicapp

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.compose.ui.text.toUpperCase
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.appsflyer.deeplink.DeepLink
import com.appsflyer.deeplink.DeepLinkListener
import com.appsflyer.deeplink.DeepLinkResult
import com.google.gson.Gson
import org.json.JSONObject

class AppsflyerBasicApp: Application() {

    //Global Values
    companion object {
        const val LOG_TAG: String = "Team3 Log Tag"
        const val DL_ATTRS: String = "dl_attrs"

    }

    var conversionData: Map<String, Any>? = null

    override fun onCreate(){
        super.onCreate()
        //Getting the SDK instance, which helps you access the methods in the af library.
        val appsFlyer: AppsFlyerLib = AppsFlyerLib.getInstance()


        val conversionListener:AppsFlyerConversionListener=object : AppsFlyerConversionListener{
            override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                    data?.let {
                        val status: Any? = data["af_status"]
                        Log.d(LOG_TAG, "::$status");
                        if(status.toString() == "Non-organic"){
                            if(data["is_first_launch"] ==true){
                                Log.d(LOG_TAG,"First time launching")
                            }
                            if(data.containsKey("fruit_name")){
                                data.put("deep_link_value", data["fruit_name"] as String)
                            }
                    }


                    }
                        ?:run{

                    Log.d(LOG_TAG, "Conversion Failed: " );

                }
                conversionData=data;
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

        appsFlyer.subscribeForDeepLink(object : DeepLinkListener {
            override fun onDeepLinking(deepLinkResult: DeepLinkResult) {
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
                val deepLinkObj: DeepLink = deepLinkResult.deepLink
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
                    Log.d(LOG_TAG, "This is a deferred deep link")
                } else {
                    Log.d(LOG_TAG, "This is a direct deep link")
                }

                try {
                    val fruitName = deepLinkObj.deepLinkValue
                    Log.d(LOG_TAG, "The DeepLink will route to: $fruitName")
                    goToFruit(fruitName, deepLinkObj)
                } catch (e:Exception) {
                    Log.d(LOG_TAG, "There's been an error: $e")
                    return
                }
            }
        })





    }


    private fun goToFruit(fruitName: String?, dlData: DeepLink?){
        //Checking if the fruit name is not null
        if(fruitName == null){
            Log.d(LOG_TAG,"Fruit name is null!")
            return
        }

        //Creating a string that represents the desired activity class name
        val fruitClassName = fruitName.substring(0,1).uppercase().plus(fruitName.substring(1)).plus("Activity")

        try {
            //Creating an object that represents the class we want to switch to
            val fruitClass:Class<*> = Class.forName(packageName.plus(".").plus(fruitClassName))

            //Creating an intent from the current activity to the desired one
            val intent = Intent(applicationContext, fruitClass)

            if(dlData != null){
                //Converting the deep link data from object to string using json
                val objToStr: String = Gson().toJson(dlData)
                //Putting the json string inside the intent in order to pass it
                intent.putExtra(DL_ATTRS, objToStr)
            }
            //Adding a flag that allows us to open an activity from a class that is not an activity
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            startActivity(intent)
        }catch (e: Exception){
            Log.d(LOG_TAG, "There's been an error: $e")

        }

    }

    fun mapToDeepLinkObject(conversionDataMap: Map<String, Any>?): DeepLink? {
        try {
            // Convert the map to a JSON string using Gson library
            val objToStr = Gson().toJson(conversionDataMap)

            // Create a DeepLink object by wrapping the JSON string in an AFKeystoreWrapper
            val deepLink = DeepLink.AFKeystoreWrapper(JSONObject(objToStr))

            // Return the created DeepLink object
            return deepLink
        } catch (e: org.json.JSONException) {
            // Handle JSONException if it occurs, and log an error message
            Log.d(LOG_TAG, "Error when converting map to DeepLink object: ${e.toString()}")
        }

        // Return null if there was an error or if the conversionDataMap is null
        return null
    }

}