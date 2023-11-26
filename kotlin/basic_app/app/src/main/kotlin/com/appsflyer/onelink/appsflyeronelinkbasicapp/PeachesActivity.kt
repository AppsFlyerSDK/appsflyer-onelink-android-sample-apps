package com.appsflyer.onelink.appsflyeronelinkbasicapp

import android.os.Bundle

class PeachesActivity : FruitActivity() {
    override val fruitName: String = "peaches"
    override protected fun getLayoutResourceId(): Int = R.layout.activity_peaches
}