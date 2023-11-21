package com.appsflyer.onelink.appsflyeronelinkbasicapp

import android.os.Bundle
import com.appsflyer.onelink.appsflyeronelinkbasicapp.FruitActivity

class PeachesActivity : FruitActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_peaches
    }
}