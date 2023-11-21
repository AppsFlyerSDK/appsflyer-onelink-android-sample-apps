package com.appsflyer.onelink.appsflyeronelinkbasicapp

import android.os.Bundle

class BananasActivity : FruitActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override protected fun getLayoutResourceId():Int{
        return R.layout.activity_bananas;
    }
}