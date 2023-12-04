package com.appsflyer.onelink.appsflyeronelinkbasicapp

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConversionDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversion_data)
        val conversionData = (application as AppsflyerBasicApp).conversionData
        displayConversionData(conversionData)
    }

    //A function that receives a map containing the conversion data and displays it
    private fun displayConversionData(conversionData: Map<String, Any>?) {
        val conversionDataString = mapToSortedString(conversionData)
        val conversionDataTextView = findViewById<TextView>(R.id.conversionDataTextView)
        conversionDataTextView.movementMethod = ScrollingMovementMethod()
        conversionDataTextView.setText(conversionDataString)

    }
// A function that accepts a Map variable and converts it to a String variable
    private fun mapToSortedString(map: Map<String, Any>?): String {
        var result = ""

        map?.let {
            for (keyValuePair in map) {
                result += "${keyValuePair.key} : ${keyValuePair.value}\n"
            }
        } ?: run {
            result = "Conversion data not available at the moment"
        }
        return result
    }

}