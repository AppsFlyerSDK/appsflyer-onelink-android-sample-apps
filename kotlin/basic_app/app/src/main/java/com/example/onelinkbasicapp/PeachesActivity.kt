package com.example.onelinkbasicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PeachesActivity : FruitActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_peaches
    }
}