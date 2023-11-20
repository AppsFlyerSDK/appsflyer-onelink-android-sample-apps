package com.example.onelinkbasicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class BananasActivity : FruitActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bananas)
    }
    override protected fun getLayoutResourceId():Int{
        return R.layout.activity_bananas;
    }
}