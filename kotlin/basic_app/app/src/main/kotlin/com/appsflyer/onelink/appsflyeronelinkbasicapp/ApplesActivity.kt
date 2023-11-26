package com.appsflyer.onelink.appsflyeronelinkbasicapp

import android.os.Bundle

class ApplesActivity : FruitActivity() {
    override val fruitName: String = "apples"
    override protected fun getLayoutResourceId():Int{
        return R.layout.activity_apples
    }
}