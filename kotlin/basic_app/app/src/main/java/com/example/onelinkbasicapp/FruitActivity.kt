package com.example.onelinkbasicapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class FruitActivity: AppCompatActivity() {

    //All the id's are recieved to this line and use it in line 13
    protected abstract fun getLayoutResourceId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId());
    }
}