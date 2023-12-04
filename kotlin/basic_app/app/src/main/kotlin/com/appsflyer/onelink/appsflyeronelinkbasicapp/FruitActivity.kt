package com.appsflyer.onelink.appsflyeronelinkbasicapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class FruitActivity : AppCompatActivity() {


    protected abstract fun getLayoutResourceId(): Int
    protected abstract val fruitName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())
    }
}