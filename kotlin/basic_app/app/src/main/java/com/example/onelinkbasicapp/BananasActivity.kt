package com.example.onelinkbasicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class BananasActivity : FruitActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    //Returns the id of this class so the fruit activity can change how it look
    override fun getLayoutResourceId():Int{
        return R.layout.activity_bananas;
    }
}