package com.appsflyer.onelink.appsflyeronelinkbasicapp

import android.os.Bundle

class BananasActivity : FruitActivity() {
    override val fruitName: String = "bananas"
    override protected fun getLayoutResourceId(): Int = R.layout.activity_bananas

}